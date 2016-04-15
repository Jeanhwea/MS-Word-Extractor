package cn.edu.buaa.sei.ltp;

import java.util.HashMap;

import org.apache.log4j.Logger;

import cn.edu.buaa.sei.AppMain;
import cn.edu.buaa.sei.util.HttpClient;
import cn.edu.buaa.sei.util.InitConfig;

public class LtpClient {

    @SuppressWarnings("unused")
    private Logger logger;
    private InitConfig conf;

    private String host;
    private String port;
    private String path;

    private HttpClient http;

    public LtpClient(AppMain app)
    {
        logger = app.getLogger();
        conf = app.getConfig();
        http = new HttpClient();
        initConfig();
    }


    private void initConfig()
    {
        host = conf.getLtp_server_host();
        port = conf.getLtp_server_port();
        path = conf.getLtp_server_path();
    }
    
    /**
     * LTP提交的POST请求相关字段的解释：</br>
     * x : 用以指明是否使用xml</br>
     * s : 输入字符串，在xml选项x为n的时候，代表输入句子；为y时代表输入xml</br>
     * t : 用以指明分析目标，t可以为分词（ws）,词性标注（pos），命名实体识别（ner），
     *     依存句法分析（dp），语义角色标注（srl），全部任务（all）
     * @param text 输入的字符串
     * @return 构造好的Map表
     */
    private HashMap<String, String> ltpEncodeArgs(String text)
    {
        HashMap<String, String> paras = new HashMap<String, String>();
        paras.put("x", "n");
        paras.put("s", text);
        paras.put("t", "all");
        return paras;
    }

    /**
     * 将sentence提交给分词服务器,返回处理后的xml输出
     * @param sentence 需要分词的句子
     * @return LTP服务器输出的xml
     */
    public String request(String sentence)
    {
        String str_xml = null;
        HashMap<String, String> args = ltpEncodeArgs(sentence);

        String data = http.buildParams(args);

        if (http.urlOpen(host, port, path)) {
            http.urlWriteData(data);
            str_xml = http.urlReadData();
            //logger.info(str_xml);
        }
        
        return str_xml;
    }


}
