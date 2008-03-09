package org.jentity.datamodel.visitor;

import java.text.ParseException;

import org.jentity.datamodel.xml.Counter;
import org.jentity.datamodel.xml.XMLFactory;

public class StringAttributeVisitor extends DefaultAttributeVisitor {

	@Override
	public Object readFromXML(String input, Counter counter) throws ParseException {
		return XMLFactory.readAttributeString(input, counter);
	}
}
