package org.jentity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.ParameterEnum;

public abstract class ComboBoxBean extends CompositeView {
    protected final DataEntity model;
    protected final ParameterEnum selectedParameter;
    protected final ParameterEnum elementListParameter;
    protected final JComboBox view;
    protected ComboBoxController controller;
    
    public ComboBoxBean(JComboBox combobox,
                        DataEntity globalModel, 
                        ParameterEnum selectedParam,
                        ParameterEnum elementListParam) {
        view = combobox;
        this.model = globalModel;
        this.selectedParameter = selectedParam;
        this.elementListParameter = elementListParam;
        addGUIBean(new DefaultGUIBean(model, 
            new ChangeListener() {
            public void handleUpdate(ChangeListener.ChangeEvent dataEntity) {
                if (dataEntity.getUpdateValues().isDefined(elementListParameter)) {
                    view.setModel(new DefaultComboBoxModel(
                        (Object[])dataEntity.getUpdateValues().getAttribute(elementListParameter))); 
                }
                if (dataEntity.getUpdateValues().isDefined(selectedParameter)) {
                    view.setSelectedItem(dataEntity.getUpdateValues().getAttribute(selectedParameter)); 
                }
            }
        }));

        view.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.update(view. getSelectedItem());
            }
        });

        setController(new ComboBoxController(selectedParam, model));
    }
    public void setEnabled(boolean value) {
        view.setEnabled(value);
        view.setFocusable(value);
    }

    public void setController(ComboBoxController controller) {
        this.controller = controller;
    }        
    
    public JComboBox getView() {
        return view;
    }
    
    public ComboBoxController getController() {
        return controller;
    }
    
    protected abstract void handleValidationErrorMessage(String message);
}