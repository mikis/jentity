package org.jentity.datamodel;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.ClassUtils;
import org.jentity.datamodel.visitor.AttributeVisitor;
import org.jentity.datamodel.visitor.DefaultAttributeVisitor;
import org.jentity.datamodel.xml.Counter;
import org.jentity.datamodel.xml.XMLFactory;

/**
 * Implements of the <code>DataEntity</code> pattern.
 */
public abstract class DataEntity {
    private boolean lock = false;
    private HashMap<ParameterEnum, Object> attributes = new HashMap<ParameterEnum, Object>();    
    protected transient LinkedList<ChangeListener> modelListeners = new LinkedList<ChangeListener>();    
    private boolean notifying = false;
    protected transient DataProcessor preprocessor;
    protected transient DataProcessor postprocessor;
    private static final AttributeVisitor defaultVisitor = new DefaultAttributeVisitor();

    //  ------------------ Attribute access  -----------------------
    /**
     * Return the value for the specified attribute. 
     * If the attribute is undefined null is returned.
     */
    public Object getAttribute(Object key) {
        return attributes.get(key);
    }
    
    /**
     * Sets the specified attribute to the indicated value
     */
    public void setAttribute(ParameterEnum key, Object value) {
        DataEntity update = createInstance();
        update.set(key,value); 
        update(update);
    }
    private void set(ParameterEnum key, Object value) {
        if (!lock) {
            attributes.put(key, value);
        } else {
            throw new IllegalStateException("Attempt to modify locked entity");
        }
    }
    
    /**
     * Sets the preprocessor for this <code>DataEntity</code> object. The preprocessor will be applied to on all changes, before they are committed.  
     * @param preprocessor
     */
    public void setPreprocessor(DataProcessor preprocessor) {
    	this.preprocessor = preprocessor;
    }
    
    /**
     * Returns <code>true</code> if the parameter has been defined in this data core, 
     * else <code>false</code>
     */
    public boolean isDefined(Object key) { 
        return attributes.containsKey(key);
    }
    
    public Set<ParameterEnum> getKeys() {
        return attributes.keySet();
    }
    /**
     * Inserts the attributes defined in the update into this entity.
     */
    public synchronized void update(DataEntity update) {
        //Defensive copy, we also lock the copy, wouldn't be nice doing this on the supplied object.
        update = update.copy();
        if (preprocessor != null) {
            preprocessor.process(update);
        }
        update.setLock(true);
        
        DataEntity oldValues = createInstance();
        Iterator<ParameterEnum> iter = update.getKeys().iterator();
        while (iter.hasNext()) {
            ParameterEnum parameter = iter.next(); 
            // The original attribute should be copied, it will potentially be changed during the patch
            oldValues.set(parameter, getVisitor(parameter).copy(getAttribute(parameter)));
            Object patchedAttribute = getVisitor(parameter).patch(this.getAttribute(parameter), update.getAttribute(parameter));
            set(parameter, patchedAttribute);
        }
        
        if (postprocessor != null) {
            postprocessor.process(this);
        }
        notifyListeners(new ChangeListener.ChangeEvent(update, oldValues, this));
    }
    
    /**
     * Return a <code>AttributeVisitor</code> for the supplied attribute. The operation should be implemented by
     * subclasses to provide visitors for specific attribute. 
     * This generic implementation returns a  <code>DefaultAttributeVisitor</code>
     */
    public AttributeVisitor getVisitor(ParameterEnum parameter) {
        return defaultVisitor;
    }
    
    /**
     * May be used to prevent data modifications. Data modification calls will have no   
     * effect on a locked core.
     * @param value <code>true</code> to lock the core, <code>false</code> to unlock the core
     */
    public void setLock(boolean value) {
        lock = value;        
    }
    
