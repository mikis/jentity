package org.jentity.datamodel;

public abstract class ParameterEnum extends org.apache.commons.lang.enums.Enum {
    protected ParameterEnum(String name) {
        super(name);
    }

	public String toString() {
		return getName();
	}
//	
//	/**
//	 * Returns the ParameterEnum with the specific name
//	 */
//	public abstract ParameterEnum getParameterEnum(String name);
}