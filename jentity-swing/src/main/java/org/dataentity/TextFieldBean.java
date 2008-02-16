package org.dataentity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import org.dataentity.datamodel.ChangeListener;
import org.dataentity.datamodel.DataEntity;
import org.dataentity.datamodel.ParameterEnum;

public class TextFieldBean extends CompositeView {
    protected final DataEntity model;
    protected final ParameterEnum parameter;
    protected final JTextField view;
    protected TextController controller;
    
    public TextFieldBean(JTextField field,
                        DataEntity globalModel, 
                        ParameterEnum param) {
        view = field;
        this.model = globalModel;
        this.parameter = param;
        addGUIBean(new DefaultGUIBean(model, 
            new ChangeListener() {
            public void handleUpdate(ChangeListener.ChangeEvent dataEntity) {
                if (dataEntity.getUpdateValues().isDefined(parameter)) {
                    view.setText((String)dataEntity.getUpdateValues().getAttribute(parameter)); 
                }
            }
        }));

        view.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.update(view.getText());
            }
        });

        setController(new TextController(parameter, model));
    }
    public void setEnabled(boolean value) {
        view.setEnabled(value);
        view.setFocusable(value);
    }

    public void setController(TextController controller) {
        this.controller = controller;
    }        
    
    public JTextField getView() {
        return view;
    }
    
    public TextController getController() {
        return controller;
    }
}