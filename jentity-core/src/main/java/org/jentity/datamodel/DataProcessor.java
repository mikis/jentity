package org.jentity.datamodel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class DataProcessor {
    private final String name;  

    public DataProcessor(String name) {
        this.name = name;
    }
    
    private final List subControllers = new LinkedList();
    
    public void addSubprocessor(DataProcessor subprocessor) {
        subControllers.add(subprocessor);  
    }
    
    /**
     * Process the supplied data with the business rules defined in the concrete processor. 
     * After this subprocessers are applied.
     */
    public final void process(DataEntity data) {
        processEntity(data);
        for (Iterator iter = subControllers.iterator(); iter.hasNext();) {
            DataProcessor subcontroller = (DataProcessor) iter.next();
            subcontroller.process(data);
        }
    }   
   
    /**
     * Must be overloaded by subtypes defined the processor operations 
     */
    protected abstract void processEntity(DataEntity data);
    
    private void appendLevelIndentedString(StringBuffer sb, int level) {
        for (int i = 0; i < level;i++) {
            sb.append("\t");
        }
        sb.append(name); 
        if (subControllers.size() > 0) {
            sb.append("  Subcontrollers: ");
            for (Iterator iter = subControllers.iterator(); iter.hasNext();) {
                DataProcessor subcontroller = (DataProcessor) iter.next();
                sb.append("\n");
                subcontroller.appendLevelIndentedString(sb, level+1);                
            }
        }
    }
    
    /**
     * Provides a representation of this processer with all subprocessers.
     * The processer hierachy is represented as a multiline tree.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();    
        appendLevelIndentedString(sb, 0);
        return sb.toString();
    }
}
