package org.jentity;

import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.DataProcessor;
import org.jentity.datamodel.ParameterEnum;

public class ButtonController {
    protected final DataEntity model;
    protected DataProcessor processor;

    public ButtonController(DataEntity model) {
        this.model = model;
        this.processor = new DataProcessor("Button") {
            protected void processEntity(DataEntity dataEntity) {
                // Does nothing, acts as root processor
            }
        };
    }
    
    public void update(ParameterEnum parameter, Object value) {
        DataEntity change = model.createInstance();
        change.setAttribute(parameter, value);
        processor.process(change);    
        model.update(change);
    }
    
    public void addSubProcessor(DataProcessor subprocesser) {
        processor.addSubprocessor(subprocesser);
    }
}