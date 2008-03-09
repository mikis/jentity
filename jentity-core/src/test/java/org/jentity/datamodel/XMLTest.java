package org.jentity.datamodel;

import static org.testng.AssertJUnit.assertEquals;

import java.text.ParseException;

import org.jentity.datamodel.xml.Counter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class XMLTest {
    protected TestEntity entity;
    
    @BeforeMethod
    protected void setUp() throws Exception {
    	entity = new TestEntity();
    }
    
    @Test
    public void testToXMLEmptyDataEntity() throws ParseException {
    	String xml = entity.toXML("");
    	assertEquals("Empty dataentity toXML invalid",
    			"<dataentity type=class org.jentity.datamodel.TestEntity>\n</dataentity>", xml);
    	DataEntity output = TestEntity.readFromXML(xml, new Counter());
    	assertEquals("Dataentity generated from empty xml not equal to original",
    			entity, output);
    }

    @Test
    public void testToXMLOneStringAtribute() throws ParseException {
    	entity.setAttribute1("Attibute 1");
    	
    	String xml = entity.toXML("");
    	assertEquals("Empty dataentity toXML invalid",
    			"<dataentity type=class org.jentity.datamodel.TestEntity>\n" +
    			"\t<attribute1>Attibute 1</attribute1>\n" +
    			"</dataentity>", xml);
    	DataEntity output = TestEntity.readFromXML(xml, new Counter());
    	assertEquals("Dataentity generated from string atribute xml not equal to original",
    			entity, output);
    }

    @Test
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
    	DataEntity output = TestEntity.readFromXML(xml, new Counter());
    	assertEquals("Dataentity generated from nested dataentity xml not equal to original",
    			entity, output);
    }
}
