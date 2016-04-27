package cn.edu.buaa.sei.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

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
    
    public void process()
    {
        List<WordParagraph> list = new ArrayList<WordParagraph>();
        for (XWPFParagraph p : docx.getParagraphs()) {
            list.add(new DocxParagraph(p.getStyle(), Helper.rmNonUTF8(p.getText())));
        }
        this.paras2Tree(list);
    }
    
}
