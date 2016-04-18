package cn.edu.buaa.sei.ltp;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cn.edu.buaa.sei.ds.DocxJsonNode;
import cn.edu.buaa.sei.ds.DocxTextNode;
import cn.edu.buaa.sei.ds.AppConfig;
import cn.edu.buaa.sei.util.ConfigMgr;
import cn.edu.buaa.sei.util.GenericFileIO;
import cn.edu.buaa.sei.util.LoggerMgr;

public class LtpStat {
    
    private HashMap<String, Integer> counter = new HashMap<String, Integer>();
    private AppConfig conf;
    private Logger logger;
    private LtpClient ltp;
    private LtpXmlParser parser;

    public LtpStat()
    {
        conf = ConfigMgr.getConfig();
        logger = LoggerMgr.getLogger();
        ltp = new LtpClient();
        parser = new LtpXmlParser();
    }
    
    public void countWords(String word)
    {
        Integer wd = counter.get(word);
        if (null == wd) {
            counter.put(word, 1);
        } else {
            counter.put(word, ++wd);
        }
    }
    
    public HashMap<String, Integer> getCounter()
    {
        return this.counter;
    }
    
    /**
     * 递归分析每个节点的文本
     * @param node 将要分析的文本节点
     */
    private void recursiveCount(DocxJsonNode node)
    {
        List<DocxTextNode> cnt = node.getContent();
        for (DocxTextNode t : cnt) {
            String xml = ltp.request(t.getText());
            parser.process(xml, this);
        }
        
        for (DocxJsonNode child : node.getChild()) {
            this.recursiveCount(child);
        }
    }
    
    public void startCount(DocxJsonNode top)
    {
        this.recursiveCount(top);
    }
    
    public void printSet() {
        StringBuilder sb = new StringBuilder("word,times" + System.lineSeparator());
        for (Entry<String, Integer> e : counter.entrySet()) {
            sb.append(e.getKey() + "," + e.getValue() + System.lineSeparator());
        }
        
        logger.trace(sb.toString());
        new GenericFileIO().write(sb.toString(), conf.getPath_to_word_output() + "stat.csv");
    }
}
