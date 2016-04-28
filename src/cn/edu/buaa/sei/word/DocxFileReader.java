package cn.edu.buaa.sei.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import cn.edu.buaa.sei.util.Helper;
import cn.edu.buaa.sei.word.ds.DocxParagraph;
import cn.edu.buaa.sei.word.ds.WordParagraph;


public class DocxFileReader extends ComWordReader {

    private XWPFDocument docx;
    private FileInputStream fis;

    public DocxFileReader()
    {
        super();
    }


    public void close() throws IOException
    {
        if (null != docx)
            docx.close();
        if (null != fis)
            fis.close();
    }

    public boolean open(File file) throws IOException
    {
        if (!file.exists()) return false;
        
        fis = new FileInputStream(file);
        if (null == fis)
            return false;

        docx = new XWPFDocument(fis);
        if (null == docx)
            return false;

        return true;
    }

    public boolean open(String filename) throws IOException
    {
        return this.open(new File(filename));
    }
    
    @Override
    public void processParagraphs()
    {
        List<WordParagraph> list = new ArrayList<>();
        for (XWPFParagraph p : docx.getParagraphs()) {
            String style, text;
            style = p.getStyle();
            text = Helper.rmNonUTF8(p.getText());
            list.add(new DocxParagraph(style, text));
        }
        this.paras2Tree(list);
        this.write2JsonFile(conf.getPath2WordOutput()+conf.getInputFilename()+".json");
    }


    @Override
    public void processTables()
    {
        // TODO Auto-generated method stub
        for (XWPFTable tab : docx.getTables()) {
            String useCaseName;
            List<XWPFTableRow> rows = tab.getRows();
            for (int i = 0; i < rows.size(); ++i) {
                List<XWPFTableCell> cells = rows.get(i).getTableCells();
                int nCol = cells.size();
                if (nCol >= 2) {
                    String keyStr = cells.get(0).getText();
                    String valStr = cells.get(1).getText();
                    if (keyStr.trim().startsWith("Use case name")) {
                        useCaseName = valStr;
                        logger.info(useCaseName);
                    } else {
                        continue;
                    }
                }
            }
        }
    }
    
}
