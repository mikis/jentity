package org.jentity.datamodel.generator;

import java.util.*;

public class IOMClass {

  private String name = "";
  private String doc = "";
  private String stereotype = "";

  private ArrayList<IOMAttribute> attributes = new ArrayList<IOMAttribute>();
  private ArrayList<IOMOperation> operations = new ArrayList<IOMOperation>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStereotype() {
    return stereotype;
  }

  public void setStereotype(String stereotype) {
    this.stereotype = stereotype;
  }

  public ArrayList<IOMAttribute> getAttributes() {
    return attributes;
  }

  public void addAttribute(IOMAttribute attribute) {
    attributes.add(attribute);
    attribute.setClassParent(this);
  }

  public ArrayList<IOMOperation> getOperations() {
    return operations;
  }

  public void addOperation(IOMOperation operation) {
    operations.add(operation);
    operation.setClassParent(this);
  }

  public ArrayList<IOMAssociation> getMyAssociations() {
    ArrayList<IOMAssociation> ret = new ArrayList<IOMAssociation>();
    for (int i = 0; i < IOMController.getAssociations().size() ; i++) {
      IOMAssociation ass = (IOMAssociation) IOMController.getAssociations().get(i);
      if(ass.getStartRole().getClassInvolved().getName().equals(this.name)
        ||ass.getEndRole().getClassInvolved().getName().equals(this.name))
        ret.add(ass);
    }
    return ret;
  }
  
  public String getDoc() {
      return doc;
  }
  public void setDoc(String doc) {
      this.doc = doc;
  }
}