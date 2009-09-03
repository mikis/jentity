package org.jentity;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.ParameterEnum;

public class ListBean extends CompositeView {
    protected final DataEntity model;
    protected final ListModel listModel = new ListModel();
    protected final ParameterEnum parameter;
    protected final JList view;
    protected TextController controller;
    
    public ListBean(JList field,
                    DataEntity globalModel, 
                    ParameterEnum param) {
    	field.setModel(listModel);
        view = field;
        this.model = globalModel;
        this.parameter = param;
        addGUIBean(new DefaultGUIBean(model, 
            new ChangeListener() {
            @SuppressWarnings("unchecked")
			public void handleUpdate(ChangeListener.ChangeEvent dataEntity) {
                if (dataEntity.getUpdateValues().isDefined(parameter)) {
                	List<Object> modelList = (List<Object>)dataEntity.getUpdateValues().getAttribute(parameter);
                	if (modelList != listModel.getModel()) { // A new list instance has been set in the model
                		listModel.setModel(modelList);
                	}
                }
            }
        }));

//        view.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                controller.update(view.getText());
//            }
//        });

        setController(new TextController(parameter, model));
    }
    public void setEnabled(boolean value) {
        view.setEnabled(value);
        view.setFocusable(value);
    }

    public void setController(TextController controller) {
        this.controller = controller;
    }        
    
    public JList getView() {
        return view;
    }
    
    public TextController getController() {
        return controller;
    }
    
    private class ListModel extends AbstractListModel {
    	List<Object> list;
    	
    	private void setModel(List<Object> list) {
    		this.list = list;
    	}
    	
    	private List<Object> getModel() {
    		return list;
    	}
    	
		public Object getElementAt(int arg0) {
			return list.get(arg0);
		}

		public int getSize() {
			return list.size();
		}    	
		
    }
}