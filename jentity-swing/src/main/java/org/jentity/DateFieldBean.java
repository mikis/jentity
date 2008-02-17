package org.jentity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.DataProcessor;
import org.jentity.datamodel.ParameterEnum;

public class DateFieldBean extends CompositeView {
	protected final DataEntity model;
	protected final ParameterEnum parameter;
	protected final JTextField view;
	protected final DateFormat dateFormat = DateFormat.getDateTimeInstance();
	protected TextController controller;

    private final EventGuard guard = new EventGuard();
	protected final DataProcessor processor = new DataProcessor("Text") {
        protected void processEntity(DataEntity dataEntity){
            // No default behavior, acts as root processor
        }
    };

	public DateFieldBean( DataEntity globalModel, 
			ParameterEnum param) {
		view = new JFormattedTextField(new DateFormatter(dateFormat));
		this.model = globalModel;
		this.parameter = param;
		addGUIBean(new DefaultGUIBean(model, 
				new ChangeListener() {
			public void handleUpdate(ChangeListener.ChangeEvent dataEntity) {
				if (guard. getGuard()) {
					if (dataEntity.getUpdateValues().isDefined(parameter)) {
						if (dataEntity.getUpdateValues().getAttribute(parameter) == null) {
							view.setText("");
						} else {
							view.setText(dateFormat.format((Date)dataEntity.getUpdateValues().getAttribute(parameter))); 
						}
					}
					guard.releaseGuard();
				}
			}
		}));

		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (guard. getGuard()) {
					DataEntity update = model.createInstance();
					try {
						update.setAttribute(parameter, dateFormat.parse(view.getText()));
					processor.process(update);    
					model.update(update);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					guard.releaseGuard();
				}
			}
		});
	}

	public void setEnabled(boolean value) {
		view.setEnabled(value);
		view.setFocusable(value);
	}

	public void addSubprocessor(DataProcessor subprocesser) {
		processor.addSubprocessor(subprocesser);
	}	

	public JTextField getView() {
		return view;
	}
}