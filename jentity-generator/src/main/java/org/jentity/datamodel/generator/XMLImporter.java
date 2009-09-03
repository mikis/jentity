package org.jentity.datamodel.generator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XMLImporter extends DefaultHandler implements Importer{

  private InputStream input = null;
  private String fileName = null;
  private Stack<Object> stack = new Stack<Object>();

  public XMLImporter(String inputFileName) {
    this.fileName = inputFileName;
  }

  public void start() throws Exception {

    input = new FileInputStream(fileName);
    SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setNamespaceAware(true);
    SAXParser saxParser = spf.newSAXParser();
    XMLReader xmlReader = saxParser.getXMLReader();
    xmlReader.setContentHandler(this);
    xmlReader.parse(new InputSource(input));
    input.close();

  }

  public IOMClass createClass(Object data) throws Exception {
    Attributes attr = (Attributes) data;
    IOMClass cl = new IOMClass();
    cl.setName(attr.getValue("name"));
    cl.setDoc(attr.getValue("doc"));
    cl.setStereotype(attr.getValue("stereotype"));
    IOMController.addClass(cl);
    stack.push(cl);
    return cl;
  }

  public IOMAttribute createAttribute(Object data) throws Exception {
    Attributes attr = (Attributes) data;
    IOMClass parent = (IOMClass) stack.peek();
    IOMAttribute at = new IOMAttribute();
    at.setName(attr.getValue("name"));
    at.setType(attr.getValue("type"));
    at.setVisitor(attr.getValue("visitor"));
    at.setDoc(attr.getValue("doc"));
    parent.addAttribute(at);
    stack.push(at);
    return at;
  }

  public IOMOperation createOperation(Object data) throws Exception {
    Attributes attr = (Attributes) data;
    IOMClass parent = (IOMClass) stack.peek();
    IOMOperation op = new IOMOperation();
    op.setName(attr.getValue("name"));
    op.setReturnType(attr.getValue("returnType"));
    parent.getOperations().add(op);
    stack.push(op);
    return op;
  }

  public IOMParameter createParameter(Object data) throws Exception {
    Attributes attr = (Attributes) data;
    IOMOperation parent = (IOMOperation) stack.peek();
    IOMParameter p = new IOMParameter();
    p.setName(attr.getValue("name"));
    p.setType(attr.getValue("type"));
    parent.addParameter(p);
    stack.push(p);
    return p;
  }

  public IOMAssociation createAssociation(Object data) throws Exception {
    Attributes attr = (Attributes) data;
    IOMAssociation ass = new IOMAssociation();
    ass.setName(attr.getValue("name"));
    IOMController.addAssociation(ass);
    stack.push(ass);
    return ass;
  }

  public IOMRole createRole(Object data) throws Exception {
    Attributes attr = (Attributes) data;
    IOMRole role = null;
    IOMAssociation parent = (IOMAssociation) stack.peek();
    //Type
    if (attr.getValue("type").equalsIgnoreCase("start"))
      role = parent.getStartRole();
    else
      role = parent.getEndRole();
    role.setName(attr.getValue("name"));
    role.setMultiplicity(attr.getValue("multiplicity"));
    role.setNavigable(attr.getValue("navigable").equalsIgnoreCase("true")?true:false);
    // class involved
    IOMClass classInvolved = IOMController.queryClass(attr.getValue("class"));
    if (classInvolved==null)
      throw new Exception("Class: " + attr.getValue("class") + " not found!");
    role.setClassInvolved(classInvolved);
    stack.push(role);
    return role;
  }

  public void startElement(String uri, String name, String qName, Attributes attr) throws SAXException {

    try {
      if (name.equals("Class")) {
        this.createClass(attr);
      }
      if (name.equals("Attribute")) {
        this.createAttribute(attr);
      }
      if (name.equals("Association")) {
        this.createAssociation(attr);
      }
      if (name.equals("Role")) {
        this.createRole(attr);
      }
      if (name.equals("Operation")) {
        this.createOperation(attr);
      }
      if (name.equals("Parameter")) {
        this.createParameter(attr);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new SAXException(e);
    }

  }

  public void endElement(String uri, String name, String qName) throws SAXException {
    if (stack.size()>0)
      stack.pop();
  }

}