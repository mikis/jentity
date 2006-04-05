package org.dataentity.datamodel.visitor;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/**
 * Attribute visitors are used for    
 * @author msn
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
     * Returns a copy of the supplied object. The deepth of the copy depends upon the concrete visitor 
     * implementation.
     * @return The copy of the supplied object
     */
    Object clone(Object obj);   
    
    Object readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
    
    void writeExternal(Serializable obj, ObjectOutput out) throws IOException;
    
    boolean isEqual(Object obj1, Object obj2);
    
    Object copy(Object obj2Copy);
    
    String toString(Object obj);
}
