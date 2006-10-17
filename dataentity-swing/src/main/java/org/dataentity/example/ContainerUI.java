package org.dataentity.example;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.dataentity.CompositeView;
import org.dataentity.DataentityView;
import org.dataentity.datamodel.Container;
import org.dataentity.numberbean.IntegerFieldBean;
import org.dataentity.numberbean.SliderBean;

public class ContainerUI extends CompositeView {
    private final Container model;
    
    public ContainerUI(Container model) {
        this.model = model;
//        BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(new SpringLayout());
        
        createTotalVolumeBean();
        createWaterVolumeBean();
        createEmptyVolumeBean();
        createContainerModelView();
        
        SpringUtilities.makeCompactGrid(this,
                4, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        
    }
    
    private IntegerFieldBean createTotalVolumeBean() {
    	IntegerFieldBean bean = new IntegerFieldBean(new JTextField(10), model, Container. TOTALVOLUME);   
    	addField("Container size", bean.getView());
        addGUIBean(bean);
        return bean;
    }
    
    private SliderBean createWaterVolumeBean() {
    	SliderBean bean = new SliderBean(new JSlider(), model, Container.WATERVOLUME, Container.MINWATERVOLUME , Container. TOTALVOLUME);   
    	addField("Water", bean.getView());
        addGUIBean(bean);
        return bean;
    }
    
    private SliderBean createEmptyVolumeBean() {
    	SliderBean bean = new SliderBean(new JSlider(), model, Container.EMPTYVOLUME, Container.MINWATERVOLUME , Container. TOTALVOLUME);   
    	addField("Empty", bean.getView());
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
//        JPanel linePanel = new JPanel(new  GridLayout( 1,2 ));
//        linePanel.add(new JLabel(name));
//        linePanel.add(view);
        add(new JLabel(name));
        add(view);
    }
}
