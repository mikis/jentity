package org.dataentity.datamodel.generator.velocity;

import java.util.*;

public class PSMClass {


  private String packageName;
  private String name;
  private String javaDoc;
  private ArrayList attributes = new ArrayList();
  private ArrayList singleAssociations = new ArrayList();
  private ArrayList multiAssociations = new ArrayList();

  public String getName() {
    return name;
  }  

  public String getPackage() {
    return packageName;
  }

  public void setName(String name) {
    int classNameIndex = name.lastIndexOf('.');
    if (classNameIndex == -1) {
        this.name = name;
        this.packageName = "";
    } else {
        this.name = name.substring(classNameIndex+1);
        this.packageName = name.substring(0, classNameIndex);
    }
  }

  public ArrayList getAttributes() {
    return attributes;
  }

  public void addAttribute(PSMAttribute attribute) {
    attributes.add(attribute);
  }

  public ArrayList getSingleAssociations() {
    return singleAssociations;
  }

  public void addSingleAssociation(PSMAssociation association) {
    singleAssociations.add(association);
  }

  public ArrayList getMultiAssociations() {
    return multiAssociations;
  }

  public void addMultiAssociation(PSMAssociation association) {
    multiAssociations.add(association);
  }
  
  public String getJavaDoc() {
      return javaDoc;
  }
  
  public void setJavaDoc(String javaDoc) {
      this.javaDoc = javaDoc;
  }
}