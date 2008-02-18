package org.jentity.datamodel.visitor;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.text.ParseException;

import org.apache.commons.lang.NotImplementedException;
import org.jentity.datamodel.DataEntity;

public class DataCoreVisitor implements AttributeVisitor {

    public Object clone(Object obj) {
        return ((DataEntity)obj).copy();
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

    public Object readFromXML(String input) throws ParseException {
		return null;
    }

    public String toXML(Object obj, String indentation) {
    	return ((DataEntity)obj).toXML(indentation);
    }
    
    /**
     * Updates the supplied original data entity with the patch and returns this.
     */
    public Object patch(Object original, Object patch) {
        if (patch == null) {
            return null;
        } else if (original == null) {
            return ((DataEntity)patch).copy();
        } else {
            ((DataEntity)original).update((DataEntity)patch);
            return original;
        }
    }

	public boolean isEqual(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) return true;
		if (obj1 == null || obj2 == null) return false;
		return obj1.equals(obj2);
	}

	public String toString(Object obj) {
		return ((DataEntity)obj).toString();
	}

    public Object copy(Object obj2Copy) {
        if (obj2Copy == null) return null;
        else return ((DataEntity)obj2Copy).copy();
    }
}
