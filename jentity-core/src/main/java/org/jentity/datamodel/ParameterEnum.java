package org.jentity.datamodel;

public abstract class ParameterEnum extends org.apache.commons.lang.enums.Enum {
    public ParameterEnum(String name) {
        super(name);
    }

	public String toString() {
		return getName();
	}
}