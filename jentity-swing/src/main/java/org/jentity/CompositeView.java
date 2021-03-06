package org.jentity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;


public abstract class CompositeView extends JPanel implements GUIBean {
    private final List<GUIBean> nestedGUIBeans = new LinkedList<GUIBean>();
    private final List<JComponent> editableComponents = new LinkedList<JComponent>();
    
    /**
     * Adds the supplied GUI bean to this composite view. This means that <code>GUIBean</code> operations called on 
     * @param component
     */
    protected final void addGUIBean(GUIBean component) {
        nestedGUIBeans.add(component);
    }
    
    protected final void removeGUIBean(GUIBean component) {
        nestedGUIBeans.remove(component);
    }
    
    public synchronized void attachToModel() {
    	for (Iterator<GUIBean> iter = nestedGUIBeans.iterator(); iter.hasNext();) {
    		GUIBean guiBean = (GUIBean) iter.next();
    		guiBean.attachToModel();
    	}
    }

    public synchronized void detachFromModel() {
        for (Iterator<GUIBean> iter = nestedGUIBeans.iterator(); iter.hasNext();) {
            GUIBean guiBean = (GUIBean) iter.next();
            guiBean.detachFromModel();
        }
    }         
    
    protected final void addEnablableComponent(JComponent component) {
        editableComponents.add(component);
    }

    protected final void removeEnablableComponent(JComponent component) {
        editableComponents.remove(component);
    }

    public void setEnabled(boolean enabled) {
        for (Iterator<GUIBean> iter = nestedGUIBeans.iterator(); iter.hasNext();) {
            JComponent component = (JComponent) iter.next();
            component.setEnabled(enabled);
        }
    }        
}
