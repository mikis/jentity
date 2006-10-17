package org.dataentity;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.dataentity.datamodel.ChangeListener;
import org.dataentity.datamodel.DataEntity;
import org.dataentity.datamodel.ParameterEnum;

public class DataentityView extends CompositeView {
    protected final DataEntity model;
    protected final JTextArea view;
    protected TextController controller;

    public DataentityView(JTextArea field,
    		                            DataEntity globalModel) {
    	view = field;
    	this.model = globalModel;
    	addGUIBean(new DefaultGUIBean(model, 
    			new ChangeListener() {
    				public void handleUpdate(ChangeListener.ChangeEvent dataEntity) {
    						view.setText(model.toString()); 
    				}
    		}));
    }

    public JTextArea getView() {
        return view;
    }
}