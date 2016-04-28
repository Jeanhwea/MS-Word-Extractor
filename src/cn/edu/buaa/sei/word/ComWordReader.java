package cn.edu.buaa.sei.word;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.edu.buaa.sei.AppConfig;
import cn.edu.buaa.sei.util.ConfigMgr;
import cn.edu.buaa.sei.util.GenericFileIO;
import cn.edu.buaa.sei.util.LoggerMgr;
import cn.edu.buaa.sei.word.ds.WordParagraph;
import cn.edu.buaa.sei.word.ds.WordTextNode;
import cn.edu.buaa.sei.word.ds.WordTitleNode;

/**
 * 通用的读取 Word 模板
 * @author hujh
 *
 */
public abstract class ComWordReader {

    public AppConfig conf;
    public Logger logger;
    
    private List<WordParagraph> paralist;

    public ComWordReader()
    {
        conf = ConfigMgr.getConfig();
        logger = LoggerMgr.getLogger();
    }


    // document root elements
    private WordTitleNode top = null;


    // 将child标题节点添加到parent的"child"的数组里面
    private void addChild(WordTitleNode child, WordTitleNode parent)
    {
        List<WordTitleNode> array_child = parent.getChild();
        array_child.add(child);
    }

    // 将content内容节点添加到parent的"content"的数组里面
    private void addContent(WordTextNode content, WordTitleNode parent)
    {
        List<WordTextNode> array_content = parent.getContent();
        array_content.add(content);
    }

    // 构造一个JSON的内容节点
    private WordTextNode allocContentItem(String text)
    {
        return new WordTextNode(text);
    }

    // 构造一个JSON的标题节点
    private WordTitleNode allocTitleItem(int level, String title)
    {
        WordTitleNode item = new WordTitleNode();
        item.setLevel(level);
        item.setTitle(title);
        item.setContent(new ArrayList<WordTextNode>());
        item.setChild(new ArrayList<WordTitleNode>());
        return item;
    }


    public WordTitleNode getDocxTop()
    {
        return this.top;
    }

    private String getJsonString()
    {
        if (null == paralist)
            this.paras2Tree(paralist);
        return JSON.toJSONString(top);
    }
    
    /**
     * 将当前style字符串转化成相应的大纲级别, 一般大纲级别默认为0-9
     * @param style 字符串
     * @return 大纲级别，返回-1,表示无法解析的大纲级别; 返回0,表示非标题的正文内容.
     */
    private int style2Level(String style)
    {
        if (null == style) return -1;
        
        String[] stylePrefixs = {"标题 ", ""};
        
        for (String prefix : stylePrefixs) {
            if (style.matches(prefix + "[0-9]")) {
                return Integer.parseInt(style.replace(prefix, ""));
            }
        }
        
        return 0;
    }

    public void paras2Tree(List<WordParagraph> paralist)
    {
        
        this.paralist = paralist;

        Stack<WordTitleNode> stack = new Stack<WordTitleNode>();
        top = allocTitleItem(0, "root");
        stack.push(top);

        int level = 0; // 当前的大纲级别
        WordTitleNode item = top;
        for (WordParagraph para : paralist) {
            String text = para.getText();
            String strStyle = para.getStyle();

            if (this.style2Level(strStyle)>0) {
                // 如果当前段落是标题
                int current_level = this.style2Level(strStyle);
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
    
    /**
     * 处理word文档中正文的抽象类
     */
    public abstract void processParagraphs();
    
    /**
     * 处理word文档中表格的抽象类
     */
    public abstract void processTables();
}
