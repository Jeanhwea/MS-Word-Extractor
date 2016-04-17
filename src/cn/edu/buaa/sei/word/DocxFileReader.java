package cn.edu.buaa.sei.word;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.alibaba.fastjson.JSON;

import cn.edu.buaa.sei.ds.DocxJsonNode;
import cn.edu.buaa.sei.ds.DocxTextNode;
import cn.edu.buaa.sei.ds.AppConfig;
import cn.edu.buaa.sei.util.ConfigMgr;
import cn.edu.buaa.sei.util.GenericFileIO;
import cn.edu.buaa.sei.util.LoggerMgr;

public class DocxFileReader {

    private AppConfig conf;
    private Logger logger;

    private String infile;
    private String outfile;
    private FileInputStream fis;
    private FileOutputStream fos;
    private XWPFDocument docx;

    // document root elements
    private DocxJsonNode top = null;

    public DocxFileReader()
    {
        conf = ConfigMgr.getConfig();
        logger = LoggerMgr.getLogger();
        initConfig();
    }

    // 将child标题节点添加到parent的"child"的数组里面
    private void addChild(DocxJsonNode child, DocxJsonNode parent)
    {
        List<DocxJsonNode> array_child = parent.getChild();
        array_child.add(child);
    }

    // 将content内容节点添加到parent的"content"的数组里面
    private void addContent(DocxTextNode content, DocxJsonNode parent)
    {
        List<DocxTextNode> array_content = parent.getContent();
        array_content.add(content);
    }

    // 构造一个JSON的内容节点
    private DocxTextNode allocContentItem(String text)
    {
        return new DocxTextNode(text);
    }

    // 构造一个JSON的标题节点
    private DocxJsonNode allocTitleItem(int level, String title)
    {
        DocxJsonNode item = new DocxJsonNode();
        item.setLevel(level);
        item.setTitle(title);
        item.setContent(new ArrayList<DocxTextNode>());
        item.setChild(new ArrayList<DocxJsonNode>());
        return item;
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

    public DocxJsonNode getDocxTop()
    {
        return this.top;
    }

    private String getJsonString()
    {
        if (null == top) {
            this.process();
        }
        
        return JSON.toJSONString(top);
    }

    private void initConfig()
    {
        infile = conf.getPath_to_word_input() + conf.getInput_filename();
        outfile = conf.getPath_to_word_output() + conf.getOutput_filename();
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
    
    public void process()
    {
        List<XWPFParagraph> paralist = docx.getParagraphs();

        Stack<DocxJsonNode> stack = new Stack<DocxJsonNode>();
        top = allocTitleItem(0, "root");
        stack.push(top);

        int level = 0; // 当前的大纲级别
        DocxJsonNode item = null;
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
                    // 回退到相应的大纲级别
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
        
        this.write2JsonFile(conf.getPath_to_word_output() + "docx.json");
//        String text = JSON.toJSONString(top);
//        logger.info(text);
    }
    
    /**
     * 将读取到的word内容写入相应的JSON格式文件中
     * @param filename 写入的文件名
     * @return
     */
    public boolean write2JsonFile(String filename)
    {
        GenericFileIO fio = new GenericFileIO();
        return fio.write(this.getJsonString(), filename);
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

        if (null != docx) {
            docx.write(fos);
        } else {
            logger.debug("OutputStream is NULL");
        }

        return true;
    }

}
