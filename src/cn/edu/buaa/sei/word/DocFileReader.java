package cn.edu.buaa.sei.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.StyleSheet;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

import cn.edu.buaa.sei.util.Helper;
import cn.edu.buaa.sei.word.ds.DocParagraph;
import cn.edu.buaa.sei.word.ds.WordParagraph;

public class DocFileReader extends ComWordReader {

    HWPFDocument doc;
    FileInputStream fis;

    public DocFileReader()
    {
        super();
    }

    public void close() throws IOException
    {
        if (null != fis)
            fis.close();
    }

    public boolean open(File file) throws IOException
    {
        if (!file.exists()) return false;
        
        fis = new FileInputStream(file);
        if (null == fis)
            return false;
        
        doc = new HWPFDocument(fis);
        if (null == doc)
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
        ArrayList<WordParagraph> list = new ArrayList<WordParagraph>();
//        WordExtractor ext = new WordExtractor(doc);
        Range range = doc.getRange();
        StyleSheet stylesheet = doc.getStyleSheet();
        
        for (int i = 0; i < range.numParagraphs(); i++) {
            Paragraph para = range.getParagraph(i);
            String strStyle = stylesheet.getStyleDescription(para.getStyleIndex()).getName();
            String text = Paragraph.stripFields(para.text());
            if (null!=strStyle && null!=text) {
                list.add(new DocParagraph(strStyle, Helper.rmNonUTF8(text)));
            }
        }
        this.paras2Tree(list);
        this.write2JsonFile(conf.getPath_to_word_output()+conf.getInput_filename()+".json");
    }

    @Override
    public void processTables()
    {
        // TODO 需要添加处理doc格式表格的方法
    }
}
