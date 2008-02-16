package org.dataentity.datamodel.visitor;

import java.util.Arrays;

public class ObjectArrayAttributeVisitor extends DefaultAttributeVisitor {
    public boolean isEqual(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) return true;
		if (obj1 == null || obj2 == null) return false;
		if (obj1 instanceof Object[] && obj2 instanceof Object[]) {
			return Arrays.equals((Object[])obj1, (Object[])obj2);
		} else {
			return false;
		}
	}   
    
    public String toString(Object obj1) {
    	return Arrays.asList((Object[])obj1).toString();
    }
}
