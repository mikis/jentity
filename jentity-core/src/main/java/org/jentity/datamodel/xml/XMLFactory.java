package org.jentity.datamodel.xml;

import java.text.ParseException;
import java.util.Iterator;

import org.apache.commons.lang.enums.EnumUtils;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.ParameterEnum;

/**
 * Contains functionality for XML <-> DataEntity transformations.
 *
 */
public abstract class XMLFactory {
	public static String createXML(DataEntity dataentity, String indentation) {	
		StringBuffer sb = new StringBuffer();
	    sb.append(indentation+"<dataentity type="+dataentity.getClass()+">\n");  
	    Iterator iterator = dataentity.getKeys().iterator();
	    while (iterator.hasNext()) {
	        ParameterEnum parameter = (ParameterEnum) iterator.next();
	        sb.append(indentation+"\t<"+parameter.getName()+">");
	        sb.append(dataentity.getVisitor(parameter).toXML(dataentity.getAttribute(parameter), indentation+"\t"));
	        sb.append(indentation+"</"+parameter.getName()+">\n");
	    }
	    sb.append(indentation+"</dataentity>");

	    return sb.toString();
	}
	
	/**
	 * Parses an XML string into a <code>DataEntity</code> object.
	 * @param inputXML The XML to base the new <code>DataEntity</code> on.
	 * @return
	 */
	// Consider using a SAX parser for this. In connection with introduction of a 
	// SAX parser we should separate the XML transformation functionality into a 
	// module for itself. 
	public static DataEntity createDataEntity(String inputXML, int counter) throws ParseException {
		int endIndex = inputXML.indexOf(">");
		String type = inputXML.substring(23, endIndex);		
		DataEntity newInstance = null;
		try {
			newInstance = (DataEntity)Class.forName(type).newInstance();
			counter=endIndex;// Set counter to end of initial dataentity tag
			if (counter<inputXML.length()) {
				counter=inputXML.indexOf("<", counter)+1; // Advance counter to next tag
				endIndex = inputXML.indexOf(">", counter);
				String tag = inputXML.substring(counter, endIndex);
				if (tag.equals("/dataentity")) {

				} else {
					ParameterEnum parameter = (ParameterEnum)EnumUtils.getEnum(newInstance.getParameterEnumClass(), inputXML.substring(counter, endIndex));
					counter = endIndex;
					Object attribute = newInstance.getVisitor(parameter).readFromXML(inputXML, counter);
					newInstance.setAttribute(parameter, attribute);
				}
				counter=inputXML.indexOf(">", counter)+1; // Advance counter to end
				
				System.out.println("Remaining string: "+inputXML.substring(counter));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return newInstance;
	}
}
