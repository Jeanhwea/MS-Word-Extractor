package cn.edu.buaa.sei.ltp;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.edu.buaa.sei.AppMain;
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
 * id 为段落中句子序号，其值从0 开始；</br>
 * cont 为句子内容；</br>
 * word 为分词结点，需含属性为id, cont；</br>
 * id 为句子中的词的序号，其值从0 开始，</br>
 * cont为分词内容；可选属性为 pos, ne, parent, relate；</br>
 * pos 的内容为词性标注内容；</br>
 * ne 为命名实体内容；</br>
 * parent 与relate 成对出现，parent 为依存句法分析的父亲结点id 号，relate 为相对应的关系；</br>
 * arg 为语义角色信息结点，任何一个谓词都会带有若干个该结点；其属性为id, type, beg，end；</br>
 * id 为序号，从0 开始；</br>
 * type 代表角色名称；</br>
 * beg 为开始的词序号，end 为结束的序号；</br>
 * @author hujh
 *
 */
public class LtpXmlParser {

    private XmlParser parser;
    private Document doc;
    private Logger logger;
    
    private Node node_note;
    private Node node_doc;

    public LtpXmlParser(AppMain app)
    {
        parser = new XmlParser();
        logger = app.getLogger();
    }

    public void process(String str_xml)
    {
        doc = parser.parseStr2Xml(str_xml);
        Element root = doc.getDocumentElement();
        Node note = null, doc = null;
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node nd = nodes.item(i);
            if (nd.getNodeType() == Node.ELEMENT_NODE) {
                if (nd.getNodeName().equals("note")) {
                    note = nd;
                }
                if (nd.getNodeName().equals("doc")) {
                    doc = nd;
                }
            }
        }
        logger.info(note.getAttributes().getNamedItem("pos").getNodeValue());
    }

}
