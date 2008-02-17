package org.jentity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JRadioButton;

import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;

/**
 * Enables radiobuttons to be attached to a <code>DataEntity</code> model. The bean includes the normal button group 
 * functionality, where radiobutton binding to the same model parameter functions as a buttongroup.
 * @author msn
 */
public abstract class RadioButtonBean implements GUIBean {
    protected final DataEntity model;
    final JRadioButton button;
    protected ButtonController controller;
    protected final ChangeListener listener;
    final List additionalListeners = new LinkedList();
    private Object parameter;
    private Object value;
    
    public RadioButtonBean(Object param,
                           Object selectValue,
                           JRadioButton view,
                           DataEntity globalModel) {
        parameter = param;
        this.value = selectValue;
        this.button = view;
        this.model = globalModel;
        listener =  new ChangeListener() {
            public void handleUpdate(ChangeEvent change) {
                DataEntity modelChange = change.getUpdateValues();
                if (modelChange.isDefined(parameter)) {
                    if (value.equals(modelChange.getAttribute(parameter))) {
                        button.setSelected(true);
                    } else {
                        button.setSelected(false);
                    }
                }
            }
        };
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (button.isSelected()) {
                    controller.update(parameter, value);
                } else {
                    button.setSelected(true);
                }
            }
        });
        setController(new ButtonController(model));
    }
    public void setEnabled(boolean value) {
        button.setEnabled(value);
        button.setFocusable(value);
    }

    public void setController(ButtonController controller) {
        this.controller = controller;
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