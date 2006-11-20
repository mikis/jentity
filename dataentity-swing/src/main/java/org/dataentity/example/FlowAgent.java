package org.dataentity.example;

import org.dataentity.datamodel.Container;
import org.dataentity.datamodel.DataProcessor;
import org.dataentity.datamodel.Valve;


/**
 * Simulates a flow by listening to the Valve setting and updating the waterlevel in the container accordingly
 */
public class FlowAgent {
	private final Container container;
	private final  Valve valve;
	private Thread thread;
	private DataProcessor waterLevelProcessor;

	public FlowAgent(Container container, Valve valve) {
		this.container = container;
		this.valve = valve;
	}
	
	public void start() {
		thread = new Thread(new Worker());
		thread.start();
	}
	
	public void stop() {
		thread.interrupt();
	}
	
	private class Worker implements Runnable {
		public void run() {
			try {
				if (valve.getOpen()) {
					Container update = new Container();
					int currentLevel = container.getWaterVolume();
					update.setWaterVolume(currentLevel-valve.getFlow());
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}		
	}
}
