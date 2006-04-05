package org.dataentity.example;

import junit.framework.Test;

import org.jaccept.CaseLevelSuite;
import org.jaccept.ExtendedTestCase;
import org.jaccept.ExtendedTestSuite;

public class DataEntityDemo extends ExtendedTestCase {
    public DataEntityDemo(String string) {
        super(string);
    }
    
    public static Test suite() {
        ExtendedTestSuite suite= new CaseLevelSuite("Dataentity demo", DataEntityDemo.class);
        suite.addDescription("Contains a number of scenarions ment to demonstrate the power of the dataentity" +
                "pattern");
        suite.addTest(new DataEntityDemo("demo1"));
        return suite;
    }
    
    public void demo1() {
        addDescription("Shows a simple example of single attribute modifications");
        addStep("Construct a test dataentity with a Graphical View attached","");
        
        addStep("Set the first attribute of the dataentity", 
                "The GUI should be updated with the assigned value and a update notification should be received");
        
        addStep("Updated the GUI with a new value",
                "A notification of the update should be received");
    }    
}
