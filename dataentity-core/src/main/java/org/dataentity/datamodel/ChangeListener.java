package org.dataentity.datamodel;

import java.util.EventListener;


/**
 * Data core listeners must implement this interface.
 */
public interface ChangeListener extends EventListener {
    /**
     * Contains information regarding one state change in a data core. 
     */
    public static class ChangeEvent {
        private final DataEntity currentValues;
        private final DataEntity updatesValues;
        private final DataEntity oldValues;
        
        /**
         * Constructs a <code>ChangeEvent</code> object composed of the supplied parameters. 
         * All the supplied data entities will be locked to prevent modification attempts.
         * @param updatedValues
         * @param oldValues
         * @param model The current model after the update
         */
        public ChangeEvent(final DataEntity updatedValues, final DataEntity oldValues, final DataEntity model) {
            super();
            this.updatesValues = updatedValues;
            updatesValues.setLock(true);
            this.oldValues = oldValues;
            oldValues.setLock(true);
            this.currentValues = model.copy();
            currentValues.setLock(true);
        }
        /**
         * @return Returns the new attribute set applied to the entity.
         */
        public DataEntity getUpdateValues() {
            return updatesValues;
        }
        /**
         * @return Returns the old attribute set replaced in the update. May be null if 
         * undefined
         */
        public DataEntity getOldValues() {
            return oldValues;
        }
        
        public DataEntity getModel() {
            return currentValues;
        } 
    }

    public void handleUpdate(ChangeListener.ChangeEvent change);
}