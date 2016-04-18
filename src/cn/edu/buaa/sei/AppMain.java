package cn.edu.buaa.sei;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.edu.buaa.sei.ds.DocxJsonNode;
import cn.edu.buaa.sei.ds.AppConfig;
import cn.edu.buaa.sei.ltp.LtpStat;
import cn.edu.buaa.sei.util.ConfigMgr;
import cn.edu.buaa.sei.util.LoggerMgr;
import cn.edu.buaa.sei.word.DocxFileReader;


public class AppMain {
    
    private static AppConfig conf;
    private static Logger logger;
    
    public static void main(String[] args) 
    {
        conf = ConfigMgr.getConfig();
        logger = LoggerMgr.getLogger();
        long tStart = System.currentTimeMillis();
        logger.info("AppMain Starting ...");
        logger.trace("AppMain Starting trace ...");
        logger.trace(JSON.toJSONString(conf));
        
        DocxFileReader reader = new DocxFileReader();
        try {
            reader.open(null);
            reader.process();
            reader.close();

            DocxJsonNode json = reader.getDocxTop();
            LtpStat stat = new LtpStat();
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
    
}
