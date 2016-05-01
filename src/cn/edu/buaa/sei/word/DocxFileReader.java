package cn.edu.buaa.sei.word;

import cn.edu.buaa.sei.rucm.RucmHelper;
import cn.edu.buaa.sei.rucm.ds.UseCase;
import cn.edu.buaa.sei.util.GenericFileIO;
import cn.edu.buaa.sei.util.Helper;
import cn.edu.buaa.sei.word.ds.DocxParagraph;
import cn.edu.buaa.sei.word.ds.WordParagraph;
import com.alibaba.fastjson.JSON;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DocxFileReader extends ComWordReader {
    private XWPFDocument docx;
    private FileInputStream fis;

    public DocxFileReader() {
        super();
    }

    private String simplifyFieldName(String nameStr) {
        if (nameStr.trim().startsWith("Use"))
            return "UseCase";
        if (nameStr.trim().startsWith("Precond"))
            return "PreCondition";
        if (nameStr.trim().startsWith("Postcond"))
            return "PostCondition";
        if (nameStr.trim().startsWith("Input"))
            return "Input";
        if (nameStr.trim().startsWith("Output"))
            return "Output";
        if (nameStr.trim().startsWith("Basic"))
            return "BasicFlow";
        if (nameStr.trim().startsWith("Specific"))
            return "AlterFlow";
        return "null";
    }

    public void close() throws IOException {
        if (null != docx) docx.close();
        if (null != fis) fis.close();
    }

    public boolean open(File file) throws IOException {
        if (!file.exists()) return false;
        fis = new FileInputStream(file);
        docx = new XWPFDocument(fis);
        return true;
    }

    public boolean open(String filename) throws IOException {
        return this.open(new File(filename));
    }

    @Override
    public void processParagraphs() {
        List<WordParagraph> list = new ArrayList<>();
        for (XWPFParagraph p : docx.getParagraphs()) {
            String style, text;
            style = p.getStyle();
            text = Helper.rmNonUTF8(p.getText());
            list.add(new DocxParagraph(style, text));
        }
        this.paras2Tree(list);
        this.write2JsonFile(conf.getPath2WordOutput() + conf.getInputFileName() + ".json");
    }


    @Override
    public void processTables() {
        int useCaseCounter = 0;
        List<XWPFTable> tables = docx.getTables();
        for (int i = 1; i < tables.size()-2; ++i) {
            XWPFTable tab = tables.get(i), nexttab = tables.get(i+1);
            List<XWPFTableRow> rows = tab.getRows();
            if (!nexttab.getText().trim().startsWith("Use case name")) {
                rows.addAll(nexttab.getRows());
                i++;
            }
            UseCase uc = extractRUCM(rows);
            if (null == uc) continue;
            useCaseCounter++;
            logger.info(useCaseCounter + uc.getUseCaseName());
//            String filename = String.format("%s%02d-%s.json", conf.getPath2WordOutput(), useCaseCounter, uc.getUseCaseName());
//            new GenericFileIO().write(JSON.toJSONString(uc), filename);
        }
        logger.info("Total Use Case Count : " + useCaseCounter);
    }

    private UseCase extractRUCM(List<XWPFTableRow> rows) {
        UseCase uc = null;
        String keyStr = null, valStr = null;
        List<XWPFTableCell> cells;
        if (rows.size() <= 0) {
            return null;
        }
        cells = rows.get(0).getTableCells();
        if (cells.size() >= 2) {
            keyStr = cells.get(0).getText();
            valStr = cells.get(1).getText();
        }
        if (null == keyStr) return null;
        if ("UseCase".equals(simplifyFieldName(keyStr))) {
            uc = RucmHelper.allocUseCase(valStr);
            // handler other field
            for (int i = 1; i < rows.size(); ++i) {
                cells = rows.get(i).getTableCells();
                if (cells.size() >= 2) {
                    keyStr = cells.get(0).getText();
                    valStr = cells.get(1).getText();
                }
                if (null == keyStr) return null;
                switch (simplifyFieldName(keyStr)) {
                    case "PreCondition":
                        RucmHelper.addPreCondition2UseCase(RucmHelper.allocCondition(valStr), uc);
                        break;
                    case "Input":
                        RucmHelper.addInput2UseCase(RucmHelper.allocInput(valStr), uc);
                        break;
                    case "Output":
                        RucmHelper.addOutput2UseCase(RucmHelper.allocOutput(valStr), uc);
                        break;
                    case "PostCondition":
                        RucmHelper.addPostCondition2UseCase(RucmHelper.allocCondition(valStr), uc);
                        break;
                    case "BasicFlow":
                        RucmHelper.addBasicFlow2UseCase(RucmHelper.allocStep(valStr), uc.getBasicFlow());
                        for ( ; i < rows.size()-1; ) {
                            cells=rows.get(++i).getTableCells();
                            if (cells.size() >= 2) {
                                keyStr = cells.get(0).getText();
                                valStr = cells.get(1).getText();
                            }
                            if ("null".equals(simplifyFieldName(keyStr))) {
                                RucmHelper.addBasicFlow2UseCase(RucmHelper.allocStep(valStr), uc.getBasicFlow());
                            } else {
                                --i;
                                break;
                            }
                        }
                        break;
                    case "AlterFlow":
                        RucmHelper.addAlterFlow2UseCase(RucmHelper.allocFlow(), uc.getAlterFlow());
                        // 解析valStr，用来获得参考的Basic Flow
                        for (int p = 0; p < valStr.length(); p++) {
                            int sum = 0;
                            char ch; boolean flag = false;
                            for (; (p < valStr.length()) && Character.isDigit(ch = valStr.charAt(p)); p++) {
                                sum = 10 * sum + ch - '0';
                                flag = true;
                            }
                            if (flag)
                                RucmHelper.appendReferBasicFlow2UseCase(new Integer(sum), uc);
                        }
                        for ( ; i < rows.size()-1; ) {
                            cells=rows.get(++i).getTableCells();
                            keyStr = cells.get(0).getText();
                            if (!"null".equals(simplifyFieldName(keyStr))) {
                                --i;
                                break;
                            }
                            if (2==cells.size()) {
                                valStr = cells.get(1).getText();
                                RucmHelper.appendStep2UseCase(RucmHelper.allocStep(valStr), uc);
                            } else if (3==cells.size()) {
                                if ("PostCondition".equals(simplifyFieldName(cells.get(1).getText()))) {
                                    valStr = cells.get(2).getText();
                                    RucmHelper.appendPostCondition2UseCase(RucmHelper.allocCondition(valStr), uc);
                                }
                            }

                            logger.info(keyStr+"-"+valStr);
                        }
                        break;
                }
            }
        }
        return uc;
    }

}

