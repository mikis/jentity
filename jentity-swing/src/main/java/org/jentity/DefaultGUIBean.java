package org.jentity;

import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;

/**
 * Implements the binding ofchangelister and model during attach/detachmodel calls.
 *
 */
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
