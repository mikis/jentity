package org.jentity.datamodel.visitor;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.text.ParseException;

/**
 * Attribute visitors are used for adapting  
 */
public interface AttributeVisitor {
    
    /**
     * Applies the patch to the supplied original returning the updated object.
     * @param original The object to patch
     * @param patch The update
     * @return The updated object
     */
    Object patch(Object original, Object patch);
    
    /**
     * Returns a copy of the supplied object. The depth of the copy depends upon the concrete visitor 
     * implementation.
     * @return The copy of the supplied object
     */
    Object copy(Object obj2Copy);
    
    Object readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
    
    void writeExternal(Serializable obj, ObjectOutput out) throws IOException;    
    
    /**
     * 
     * @param input
     * @param counter The XML should be read from the counter onward. 
     * When return from operation the counter must be set to the end of the XML 
     * defining the attribute. 
     * @return
     * @throws ParseException
     */
    Object readFromXML(String input, int counter) throws ParseException;
    
    public String toXML(Object obj, String indentation);
    
    boolean isEqual(Object obj1, Object obj2);    
    
    String toString(Object obj);
}
