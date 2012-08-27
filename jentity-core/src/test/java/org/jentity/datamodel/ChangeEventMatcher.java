package org.jentity.datamodel;

import org.mockito.ArgumentMatcher;

public class ChangeEventMatcher extends ArgumentMatcher<ChangeListener.ChangeEvent> {
	private final DataEntity update;

	public ChangeEventMatcher(DataEntity update) {
        this.update = update;
    }
	
	public boolean matches(Object arg0) {
		ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent)arg0;
		 return update.equals(changeEvent.getUpdateValues());
	}
}
