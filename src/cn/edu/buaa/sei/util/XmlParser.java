package cn.edu.buaa.sei.util;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XmlParser {

    private static DocumentBuilder builder;
    private static Transformer transfor;

    public XmlParser() {
        init();
    }

    private void init() {
        DocumentBuilderFactory docu_fact = DocumentBuilderFactory.newInstance();
        TransformerFactory trans_fact = TransformerFactory.newInstance();
        try {
            builder = docu_fact.newDocumentBuilder();
            transfor = trans_fact.newTransformer();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将str_xml这个XML字符串转化成xml的Document对象
     *
     * @param str_xml 输入的字符串
     * @return Document对象
     */
    public Document parseStr2Xml(String str_xml) {
        Document doc = null;

        try {
            StringReader sreader = new StringReader(str_xml);
            InputSource input = new InputSource(sreader);
            doc = builder.parse(input);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    /**
     * 将doc这个Document对象转化成xml字符串
     *
     * @param doc 输入的Document对象
     * @return 转化成的xml字符串
     */
    public String parseXml2Str(Document doc) {
        String str_xml = null;

        try {
            // transfor.setOutputProperty("encoding", "GB23121"); 中文GBK编码
            transfor.setOutputProperty("encoding", "UTF-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            transfor.transform(new DOMSource(doc), new StreamResult(bos));
            str_xml = bos.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return str_xml;
    }

    /**
     * 读取xml文件，将文件转化成Document对象
     *
     * @param file 要读的文件
     * @return Document对象
     */
    public Document readXmlFile(File file) {
        if (!file.exists())
            return null;

        try {
            return builder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 读取xml文件，将文件转化成Document对象
     *
     * @param filename 要读的文件名
     * @return Document对象
     */
    public Document readXmlFile(String filename) {
        File file = new File(filename);
        return this.readXmlFile(file);
    }

    /**
     * 写入xml文件，将文件写入文件中
     *
     * @param doc  xml的Document类型
     * @param file 需要写入的文件
     * @return 是否成功
     */
    public boolean writeXmlFile(Document doc, File file) {
        GenericFileIO fio = new GenericFileIO();
        return fio.write(this.parseXml2Str(doc), file);
    }

    /**
     * 写入xml文件，将文件写入文件中
     *
     * @param doc      xml的Document类型
     * @param filename 需要写入的文件名
     * @return 是否成功
     */
    public boolean writeXmlFile(Document doc, String filename) {
        return this.writeXmlFile(doc, new File(filename));
    }

}