    /**
     * Returns a new <code>DataEntity</code> containing all the attributes in this object
     * different from the supplied object. The difference object can be used to change data cores
     * equal to the supplied cores into a data core equal to this core. 
     */
    public DataEntity getDifference(DataEntity other) {
        // TODO Auto-generated method stub
        return null;
    }
    
    //----------------- Object overrides------------------------------
    
    /**
     * Creates a copy of the core. The depth of the copy depends on the implementation.
     */
	public DataEntity copy() {
        DataEntity copy = createInstance();
        copy.attributes = (HashMap<ParameterEnum,Object>)attributes.clone();
        copy.preprocessor = preprocessor;
        copy.postprocessor = postprocessor;
        return copy;
    }

    /**
     * Return a string representation of the attribute defined in this object
     */
    public String toString() {
        TreeMap<ParameterEnum,Object> sortedAttributes = new TreeMap<ParameterEnum,Object>(attributes);
        StringBuffer sb = new StringBuffer();
        sb.append(ClassUtils.getShortClassName(getClass()));  
        if (lock) sb.append("(Locked)");
        Iterator<ParameterEnum> iterator = sortedAttributes.keySet().iterator();
        while (iterator.hasNext()) {
            ParameterEnum parameter = iterator.next();
            sb.append("\n"+parameter+" = " + getVisitor(parameter).toString(getAttribute(parameter)));
        }
        return sb.toString();
    }
    
    /**
     * Returns <code>true</code> if all the attributes are equal else false.
     */
    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) return false;

        DataEntity other = (DataEntity)obj;
        
        if (attributes.size() != other.getKeys().size()) return false; 
        
        Iterator<ParameterEnum> iterator = attributes.keySet().iterator();
        while (iterator.hasNext()) {
            ParameterEnum parameter = iterator.next();
            if (!getVisitor(parameter).isEqual(getAttribute(parameter), other.getAttribute(parameter))) return false;
        }
        
        return true;
    }
    
    /**
     * Creates a new <code>DataEntity</code> object corresponding to this entity 
     * with the update applied.
     */
    public DataEntity createUpdatedEntity(DataEntity update) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * Adds a listener to this core. Listeners will be notified of changes to
     * the attribute set. 
     */
    public void addListener(ChangeListener listener) {
        modelListeners = (LinkedList<ChangeListener>)modelListeners.clone();
        modelListeners.add(listener);
        DataEntity entity = copy();
        listener.handleUpdate(new ChangeListener.ChangeEvent(entity, createInstance(), entity));
    }
    
    /**
     * Removes the indicated listener.
     */
    public void removeListener(ChangeListener listener) {
        modelListeners = (LinkedList<ChangeListener>)modelListeners.clone();
        modelListeners.remove(listener);
    }

    /**
     * Notifies listeners of change to this data entities data. Data changes will always be 
     * called through a synchronization so only one thread will be running a notification at a time.  
     * @param update
     * @throw IllegalStateException Throw if a thread tries to update the data while a notification 
     * is taking place. <p>
     * Updates are synchronized, so this can only occur if the updating thread is the same as the 
     * notification thread.
     */
    private void notifyListeners(ChangeListener.ChangeEvent update) throws IllegalStateException {
    	if (!notifying) {
    		try {
    			notifying = true;
    			for (Iterator<ChangeListener> iter = modelListeners.iterator(); iter.hasNext();) {
    				ChangeListener listener = iter.next();
    				listener.handleUpdate(update);
    			}
    		} finally {
    			notifying = false;
    		}
    	} else {
    		throw new IllegalStateException("Attempt to update entity in notification thread.");
    	}
    }

    public abstract DataEntity createInstance();
    public abstract Class<ParameterEnum> getParameterEnumClass();

	public static DataEntity readFromXML(String inputXML, Counter counter) throws ParseException {
		return XMLFactory.createDataEntity(inputXML, counter);
	}

	public String toXML(String indentation) {    
		return XMLFactory.createXML(this, indentation);
	}
}
