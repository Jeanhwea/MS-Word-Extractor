package cn.edu.buaa.sei;

import cn.edu.buaa.sei.rucm.RucmReader;
import cn.edu.buaa.sei.util.ConfigMgr;
import cn.edu.buaa.sei.util.LoggerMgr;
import cn.edu.buaa.sei.util.SimpleUI;
import cn.edu.buaa.sei.util.StopWatch;
import org.apache.log4j.Logger;

/**
 * Created by hujh on 5/3/16.
 */
public class RucmViewer {
    private static AppConfig conf;
    private static Logger logger;


    public static void main(String[] args) {
        conf = ConfigMgr.getConfig();
        logger = LoggerMgr.getLogger();
        StopWatch watch = new StopWatch();
        RucmReader reader = new RucmReader();

        String path = conf.getPath2WordOutput() + "rucm";
        SimpleUI ui = new SimpleUI();
        String fileName = ui.chooseAFile(path);
        if (null != fileName) {
            String text = reader.processFile(fileName);
            logger.info(text);
            ui.showText(text, fileName);
        }

        logger.info("AppMain finished, elapsedTimes: " + watch.getElapsedTimeInSeconds() + "s");
    }
}
