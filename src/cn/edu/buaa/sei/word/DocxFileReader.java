package cn.edu.buaa.sei.word;

import cn.edu.buaa.sei.rucm.RucmHelper;
import cn.edu.buaa.sei.rucm.ds.UseCase;
import cn.edu.buaa.sei.util.Helper;
import cn.edu.buaa.sei.word.ds.DocxParagraph;
import cn.edu.buaa.sei.word.ds.WordParagraph;
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

        if (nameStr.trim().startsWith("Use case name"))
            return "UseCase";

        if (nameStr.trim().startsWith("Precondition"))
            return "PreCondition";

        if (nameStr.trim().startsWith("Postcondition"))
            return "PostCondition";

        if (nameStr.trim().startsWith("Input"))
            return "Input";

        if (nameStr.trim().startsWith("Output"))
            return "Output";

        if (nameStr.trim().startsWith("Basic flow"))
            return "BasicFlow";

        if (nameStr.trim().startsWith("Specific alternative flows"))
            return "AlterFlow";

        return "null";
    }

    public void close() throws IOException {
        if (null != docx)
            docx.close();
        if (null != fis)
            fis.close();
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

        for (XWPFTable tab : docx.getTables()) {
            UseCase uc = extractRUCM(tab);
            if (null == uc) continue;
            useCaseCounter++;
            logger.info(useCaseCounter + uc.getUseCaseName());
        }
        logger.info("Total Use Case Count : " + useCaseCounter);
    }

    private UseCase extractRUCM(XWPFTable table) {
        UseCase uc = null;

        String keyStr = null, valStr = null;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;

        rows = table.getRows();
        if (rows.size() <= 0) return null;
        cells = rows.get(0).getTableCells();
        if (cells.size() >= 2) {
            keyStr = cells.get(0).getText();
            valStr = cells.get(1).getText();
        }
        if (null == keyStr) return null;
        if ("UseCase".equals(simplifyFieldName(keyStr))) {
            uc = RucmHelper.allocUseCase(keyStr);

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
                        RucmHelper.addStep2BasicFlow(RucmHelper.allocStep(valStr), uc.getBasicFlow());
                        for (;;) {
                            cells = rows.get(++i).getTableCells();
                            if (cells.size() >= 2) {
                                keyStr = cells.get(0).getText();
                                valStr = cells.get(1).getText();
                            }
                            if ("null".equals(simplifyFieldName(keyStr))) {
                                RucmHelper.addStep2BasicFlow(RucmHelper.allocStep(valStr), uc.getBasicFlow());
                            } else {
                                --i;
                                break;
                            }
                        }
                        break;
                    case "AlterFlow":
                        RucmHelper.addFlow2AlterFlow(RucmHelper.allocFlow(), uc.getAlterFlow());
                        break;
                }
            }
        }
        return uc;
    }


}

