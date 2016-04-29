package cn.edu.buaa.sei.ltp;

import cn.edu.buaa.sei.AppConfig;
import cn.edu.buaa.sei.util.ConfigMgr;
import cn.edu.buaa.sei.util.GenericFileIO;
import cn.edu.buaa.sei.util.LoggerMgr;
import cn.edu.buaa.sei.word.ds.WordTextNode;
import cn.edu.buaa.sei.word.ds.WordTitleNode;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class LtpStat {

    private HashMap<String, Integer> counter = new HashMap<String, Integer>();
    private AppConfig conf;
    private Logger logger;
    private LtpClient ltp;
    private LtpXmlParser parser;

    public LtpStat() {
        conf = ConfigMgr.getConfig();
        logger = LoggerMgr.getLogger();
        ltp = new LtpClient();
        parser = new LtpXmlParser();
    }

    public void countWords(String word) {
        Integer wd = counter.get(word);
        if (null == wd) {
            counter.put(word, 1);
        } else {
            counter.put(word, ++wd);
        }
    }

    public HashMap<String, Integer> getCounter() {
        return this.counter;
    }

    /**
     * 递归分析每个节点的文本
     *
     * @param node 将要分析的文本节点
     */
    private void recursiveCount(WordTitleNode node) {
        List<WordTextNode> cnt = node.getContent();
        for (WordTextNode t : cnt) {
            String xml = ltp.request(t.getText());
            parser.process(xml, this);
        }

        for (WordTitleNode child : node.getChild()) {
            this.recursiveCount(child);
        }
    }

    public void startCount(WordTitleNode top) {
        this.recursiveCount(top);
    }

    public void printSet() {
        StringBuilder sb = new StringBuilder("word,times" + System.lineSeparator());
        for (Entry<String, Integer> e : counter.entrySet()) {
            sb.append(e.getKey() + "," + e.getValue() + System.lineSeparator());
        }

        logger.trace(sb.toString());
        new GenericFileIO().write(sb.toString(), conf.getPath2WordOutput() + "stat.csv");
    }
}
