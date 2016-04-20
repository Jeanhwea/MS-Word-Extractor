package cn.edu.buaa.sei.ltp;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.edu.buaa.sei.util.LoggerMgr;
import cn.edu.buaa.sei.util.XmlParser;

/**
 * LTML 标准要求如下：

 * 结点标签分别为 xml4nlp, note, doc, para, sent, word, arg 共七种结点标签：
 * xml4nlp 为根结点，无任何属性值；</br>
 * note 为标记结点，具有的属性分别为：sent, word, pos, ne, parser, srl； 
 *      分别代表分句，分词，词性标注，命名实体识别，依存句法分析，词义消歧，语义角色标注； 
 *      值为”n”，表明未做，值为”y”则表示完成，如pos=”y”，表示已经完成了词性标注；</br>
 * doc 为篇章结点，以段落为单位包含文本内容；无任何属性值；</br>
 * para 为段落结点，需含id 属性，其值从0 开始；</br>
 * sent 为句子结点，需含属性为id，cont；</br>
 *      id 为段落中句子序号，其值从0 开始；</br>
 *      cont 为句子内容；</br>
 * word 为分词结点，需含属性为id, cont；</br>
 *      id 为句子中的词的序号，其值从0 开始，</br>
 *      cont为分词内容；可选属性为 pos, ne, parent, relate；</br>
 *          pos 的内容为词性标注内容；</br>
 *          ne 为命名实体内容；</br>
 *          parent 与relate 成对出现，parent 为依存句法分析的父亲结点id 号，relate 为相对应的关系；</br>
 *          arg 为语义角色信息结点，任何一个谓词都会带有若干个该结点；其属性为id, type, beg，end；</br>
 *              id 为序号，从0 开始；</br>
 *              type 代表角色名称；</br>
 *              beg 为开始的词序号，end 为结束的序号；</br>
 * @author hujh
 *
 */
public class LtpXmlParser {

    private XmlParser parser;
    private Document doc;
    private Logger logger;
    private HashSet<String> pos_filter;

    public LtpXmlParser()
    {
        parser = new XmlParser();
        logger = LoggerMgr.getLogger();
        this.initFilter();
        logger.trace("LtpXmlParser create ...");
    }
    
    private void initFilter()
    {
        pos_filter = new HashSet<String>();
        pos_filter.add("wp");
    }
    
    private String getAttrValueByName(Node node, String attrname)
    {
        NamedNodeMap attrs;
        if (null==(attrs=node.getAttributes())) {
            return null;
        } else {
            return attrs.getNamedItem(attrname).getNodeValue();
        }
    }
    
    private void locateNodes(String xml)
    {
        doc = parser.parseStr2Xml(xml);
//        Element root = doc.getDocumentElement();
//        NodeList nodes = root.getChildNodes();
//        for (int i = 0; i < nodes.getLength(); ++i) {
//            Node nd = nodes.item(i);
//            if (nd.getNodeType() == Node.ELEMENT_NODE) {
//                if (nd.getNodeName().equals("note")) {
//                }
//                if (nd.getNodeName().equals("doc")) {
//                }
//            }
//        }
    }
    
    public HashMap<String, String> parseNoteTag(Node note)
    {
        if (null==note) return null;
        if (!note.getNodeName().equals("note")) return null;
        HashMap<String, String> map_note = new HashMap<String, String>();
        NamedNodeMap attr = note.getAttributes();
        String[] all_keys = {"sent", "word", "pos", "ne", "parser", "srl"};
        for (String key : all_keys) {
            map_note.put(key, attr.getNamedItem(key).getNodeValue());
        }
        return map_note;
    }

    public void process(String xml, LtpStat stat)
    {
        locateNodes(xml);
        NodeList words = doc.getElementsByTagName("word");
        for (int i = 0; i < words.getLength(); ++i) {
            Node wd = words.item(i);
            String text = getAttrValueByName(wd, "cont");
            String pos = getAttrValueByName(wd, "pos");
            if (!pos_filter.contains(pos)) {
                stat.countWords(text);
            }
        }
    }

}
