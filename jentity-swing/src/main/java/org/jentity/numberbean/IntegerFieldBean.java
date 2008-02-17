package org.jentity.numberbean;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import org.jentity.CompositeView;
import org.jentity.DefaultGUIBean;
import org.jentity.EventGuard;
import org.jentity.TextController;
import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.DataProcessor;
import org.jentity.datamodel.ParameterEnum;

public class IntegerFieldBean extends CompositeView {
	protected final DataEntity model;
	protected final ParameterEnum parameter;
	protected final JTextField view;
	protected TextController controller;
	private final EventGuard guard = new EventGuard();

	protected final DataProcessor processor = new DataProcessor("Integer") {
		protected void processEntity(DataEntity dataEntity){
			// Does nothing, acts as root processor
		}
	};

	public IntegerFieldBean(JTextField field,
			DataEntity globalModel, 
			ParameterEnum param) {
		view = field;
		this.model = globalModel;
		this.parameter = param;
		addGUIBean(new DefaultGUIBean(model, 
				new ChangeListener() {
			public void handleUpdate(ChangeListener.ChangeEvent dataEntity) {
				if (guard. getGuard()) {
					if (dataEntity.getUpdateValues().isDefined(parameter)) {
						view.setText(((Integer)dataEntity.getUpdateValues().getAttribute(parameter)).toString()); 
					}
					guard.releaseGuard();
				}
			}
		}));  

		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if (guard. getGuard()) {
					DataEntity update = model.createInstance();
					update.setAttribute(parameter, new Integer(view.getText()));
					processor.process(update);    
					model.update(update);
					guard.releaseGuard();
				}
			}
		});
	}
	public void setEnabled(boolean value) {
		view.setEnabled(value);
		view.setFocusable(value);
	}

	public JTextField getView() {
		return view;
	}

	public void addSubprocessor(DataProcessor subprocesser) {
		processor.addSubprocessor(subprocesser);
	}
}