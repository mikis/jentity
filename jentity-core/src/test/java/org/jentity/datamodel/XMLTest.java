package org.jentity.datamodel;

import java.text.ParseException;

import junit.framework.TestCase;

public class XMLTest extends TestCase {
    protected final TestEntity entity = new TestEntity();
    
    public void testToXMLEmptyDataEntity() throws ParseException {
    	String xml = entity.toXML("");
    	assertEquals("Empty dataentity toXML invalid",
    			"<dataentity type=class org.jentity.datamodel.TestEntity>\n</dataentity>", xml);
    	DataEntity output = TestEntity.readFromXML(xml, 0);
    	assertEquals("Dataentity generated from empty xml not equal to original",
    			entity, output);
    }
    
    public void testToXMLOneStringAtribute() throws ParseException {
    	entity.setAttribute1("Attibute 1");
    	
    	String xml = entity.toXML("");
    	assertEquals("Empty dataentity toXML invalid",
    			"<dataentity type=class org.jentity.datamodel.TestEntity>\n" +
    			"\t<attribute1>Attibute 1</attribute1>\n" +
    			"</dataentity>", xml);
    	DataEntity output = TestEntity.readFromXML(xml, 0);
    	assertEquals("Dataentity generated from string atribute xml not equal to original",
    			entity, output);
    }
    
    public void testToXMLOneDataEntityAtribute() throws ParseException {
    	entity.setAttribute4(new TestEntity());
    	
    	String xml = entity.toXML("");
    	assertEquals("Empty dataentity toXML invalid",
    			"<dataentity type=class org.jentity.datamodel.TestEntity>\n" +
    			"\t<attribute4>\n"+
    			"\t\t<dataentity type=class org.jentity.datamodel.TestEntity>\n"+
    			"\t\t</dataentity>\n" +
    			"\t</attribute4>\n"+
    			"</dataentity>", xml);
    	DataEntity output = TestEntity.readFromXML(xml, 0);
    	assertEquals("Dataentity generated from nested dataentity xml not equal to original",
    			entity, output);
    }
}
