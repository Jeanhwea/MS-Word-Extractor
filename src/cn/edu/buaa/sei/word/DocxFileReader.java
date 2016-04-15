package cn.edu.buaa.sei.word;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.alibaba.fastjson.JSON;

import cn.edu.buaa.sei.AppMain;
import cn.edu.buaa.sei.util.InitConfig;

public class DocxFileReader {

    private Logger logger;
    private InitConfig conf;

    private String infile;
    private String outfile;
    private FileInputStream fis;
    private FileOutputStream fos;
    private XWPFDocument docx;

    // document root elements
    private HashMap<String, Object> top = null;

    private void initConfig()
    {
        infile = conf.getPath_to_word_input() + conf.getInput_filename();
        outfile = conf.getPath_to_word_output() + conf.getOutput_filename();
    }

    public DocxFileReader(AppMain app)
    {
        logger = app.getLogger();
        conf = app.getConfig();
        initConfig();
    }

    public boolean open(String filename) throws IOException
    {
        if (null == filename) {
            logger.trace("Open default file: " + infile);
        } else {
            this.infile = filename;
            logger.trace("Open file: " + infile);
        }

        fis = new FileInputStream(this.infile);
        if (null == fis)
            return false;

        docx = new XWPFDocument(fis);
        if (null == docx)
            return false;

        return true;
    }

    public boolean write(String filename) throws IOException
    {
        if (null == filename) {
            logger.trace("Write to default file: " + outfile);
        } else {
            this.outfile = filename;
            logger.trace("Write to file: " + outfile);
        }

        fos = new FileOutputStream(this.outfile);
        if (null == fos)
            return false;

        if (null != docx) {
            docx.write(fos);
        } else {
            logger.debug("OutputStream is NULL");
        }

        return true;
    }

    public boolean write(FileOutputStream fos)
    {
        if (null == fos) {
            logger.trace("FileOutputStream is NULL");
            return false;
        }

        return true;
    }

    // 构造一个JSON的标题节点
    private HashMap<String, Object> allocTitleItem(int level, String title)
    {
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put("level", new Integer(level));
        item.put("title", title);
        item.put("content", new ArrayList<Object>());
        item.put("child", new ArrayList<Object>());
        return item;
    }

    // 构造一个JSON的内容节点
    private HashMap<String, Object> allocContentItem(String text)
    {
        HashMap<String, Object> cont = new HashMap<String, Object>();
        cont.put("text", text);
        return cont;
    }

    // 将child标题节点添加到parent的"child"的数组里面
    private void addChild(HashMap<String, Object> child, HashMap<String, Object> parent)
    {
        @SuppressWarnings("unchecked")
        List<Object> array_child = (List<Object>) parent.get("child");
        array_child.add(child);
    }

    // 将conten内容节点添加到parent的"content"的数组里面
    private void addContent(HashMap<String, Object> content, HashMap<String, Object> parent)
    {
        @SuppressWarnings("unchecked")
        List<Object> array_child = (List<Object>) parent.get("content");
        array_child.add(content);
    }

    public void process()
    {
        List<XWPFParagraph> paralist = docx.getParagraphs();

        Stack<HashMap<String, Object>> stack = new Stack<HashMap<String, Object>>();
        top = allocTitleItem(0, "root");
        stack.push(top);

        int level = 0; // 当前的大纲级别
        HashMap<String, Object> item = null;
        for (XWPFParagraph para : paralist) {
            String text = para.getText().trim();
            String strStyle = para.getStyle();

            if (null != strStyle && strStyle.matches("[1-9]")) {
                // 如果当前段落是标题
                int current_level = Integer.parseInt(strStyle);
                if (current_level == level + 1) {
                    item = allocTitleItem(current_level, text);
                    addChild(item, stack.peek());
                } else if (current_level == level + 2) {
                    if (null == item)
                        logger.fatal("add null item to stack");
                    stack.push(item);
                    ++level;
                    item = allocTitleItem(current_level, text);
                    addChild(item, stack.peek());
                } else {
                    if (current_level > level)
                        logger.fatal("current level must less than level");
                    while (level >= current_level) {
                        stack.pop();
                        --level;
                    }
                    item = allocTitleItem(current_level, text);
                    addChild(item, stack.peek());
                }
            } else {
                // 如果当前段落不是标题
                if (!text.trim().isEmpty()) {
                    addContent(allocContentItem(text), item);
                }
            }
        }
        String jsontext = JSON.toJSONString(top);
        logger.trace(jsontext);
    }

    public void close() throws IOException
    {
        if (null != docx)
            docx.close();
        if (null != fis)
            fis.close();
        if (null != fos)
            fos.close();
    }

}
