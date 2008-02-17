package org.jentity;

import javax.swing.JTextArea;

import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;

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