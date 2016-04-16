package cn.edu.buaa.sei;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.alibaba.fastjson.JSON;

import cn.edu.buaa.sei.ds.DocxJsonNode;
import cn.edu.buaa.sei.ds.InitConfig;
import cn.edu.buaa.sei.ltp.LtpStat;
import cn.edu.buaa.sei.word.DocxFileReader;


public class AppMain {
    
    private static Logger logger = null;
    private static InitConfig conf = null;
    
    public static void main(String[] args) 
    {
        AppMain app = new AppMain();
        long tStart = System.currentTimeMillis();
        logger.info("AppMain Starting ...");
        logger.trace(JSON.toJSONString(conf));
        
        DocxFileReader reader = new DocxFileReader(app);
        try {
            reader.open(null);
            reader.process();
            reader.close();

            DocxJsonNode json = reader.getDocxTop();
            LtpStat stat = new LtpStat(app);
            stat.startCount(json);
            stat.printSet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;
        logger.info("AppMain finished, elapsedTimes: " + elapsedSeconds + "s");
    }
    
    public AppMain() 
    {
        commonInit();
    }
    
    private void commonInit()
    {
        setDefaultConfig(null);
        logger = Logger.getLogger(AppMain.class);
        PropertyConfigurator.configure(conf.getPath_to_log4j_properties());
    }

    public InitConfig getConfig()
    {
        return conf;
    }
    
    public Logger getLogger()
    {
        return logger;
    }

    private void setDefaultConfig(String path_to_config)
    {
        File initJsonFile;
        if (null==path_to_config) {
            initJsonFile = new File("input/init.json");
        } else {
            initJsonFile = new File(path_to_config);
        }

        if (initJsonFile.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(initJsonFile));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line=br.readLine()) != null) {
                    sb.append(line);
                }
                conf = JSON.parseObject(sb.toString(), InitConfig.class);
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            conf = new InitConfig();
            conf.setAuthor("Jeanhwea");
            conf.setPath_to_word_input("/home/hujh/Shared/input/");
            conf.setPath_to_word_output("output/");
            conf.setInput_filename("00-631需求文档.docx");
            conf.setOutput_filename("simple.out");
            conf.setPath_to_log4j_properties("input/log4j.properties");
            conf.setLtp_server_host("127.0.0.1");
            conf.setLtp_server_path("/ltp");
            conf.setLtp_server_port("12345");
        }
    }
}
