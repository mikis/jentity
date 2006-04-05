package org.dataentity.example;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jaccept.ExtendedTestSuite;
import org.jaccept.IntermediateLevelSuite;

public class ExampleTests extends TestSuite {    
    
    public ExampleTests(String name) {
        super(name);
    }

    public static Test suite() {
        ExtendedTestSuite suite= new IntermediateLevelSuite("ExampleSuite");
        suite.addTest(DataEntityDemo.suite());
        return suite;
    }     
}
