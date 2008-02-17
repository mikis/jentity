package org.jentity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;

import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.DataProcessor;

/**
 * Enables checkboxes to be attached to a <code>DataEntity</code> model. 
 */
public class CheckBoxBean implements GUIBean {
    protected final DataEntity model;
    final JCheckBox button;
    protected ButtonController controller;
    protected final ChangeListener listener;
    final List additionalListeners = new LinkedList();
    private Object parameter;
    private final EventGuard guard = new EventGuard();
    
	protected final DataProcessor processor = new DataProcessor("Text") {
        protected void processEntity(DataEntity dataEntity){
            // No default behavior, acts as root processor
        }
    };
    
    public CheckBoxBean(final Object param,
                        final JCheckBox view,
                        final DataEntity model) {
        parameter = param;
        this.button = view;
        this.model = model;
        listener =  new ChangeListener() {
            public void handleUpdate(ChangeEvent change) {
                DataEntity modelChange = change.getUpdateValues();
                if (modelChange.isDefined(parameter)) {
                    boolean newValue = false;
                    if (model.getAttribute(param) != null && ((Boolean)model.getAttribute(param)).booleanValue()) {
                        newValue = true;
                    }
                    button.setSelected(newValue);
                }
            }
        };
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (guard. getGuard()) {
        			DataEntity update = model.createInstance();
        			update.setAttribute(parameter, new Boolean(button.isSelected()));

        			processor.process(update);    
        			model.update(update);
        			guard.releaseGuard();
        		}
        	}
        });
    }
    
    public void setEnabled(boolean value) {
        button.setEnabled(value);
        button.setFocusable(value);
    }

	public void addSubprocessor(DataProcessor subprocesser) {
		processor.addSubprocessor(subprocesser);
	}	
    
    public AbstractButton getView() {
        return button;
    }
    
    public ButtonController getController() {
        return controller;
    }

    public void addModelListener(ChangeListener listener) {
        additionalListeners.add(listener);
    }
    
    public void attachToModel() {
        model.addListener(listener);
        for (Iterator iter = additionalListeners.iterator(); iter.hasNext();) {
            ChangeListener additionalListener = (ChangeListener) iter.next();
            model.addListener(additionalListener);
        }
    }
    public void detachFromModel() {
        model.removeListener(listener);
        for (Iterator iter = additionalListeners.iterator(); iter.hasNext();) {
            ChangeListener additionalListener = (ChangeListener) iter.next();
            model.removeListener(additionalListener);
        }
    }        
}