package org.dataentity.example;

import javax.swing.JCheckBox;

import org.dataentity.CheckBoxBean;
import org.dataentity.CompositeView;
import org.dataentity.datamodel.TestEntity;

public class TestEntityUI extends CompositeView {
    private final TestEntity model;
    
    public TestEntityUI(TestEntity model) {
        this.model = model;
        add(createAttribute6Bean().getView());
    }
    
    private CheckBoxBean createAttribute6Bean() {
        CheckBoxBean bean = new CheckBoxBean(TestEntity.ATTRIBUTE6, new JCheckBox(), model );
        addGUIBean(bean);
        return bean;
    }
}
