package org.dataentity.example;

import javax.swing.JFrame;

import org.dataentity.datamodel.ChangeListener;
import org.dataentity.datamodel.Container;
import org.dataentity.datamodel.Valve;

public class DataEntityDemo  {
	private JFrame frame = new JFrame("Dataentity demo");
	private FlowAgent flowAgent;
    
    public void simpleGUI() throws InterruptedException {
    	System.out.println("Shows a simple example of single attribute modifications");
        
    	System.out.println("Construct a test dataentity with a Graphical View attached");
        Container container = new Container();
        
        //Initialize model
        container.setTotalVolume(1000);
        container.setWaterVolume(900);
        container.setMinWaterVolume(0);
        
        ContainerUI containerUI = new ContainerUI(container);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(containerUI);
        frame.pack();
        frame.setVisible(true);
        
        Valve valve = new Valve();
        
        //Initialize model
        valve.setFlow(3);
        valve.setOpen(true);
        
        ValveUI valveUI = new ValveUI(valve);
        
        flowAgent = new FlowAgent(container, valve);
        
    	JFrame valveFrame = new JFrame("Valve");
    	valveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	valveFrame.getContentPane().add(valveUI);
    	valveFrame.pack();
    	valveFrame.setVisible(true);
        
        System.out.println("Attach a model listener,  A update containing the initial state should be received");
        container.addListener(new ChangeListener() {
			public void handleUpdate(ChangeEvent change) {				
				System.out.println("Received newValue : "+change.getUpdateValues().toString());
			}        	
        });
        containerUI.attachToModel();
        valveUI.attachToModel();        

		flowAgent.start();
    }    
    
    public static void main(String[] args) {
    	System.out.println("Demonstrates the use of the dataentity framework");
    	DataEntityDemo demo = new DataEntityDemo();
    	try {
			demo.simpleGUI();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }     
}
