package org.jentity;

/**
 * Instances of this class can be used to avoid event notification loops. 
 * Event based functionality should attempt to acquire the monitor with the 
 * <code>getGuard()</code> method. 
 * After the event based functionality has been executed the monitor must be released with 
 * the <code>releaseGuard()</code> operation.
 */
public class EventGuard {
    private boolean updating = false;    
    
    /**
     * Will attempt to acquire the monitor. If the monitor is successfully aquired the event processing 
     * may continue, else the event should be ignored.
     * @return <code>true</code> if the monitor is aquired, else <code>false</code>
     */
    public boolean getGuard() {
        if (!updating) {
            updating = true;
            return true;
        } else return false;
    }
    
    /**
     * Releases the monitor.
     */
    public void releaseGuard() {
        updating = false;
    }
}
