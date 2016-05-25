package cn.edu.buaa.sei;

import cn.edu.buaa.sei.util.ConfigMgr;
import cn.edu.buaa.sei.util.LoggerMgr;
import cn.edu.buaa.sei.util.StopWatch;
import cn.edu.buaa.sei.word.DocxFileReader;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import java.io.IOException;


public class AppMain {

    private static AppConfig conf;
    private static Logger logger;

    public static void main(String[] args) {
        conf = ConfigMgr.getConfig();
        logger = LoggerMgr.getLogger();
        StopWatch watch = new StopWatch();
        logger.info("AppMain Starting ...");
        logger.trace(JSON.toJSONString(conf));
        // open and read doc or docx format files
        DocxFileReader reader = new DocxFileReader();
       // DocFileReader reader = new DocFileReader();
        try {
            String file_to_open = conf.getPath2WordInput() + conf.getInputFileName();
            reader.open(file_to_open);
           // reader.processParagraphs();
            reader.processTables();
            reader.close();
           // WordTitleNode json = reader.getDocxTop();
           // LtpStat stat = new LtpStat();
           // stat.startCount(json);
           // stat.printSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // logging elapsed time in seconds
        logger.info("AppMain finished, elapsedTimes: " + watch.getElapsedTimeInSeconds() + "s");
    }

}
