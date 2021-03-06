package cn.edu.buaa.sei.util;

import cn.edu.buaa.sei.AppConfig;
import com.alibaba.fastjson.JSON;

import java.io.File;

public class ConfigMgr {

    private static String PATH_TO_INIT_CONFIG = "input/init.json";
    private static AppConfig conf = null;

    public ConfigMgr() {

    }

    public static AppConfig getConfig() {
        if (null == conf) {
            conf = allocConfig();
        }
        return conf;
    }

    public static void setPath2Config(String filename) {
        PATH_TO_INIT_CONFIG = filename;
    }

    public static void write2File(String filename) {
        String text = JSON.toJSONString(conf);
        new GenericFileIO().write(text, filename);
    }

    private static AppConfig defaultConfig() {
        AppConfig appconfig = new AppConfig();
        appconfig.setAuthor("Jeanhwea");
        appconfig.setPath2WordInput("/home/hujh/Shared/input/");
        appconfig.setPath2WordOutput("output/");
        appconfig.setInputFileName("00-631需求文档.docx");
        appconfig.setOutputFileName("simple.out");
        appconfig.setPath2Log4jProperties("input/log4j.properties");
        appconfig.setLtpServerHost("127.0.0.1");
        appconfig.setLtpServerPort("12345");
        appconfig.setLtpServerPath("/ltp");
        return appconfig;
    }

    private static AppConfig allocConfig() {
        File file = new File(PATH_TO_INIT_CONFIG);
        if (!file.exists()) {
            return defaultConfig();
        } else {
            String text = new GenericFileIO().load(PATH_TO_INIT_CONFIG);
            return JSON.parseObject(text, AppConfig.class);
        }
    }

}
