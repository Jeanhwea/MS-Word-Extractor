package cn.edu.buaa.sei;

import junit.framework.TestCase;

public class TestAppMain extends TestCase {

    public TestAppMain()
    {
    }

    public void testMain()
    {
        final AppMain app = new AppMain();
        app.getLogger().info("AppMain Starting ...");
   }
}
