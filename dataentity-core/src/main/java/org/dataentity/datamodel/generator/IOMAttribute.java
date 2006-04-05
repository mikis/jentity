package org.dataentity.datamodel.generator;

public class IOMAttribute {

  private String name = "";
  private String type = "";
  private String doc = "";

  private IOMClass classParent = null;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public IOMClass getClassParent() {
    return classParent;
  }

  public void setClassParent(IOMClass classParent) {
    this.classParent = classParent;
  }
  
  public String getDoc() {
      return doc;
  }
  
  public void setDoc(String doc) {
      this.doc = doc;
  }
}