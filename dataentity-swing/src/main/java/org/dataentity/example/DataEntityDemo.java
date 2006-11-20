package org.dataentity.example;

import javax.swing.JFrame;

import org.dataentity.datamodel.ChangeListener;
import org.dataentity.datamodel.Container;
import org.dataentity.datamodel.Valve;

/**
 * Demo runner. Composes of factory methods for the three components and a main section which constructs the demo.
 * @author mikis
 *
 */
public class DataEntityDemo  {

	private Container createContainer() {
    	System.out.println("Shows a simple example of single attribute modifications");
        
    	System.out.println("Construct a test dataentity with a Graphical View attached");
        Container container = new Container();
        
        //Initialize model
        container.setTotalVolume(1000);
        container.setWaterVolume(900);
        container.setMinWaterVolume(0);
        
        ContainerUI containerUI = new ContainerUI(container);

    	JFrame frame = new JFrame("Container view");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(containerUI);
        frame.pack();
        frame.setVisible(true);
        
        System.out.println("Attach a model listener,  A update containing the initial state should be received");
        container.addListener(new ChangeListener() {
			public void handleUpdate(ChangeEvent change) {				
				System.out.println("Received newValue : "+change.getUpdateValues().toString());
			}        	
        });
        containerUI.attachToModel();
        
        return container;
    }    
    
    private Valve createValve()  {
        Valve valve = new Valve();
        
        //Initialize model
        valve.setFlow(3);
        valve.setOpen(true);
        
        ValveUI valveUI = new ValveUI(valve);
        
        
    	JFrame valveFrame = new JFrame("Valve view");
    	valveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	valveFrame.getContentPane().add(valveUI);
    	valveFrame.pack();
    	valveFrame.setVisible(true);
        
        System.out.println("Attach a valve model listener,  A update containing the initial state should be received");
        valve.addListener(new ChangeListener() {
			public void handleUpdate(ChangeEvent change) {				
				System.out.println("Received newValue : "+change.getUpdateValues().toString());
			}        	
        });
        valveUI.attachToModel();       
        
        return valve;
    }    
    
    private void createFlowAgent(Container container, Valve valve)  {
    	FlowAgent flowAgent;
        flowAgent = new FlowAgent(container, valve);
		flowAgent.start();
    }    
    
    public static void main(String[] args) {
    	System.out.println("Demonstrates the use of the dataentity framework");
    	DataEntityDemo demo = new DataEntityDemo();
    	
    	// Compose the Demo 
    	Container container = demo.createContainer();
    	Valve valve = demo.createValve();
    	demo.createFlowAgent(container, valve);	
    }     
}
