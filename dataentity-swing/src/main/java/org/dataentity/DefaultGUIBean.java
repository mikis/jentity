package org.dataentity;

import org.dataentity.datamodel.ChangeListener;
import org.dataentity.datamodel.DataEntity;

public class DefaultGUIBean implements GUIBean {
    private final DataEntity model;
    private final ChangeListener listener;
    
    public DefaultGUIBean(DataEntity model, ChangeListener listener) {
        this.model = model;
        this.listener = listener;
    }
    
    public void attachToModel() {
        model.addListener(listener);
    }

    public void detachFromModel() {
        model.removeListener(listener);
    }

}
