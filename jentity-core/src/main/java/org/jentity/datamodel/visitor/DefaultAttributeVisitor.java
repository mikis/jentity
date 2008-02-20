package org.jentity.datamodel.visitor;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.text.ParseException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DefaultAttributeVisitor implements AttributeVisitor {

    /**
     * Returns the supplied object. This operation should be override during subclassing if 
     * a 'real' copy is needed. 
     */
    public Object clone(Object obj) {
        return obj;
    }

    /**
     * Maps to the normal serialization operation, e.g. <code>readObject()</code>
     */
    public Object readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    /**
     * Maps to the normal serialization operation, e.g. <code>writeObject()</code>
     */
    public void writeExternal(Serializable obj, ObjectOutput out) throws IOException {
        out.writeObject(obj);
    }
    
    /**
     * Maps to the normal serialization operation, e.g. <code>readObject()</code>
     */
    public Object readFromXML(String input, int counter) throws ParseException {
        throw new NotImplementedException();
    }

    /**
     * Maps to the normal serialization operation, e.g. <code>writeObject()</code>
     */
    public String toXML(Object obj, String indentation) {
    	return obj.toString();
    }
    

    /**
     * Returns the patch itself. The default attribute behavior for patching is to replace the
     * original attribute with the patch.<p>
     * For more fine granulated patching override this method.
     * @param original
     * @param patch
     * @return
     */
    public Object patch(Object original, Object patch) {
        return patch;
    }

    /**
     * The default equal implementation, works for most types including arrays. Uses the commons.lang EqualBuilder and
     * is therefor not the fastests way of performing equal. If a fast equal is needed, overload.
     */
    public boolean isEqual(Object obj1, Object obj2) {
    	return new EqualsBuilder().append(obj1, obj2).isEquals();
	}   
    /**
     * The default hashcode implementation, works for most types including arrays. Uses the commons.lang HashCodeBuilder and
     * is therefor not the fastests way of performing hashcode. If a faster operation is needed, overload.
     */
    public int getHashCode(Object obj) {
    	return new HashCodeBuilder().append(obj).toHashCode();
    }
    
    /**
     * Maps to the <code>Object</code> 
     * @param obj
     * @return
     */
	public String toString(Object obj) {
		if (obj == null) return null;
		return obj.toString();
	}

    /**
     * The default implementation is to return the object itself.
     */
    public Object copy(Object obj2Copy) {
        return obj2Copy;
    }
}
