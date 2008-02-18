package org.jentity.datamodel;

import junit.framework.TestCase;

public class XMLTest extends TestCase {
    protected final TestEntity entity = new TestEntity();
    
    public void testToXMLEmptyDataEntity() {
    	String xml = entity.toXML("");
    	assertEquals("Empty dataentity toXML invalid",
    			"<attribute type=class org.jentity.datamodel.TestEntity>\n</attribute>\n", xml);
    	DataEntity output = new TestEntity().readFromXML(xml);
    	assertEquals("Dataentity generated from empty xml not equal to original",
    			entity, output);
    }
    
    public void testToXMLOneStringAtribute() {
    	entity.setAttribute1("Attibute 1");
    	
    	String xml = entity.toXML("");
    	assertEquals("Empty dataentity toXML invalid",
    			"<attribute type=class org.jentity.datamodel.TestEntity>\n" +
    			"\t<attribute>\n"+
    			"\t\tAttibute 1\n"+
    			"\t</attribute>\n" +
    			"</attribute>\n", xml);
    	DataEntity output = new TestEntity().readFromXML(xml);
    	assertEquals("Dataentity generated from string atribute xml not equal to original",
    			entity, output);
    }
    
    public void testToXMLOneDataEntityAtribute() {
    	entity.setAttribute4(new TestEntity());
    	
    	String xml = entity.toXML("");
    	assertEquals("Empty dataentity toXML invalid",
    			"<attribute type=class org.jentity.datamodel.TestEntity>\n" +
    			"\t<attribute type=class org.jentity.datamodel.TestEntity>\n"+
    			"\t</attribute>\n" +
    			"</attribute>\n", xml);
    	DataEntity output = new TestEntity().readFromXML(xml);
    	assertEquals("Dataentity generated from nested dataentity xml not equal to original",
    			entity, output);
    }
}
