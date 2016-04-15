package cn.edu.buaa.sei;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.alibaba.fastjson.JSON;

import cn.edu.buaa.sei.ltp.LtpXmlParser;
import junit.framework.TestCase;

public class TestAppMain extends TestCase {

    public TestAppMain()
    {
    }

    public void testMain()
    {
        final AppMain app = new AppMain();
        app.getLogger().info("AppMain Starting ...");
        //app.getLogger().trace(JSON.toJSONString(app.getConfig()));
        
        LtpXmlParser ltp_parser = new LtpXmlParser(app);
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader breader = new BufferedReader(new FileReader("logs/test.xml"));
            String line = null;
            while ((line = breader.readLine())!=null) {
                sb.append(line);
            }
            breader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ltp_parser.process(sb.toString());
    }
}
