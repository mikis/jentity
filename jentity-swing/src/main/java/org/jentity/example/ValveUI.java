package org.jentity.example;

import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jentity.CheckBoxBean;
import org.jentity.CompositeView;
import org.jentity.DataentityView;
import org.jentity.DateFieldBean;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.DataProcessor;
import org.jentity.datamodel.Valve;
import org.jentity.numberbean.IntegerFieldBean;

public class ValveUI extends CompositeView {
    private final Valve model;
    
    public ValveUI(Valve model) {
        this.model = model;
        this.setLayout(new SpringLayout());
        
        CheckBoxBean openBean = createOpenBean();
        createTimeOpenedBean();
        createFlowBean();
        createValveModelView();
        
        DataProcessor openProcessor = createOpenProcessor();
        openBean.addSubprocessor(openProcessor);
        
        SpringUtilities.makeCompactGrid(this,
                4, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        
    }
    
    private CheckBoxBean createOpenBean() {
    	CheckBoxBean bean = new CheckBoxBean(Valve.OPEN, new JCheckBox(), model);
    	addField("Open", bean.getView());
        addGUIBean(bean);
        return bean;
    }
    
    private DateFieldBean createTimeOpenedBean() {
    	DateFieldBean bean = new DateFieldBean(model, Valve.TIMEOPENED);    
    	bean.setEnabled(false);
    	addField("Time opened", bean.getView());
        addGUIBean(bean);
        return bean;
    }    
    
    private IntegerFieldBean createFlowBean() {
    	IntegerFieldBean bean = new IntegerFieldBean(new JTextField(10), model, Valve.FLOW);    
    	addField("Flow (l/s)", bean.getView());
        addGUIBean(bean);
        return bean;
    }    

    private DataentityView createValveModelView() {
    	DataentityView bean = new DataentityView(new JTextArea(10, 20), model);   
    	addField("Valve model", bean.getView());
        addGUIBean(bean);
        return bean;
    }
    
    private void addField(String name, JComponent view) {
        add(new JLabel(name));
        add(view);
    }
    
    /**
     * Maintains TotalVolume = WaterVolume + EmptyVolume with TotalVolume as the invariable. If TotalVolume is changed the empty volume is changed, when no empty volume remains
     * watervolume is used.
     * @return
     */
    private DataProcessor createOpenProcessor() {
    	return new DataProcessor("Valve processor")  {		
			protected void processEntity(DataEntity data) {
				Valve change = (Valve)data;
				if (change.isDefined(Valve.OPEN)) {
					if (change.getOpen()) {
						change.setTimeOpened(new Date());
					} else {
						change.setTimeOpened(null);	
					}
				} 
			}		
		};
    }
}
