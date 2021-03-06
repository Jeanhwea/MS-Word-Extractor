package cn.edu.buaa.sei.util;

import cn.edu.buaa.sei.AppMain;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerMgr {

    private static final Class<AppMain> LOGGER_CLASS = AppMain.class;
    private static final Logger logger = allocLogger();

    public LoggerMgr() {
    }

    public static Logger getLogger() {
        return logger;
    }

    private static Logger allocLogger() {
        Logger ret = Logger.getLogger(LOGGER_CLASS);
        String logger4j_filename = ConfigMgr.getConfig().getPath2Log4jProperties();
        PropertyConfigurator.configure(logger4j_filename);
        return ret;
    }

}
