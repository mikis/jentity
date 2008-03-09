package org.jentity.datamodel;

import java.util.LinkedList;

import org.testng.annotations.*;

import static org.testng.AssertJUnit.*;

import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.TestEntity;

public class DataCoreTest {
    protected TestEntity entity;
    protected ChangeEventQueue eventQueue; 

    @BeforeMethod
    protected void setUp() throws Exception {
    	entity = new TestEntity();
        entity.addListener(eventQueue = new ChangeEventQueue());
    }
    
    @AfterMethod
    protected void tearDown() throws Exception {
        entity.removeListener(eventQueue);
    }

    @Test
    public void testSetGet() {
        assertNull("Actual date was not null initially", entity.getAttribute1());
        
        TestEntity change = new TestEntity();
        
        String value = "This is a test value";
        
        change.setAttribute1(value);
        entity.update(change);
        
        assertEquals("Wrong actual date after update", value, entity.getAttribute1());
    }

    /**
     * Verifies equals functionality.
     * Note: If the default attribute equals has been overloaded, 
     * you should use different instances of the attribute when testing the equals functionality.
     */
    @Test
    public void testEquals() {
        DataEntity model2 = new TestEntity();
        assertTrue("Empty entity not equals to other empty object", entity.equals(model2));    

        String value1 = "This is a test value";    
        entity.setAttribute(TestEntity.ATTRIBUTE1, value1);
        assertTrue("Equals returned true for different objects with one string value", !entity.equals(model2)); 
        model2.setAttribute(TestEntity.ATTRIBUTE1, new String(value1));
        assertTrue("Entity not equals to similar", entity.equals(model2));  
        
        String[] value3 = new String[] { value1 };      
        entity.setAttribute(TestEntity.ATTRIBUTE3, value3);
        assertTrue("Equals returned true for different objects with one string value", !entity.equals(model2)); 
        model2.setAttribute(TestEntity.ATTRIBUTE3, new String[] { value1 });
        assertTrue("Equals returned false for simlilar entities containing array attributes", entity.equals(model2));   
    }

    @Test
    public void testCopy() {
        String value = "Attribute1";
        entity.setAttribute(TestEntity.ATTRIBUTE1, value);
        entity.setAttribute(TestEntity.ATTRIBUTE2, "Attribute2");
        
        DataEntity copy = entity.copy();
        assertTrue("Same reference efter kopiering", copy != entity);
        assertEquals("Equals() returnerede false for kopi", entity, copy);
        
        copy.setAttribute(TestEntity.ATTRIBUTE1, "Attribute1.2");
        assertEquals("Attribute ændring i kopi påvirkede original", value, entity.getAttribute(TestEntity.ATTRIBUTE1));
    }

    @Test
    public void testUpdate() {
        entity.setAttribute(TestEntity.ATTRIBUTE1, "Attribute1");
        entity.setAttribute(TestEntity.ATTRIBUTE2, "Attribute2");
        
        DataEntity change = new TestEntity();
        change.setAttribute(TestEntity.ATTRIBUTE1, "Attribur1.2");

        entity.update(change);
        assertEquals("Attribute was not update", change.getAttribute(TestEntity.ATTRIBUTE1), entity.getAttribute1());
    }

    @Test
    public void testCompositeUpdate() {
        DataEntity nestedDataEntity = new TestEntity();
        String attribute2 = "Attribute2";
        nestedDataEntity.setAttribute(TestEntity.ATTRIBUTE1, "Attribute1");
        nestedDataEntity.setAttribute(TestEntity.ATTRIBUTE2, attribute2);
        entity.setAttribute(TestEntity.ATTRIBUTE4, nestedDataEntity);
        
        DataEntity nestedUpdate = new TestEntity();
        String newValue = "Attribute1.2";
        nestedUpdate.setAttribute(TestEntity.ATTRIBUTE1, newValue);
        TestEntity update = new TestEntity();
        update.setAttribute(TestEntity.ATTRIBUTE4, nestedUpdate);
        
        entity.update(update);
        assertEquals("The attribute in the nested entity wasn't updated", newValue, entity.getAttribute4().getAttribute(TestEntity.ATTRIBUTE1));
        assertEquals("The second attibute in the nnested entity was changed", attribute2, entity.getAttribute4().getAttribute(TestEntity.ATTRIBUTE2));
    }

    @Test
    public void testListener() {
        DataEntity change = entity.copy();
        String value = "This is a test value";
        
        assertEquals("Wrong initial update", change, eventQueue.popEvent().getUpdateValues());     
        
        change.setAttribute(TestEntity.ATTRIBUTE1, value);
        entity.update(change);
        
        assertEquals("Wrong update received from model", change, eventQueue.popEvent().getUpdateValues());        
    }
   
    @Test
    private class ChangeEventQueue implements ChangeListener {
        LinkedList eventList = new LinkedList();
        ChangeListener.ChangeEvent popEvent() {
            return (ChangeListener.ChangeEvent) eventList.getLast();
        }
        
        void addEvent(ChangeListener.ChangeEvent event) {
            eventList.add(event);
        }
        
        public void handleUpdate(ChangeListener.ChangeEvent change) {
            addEvent(change);
        }          
    }
}
