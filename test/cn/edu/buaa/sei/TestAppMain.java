package cn.edu.buaa.sei;

import cn.edu.buaa.sei.util.ConfigMgr;
import junit.framework.TestCase;

public class TestAppMain extends TestCase {

    public TestAppMain()
    {
    }

    public void testMain()
    {
        ConfigMgr.setPath2Config("input/init2.json");
        AppMain.main(null);
    }
}
