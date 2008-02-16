package org.dataentity.datamodel.generator.velocity;


public class PSMAttribute {

  private String name;
  private String type;
  private String visitor;
  private String nameUpper;
  private String nameAllUpper;
  private String javaDoc;
  
  private String toObjectBlock;
  private String fromObjectBlock;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  public String getType() {
    return type;
  }

  public String getVisitor() {
      return visitor;
  }
  public void setVisitor(String visitor) {
      this.visitor = visitor;
  } 
  
  public String getNameUpper() {
    return nameUpper;
  }

  public void setNameUpper(String nameUpper) {
    this.nameUpper = nameUpper;
  }
  public String getNameAllUpper() {
      return nameUpper.toUpperCase();
  }

  public String getJavaDoc() {
      return javaDoc;
  }
  public void setJavaDoc(String javaDoc) {
      this.javaDoc = javaDoc;
  }

  public String getToObjectBlock() {
      return toObjectBlock;
  }
  public void setToObjectBlock(String toObjectBlock) {
      this.toObjectBlock = toObjectBlock;
  }

  public String getFromObjectBlock() {
      return fromObjectBlock;
  }
  public void setFromObjectBlock(String fromObjectBlock) {
      this.fromObjectBlock = fromObjectBlock;
  }
  
  public boolean isInt() {
      return getType().equals("integer");
  }

}