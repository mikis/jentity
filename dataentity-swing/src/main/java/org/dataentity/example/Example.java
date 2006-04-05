package org.dataentity.example;


import javax.swing.JFrame;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jaccept.ExtendedTestSuite;
import org.jaccept.RootLevelSuite;
import org.jaccept.TestEventLogger;
import org.jaccept.TestEventManager;
import org.jaccept.gui.ComponentTestFrame;

public class Example extends TestSuite {
        
    public static Test suite() {
        JFrame hmi = new ComponentTestFrame();
        hmi.pack();
        hmi.setVisible(true);
        ExtendedTestSuite suite= new RootLevelSuite("Dataentity demo");
        TestEventManager.addTestListener(new TestEventLogger());
        suite.addTest(ExampleTests.suite());
        return suite;
    }     
    
    
}
