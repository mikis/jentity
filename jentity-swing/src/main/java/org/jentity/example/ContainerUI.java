package org.jentity.example;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jentity.CompositeView;
import org.jentity.DataentityView;
import org.jentity.datamodel.Container;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.DataProcessor;
import org.jentity.numberbean.IntegerFieldBean;
import org.jentity.numberbean.SliderBean;

public class ContainerUI extends CompositeView {
    private final Container model;
    
    public ContainerUI(Container model) {
        this.model = model;
        this.setLayout(new SpringLayout());
        
        createTotalVolumeBean();
        createWaterVolumeBean();
        createEmptyVolumeBean();
        createContainerModelView();
        
        model.setPreprocessor(createWaterLevelProcessor());
        
        SpringUtilities.makeCompactGrid(this,
                4, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
    }
    
    private IntegerFieldBean createTotalVolumeBean() {
    	IntegerFieldBean bean = new IntegerFieldBean(new JTextField(10), model, Container. TOTALVOLUME);   
    	addField("Container size (l)", bean.getView());
        addGUIBean(bean);
        return bean;
    }
    
    private SliderBean createWaterVolumeBean() {
    	SliderBean bean = new SliderBean(new JSlider(), model, Container.WATERVOLUME, Container.MINWATERVOLUME , Container. TOTALVOLUME);       	
    	addField("Water (l)", bean.getView());
        addGUIBean(bean);
        return bean;
    }
    
    private SliderBean createEmptyVolumeBean() {
    	SliderBean bean = new SliderBean(new JSlider(), model, Container.EMPTYVOLUME, Container.MINWATERVOLUME , Container. TOTALVOLUME);   
    	addField("Empty (l)", bean.getView());
        addGUIBean(bean);
        return bean;
    }    

    private DataentityView createContainerModelView() {
    	DataentityView bean = new DataentityView(new JTextArea(10, 20), model);   
    	addField("Container model", bean.getView());
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
    private DataProcessor createWaterLevelProcessor() {
    	return new DataProcessor("Water volume processor")  {		
			protected void processEntity(DataEntity data) {
				Container change = (Container)data;
				if (change.isDefined(Container.WATERVOLUME)) {
					int newEmptyVolume = model.getTotalVolume()-change.getWaterVolume();
					change.setEmptyVolume(newEmptyVolume);
				} else if (change.isDefined(Container.EMPTYVOLUME)) {
					int newWaterVolume = model.getTotalVolume()-change.getEmptyVolume();
					change.setWaterVolume(newWaterVolume);
				} else if (change.isDefined(Container.TOTALVOLUME)) {
					if (change.getTotalVolume()>model.getWaterVolume()) {
						int newEmptyVolume = change.getTotalVolume()-model.getWaterVolume();
						change.setEmptyVolume(newEmptyVolume);
					} else {
						int newWaterVolume = change.getTotalVolume();
						change.setWaterVolume(newWaterVolume);
						change.setEmptyVolume(0);
					}
				}
			}		
		};
    }
}
