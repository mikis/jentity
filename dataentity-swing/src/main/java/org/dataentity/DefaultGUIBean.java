package org.dataentity;

import org.dataentity.datamodel.ChangeListener;
import org.dataentity.datamodel.DataEntity;

public class DefaultGUIBean implements GUIBean {
    private final DataEntity model;
    private final ChangeListener listener;
    private boolean attached;
    
    public DefaultGUIBean(DataEntity model, ChangeListener listener) {
        this.model = model;
        this.listener = listener;
    }
    
    public void attachToModel() {
    	assert !attached;
    	attached = true;
        model.addListener(listener);
    }

    public void detachFromModel() {
        assert attached;
        attached = false;
        model.removeListener(listener);
    }

}
