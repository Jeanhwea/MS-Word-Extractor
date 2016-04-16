package cn.edu.buaa.sei.ltp;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cn.edu.buaa.sei.AppMain;
import cn.edu.buaa.sei.ds.DocxJsonNode;
import cn.edu.buaa.sei.ds.DocxTextNode;
import cn.edu.buaa.sei.util.GenericFileIO;

public class LtpStat {
    
    private HashMap<String, Integer> counter = new HashMap<String, Integer>();
    private Logger logger;
    private LtpClient ltp;
    private LtpXmlParser parser;

    public LtpStat(AppMain app)
    {
        logger = app.getLogger();
        ltp = new LtpClient(app);
        parser = new LtpXmlParser(app);
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
        StringBuilder sb = new StringBuilder();
        for (Entry<String, Integer> e : counter.entrySet()) {
            sb.append(e.getKey() + "\t" + e.getValue() + "\n");
        }
        
        new GenericFileIO().write(sb.toString(), "output/stat.txt");
    }
}
