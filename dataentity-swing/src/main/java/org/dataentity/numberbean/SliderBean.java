package org.dataentity.numberbean;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import org.dataentity.CompositeView;
import org.dataentity.DefaultGUIBean;
import org.dataentity.EventGuard;
import org.dataentity.datamodel.ChangeListener;
import org.dataentity.datamodel.DataEntity;
import org.dataentity.datamodel.DataProcessor;
import org.dataentity.datamodel.ParameterEnum;

public class SliderBean extends CompositeView {
    protected final DataEntity model;
    protected final ParameterEnum valueParameter;
    protected final ParameterEnum minParameter;
    protected final ParameterEnum maxParameter;
    protected final JSlider view;
    private final EventGuard guard = new EventGuard();
	protected final DataProcessor processor = new DataProcessor("Text") {
        protected void processEntity(DataEntity dataEntity){
            // Does nothing, acts as root processor
        }
    };
    
    public SliderBean(final JSlider field,
                                  final DataEntity globalModel, 
                                  final ParameterEnum valueParameter,
                                  final ParameterEnum minParameter,
                                  final ParameterEnum maxParameter) {
        view = field;
        this.model = globalModel;
        this.valueParameter = valueParameter;
        this.minParameter = minParameter;
        this.maxParameter = maxParameter;
        addGUIBean(new DefaultGUIBean(model, 
        		new ChangeListener() {
        	public void handleUpdate(ChangeListener.ChangeEvent dataEntity) {
        		if (guard. getGuard()) {
        			if (dataEntity.getUpdateValues().isDefined(valueParameter)) {
        				view.setValue(((Integer)dataEntity.getUpdateValues().getAttribute(valueParameter)).intValue()); 
        			}
        			if (dataEntity.getUpdateValues().isDefined(minParameter)) {
        				view.setMinimum(((Integer)dataEntity.getUpdateValues().getAttribute(minParameter)).intValue()); 
        			}
        			if (dataEntity.getUpdateValues().isDefined(maxParameter)) {
        				view.setMaximum(((Integer)dataEntity.getUpdateValues().getAttribute(maxParameter)).intValue()); 
        			}
        		}
        		guard.releaseGuard();
        	}
        }));

        view.addChangeListener(new javax.swing.event.ChangeListener() {
        	public void stateChanged(ChangeEvent changeEvent) {
        		if (guard. getGuard()) {
        			DataEntity update = globalModel.createInstance();
        			Integer value = Integer.valueOf(((JSlider)changeEvent.getSource()).getValue());
        			if (!value.equals(model.getAttribute(valueParameter))) {
        				update.setAttribute(valueParameter, value);
        			}

        			Integer minimum = Integer.valueOf(((JSlider)changeEvent.getSource()).getMinimum());
        			if (!minimum.equals(model.getAttribute(minParameter))) {
        				update.setAttribute(minParameter, minimum);
        			}

        			Integer maximum = Integer.valueOf(((JSlider)changeEvent.getSource()).getMaximum());
        			if (!maximum.equals(model.getAttribute(maxParameter))) {
        				update.setAttribute(maxParameter, maximum);
        			}
        			processor.process(update);    
        			model.update(update);
        		}
        		guard.releaseGuard();
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
    
    public JSlider getView() {
        return view;
    }
}