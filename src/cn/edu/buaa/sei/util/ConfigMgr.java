package cn.edu.buaa.sei.util;

import java.io.File;

import com.alibaba.fastjson.JSON;

import cn.edu.buaa.sei.ds.AppConfig;

public class ConfigMgr {
    
    private static String PATH_TO_INIT_CONFIG = "input/init.json";
    private static AppConfig conf = null;

    public ConfigMgr()
    {

    }
    
    public static AppConfig getConfig()
    {
        if (null == conf) {
            conf = allocConfig();
        }
        return conf;
    }
    
    public static void setPath2Config(String filename)
    {
        PATH_TO_INIT_CONFIG = filename;
    }
    
    public static void write2File(String filename)
    {
        String text = JSON.toJSONString(conf);
        new GenericFileIO().write(text, filename);
    }

    private static AppConfig defaultConfig()
    {
        AppConfig appconfig = new AppConfig();
        appconfig.setAuthor("Jeanhwea");
        appconfig.setPath_to_word_input("/home/hujh/Shared/input/");
        appconfig.setPath_to_word_output("output/");
        appconfig.setInput_filename("00-631需求文档.docx");
        appconfig.setOutput_filename("simple.out");
        appconfig.setPath_to_log4j_properties("input/log4j.properties");
        appconfig.setLtp_server_host("127.0.0.1");
        appconfig.setLtp_server_path("/ltp");
        appconfig.setLtp_server_port("12345");
        return appconfig;
    }

    private static AppConfig allocConfig()
    {
        File file = new File(PATH_TO_INIT_CONFIG);
        if (!file.exists()) {
            return defaultConfig();
        } else {
            String text = new GenericFileIO().load(PATH_TO_INIT_CONFIG);
            return JSON.parseObject(text, AppConfig.class);
        }
    }
    
}
