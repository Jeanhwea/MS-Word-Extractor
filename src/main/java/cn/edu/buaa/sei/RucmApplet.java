package cn.edu.buaa.sei;


import cn.edu.buaa.sei.rucm.RucmReader;
import cn.edu.buaa.sei.util.ConfigMgr;
import cn.edu.buaa.sei.util.LoggerMgr;
import cn.edu.buaa.sei.util.StopWatch;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by hujh on 5/2/16.
 */
public class RucmApplet {
    private static AppConfig conf;
    private static Logger logger;


    public static void main(String[] args) {
        conf = ConfigMgr.getConfig();
        logger = LoggerMgr.getLogger();
        StopWatch watch = new StopWatch();
        RucmReader reader = new RucmReader();


        String path = conf.getPath2WordOutput() + "rucm";
        String[] files = new File(path).list();
        for (String file : files) {
            reader.processFile(path + "/" + file);
        }

        logger.info("AppMain finished, elapsedTimes: " + watch.getElapsedTimeInSeconds() + "s");
    }
}
