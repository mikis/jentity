package org.jentity.datamodel;

/**
 * Model entities are data entities extends with a ID attribute. 
 * The ID's are used to identify the entity in larger system contexts.
 */
public interface ModelEntity {
    /**
     * Return the ID for this model entity.
     */
    public Object getID();
}
