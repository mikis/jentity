package org.dataentity.example;

import javax.swing.JFrame;

import org.dataentity.datamodel.ChangeListener;
import org.dataentity.datamodel.Container;

public class DataEntityDemo  {
	private JFrame frame = new JFrame("Dataentity demo");
    
    public void simpleGUI() throws InterruptedException {
    	System.out.println("Shows a simple example of single attribute modifications");
        
    	System.out.println("Construct a test dataentity with a Graphical View attached");
        Container model = new Container();
        
        //Initialize model
        model.setTotalVolume(1000);
        model.setWaterVolume(900);
        model.setMinWaterVolume(0);
        
        ContainerUI containerUI = new ContainerUI(model);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(containerUI);
        frame.pack();
        frame.setVisible(true);
        
        System.out.println("Attach a model listener,  A update containing the initial state should be received");
        model.addListener(new ChangeListener() {
			public void handleUpdate(ChangeEvent change) {				
				System.out.println("Received newValue : "+change.getUpdateValues().toString());
			}        	
        });
        containerUI.attachToModel();
        Thread.sleep(5000);
        
        System.out.println("Set the first attribute of the dataentity, the GUI should be updated with the assigned value and a update notification should be received");
        Thread.sleep(5000);
        
        System.out.println("Updated the GUI with a new value, a notification of the update should be received");
        Thread.sleep(5000);
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
