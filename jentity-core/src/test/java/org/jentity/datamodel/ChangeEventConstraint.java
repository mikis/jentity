package org.jentity.datamodel;

import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;
import org.jmock.core.Constraint;

public class ChangeEventConstraint implements Constraint {
	private DataEntity update; 

	public ChangeEventConstraint(DataEntity update) {
        this.update = update;
    }
	
	public boolean eval(Object arg0) {
		ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent)arg0;
		 return update.equals(changeEvent.getUpdateValues());
	}

	public StringBuffer describeTo(StringBuffer buffer) {
		return buffer.append(update);
	}
}
