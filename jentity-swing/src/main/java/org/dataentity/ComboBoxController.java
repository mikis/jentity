package org.dataentity;

import org.dataentity.datamodel.DataEntity;
import org.dataentity.datamodel.DataProcessor;
import org.dataentity.datamodel.ParameterEnum;


public class ComboBoxController {
    protected final ParameterEnum parameter; 
    protected final DataEntity model;
    protected DataProcessor processor;
    
    
    ComboBoxController(ParameterEnum parameter, 
        DataEntity model) {
        this.parameter = parameter;
        this.model = model;
        this.processor = new DataProcessor("ComboBox") {
            protected void processEntity(DataEntity dataEntity){
                // Does nothing, acts as root processor
            }
        };
    }
    
    public void update(Object selectedItem) {
        if (!selectedItem.equals(model.getAttribute(parameter))) {
            DataEntity change = model.createInstance();
            change.setAttribute(parameter, selectedItem);
            processor.process(change);    
            model.update(change);
        }
    }
    
    public void addSubprocessor(DataProcessor subprocesser) {
        processor.addSubprocessor(subprocesser);
    }
    
    public ParameterEnum getParameter() {
        return parameter;
    }
}