package org.dataentity;

public interface GUIBean {
    
	/**
	 * Attaches the GUI bean to the defined model. This means bean cheanges will be added to the model and model notification will be handled by the bean.
	 *
	 */
    void attachToModel();
    
    /**
     * Decouples the bean from the model. This beans bean changes will not affect the model, and model changes will not be handled by the bean.
     *
     */
    void detachFromModel();    
}
