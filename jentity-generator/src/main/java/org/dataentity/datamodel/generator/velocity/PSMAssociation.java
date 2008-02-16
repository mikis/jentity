package org.dataentity.datamodel.generator.velocity;


public class PSMAssociation {

  private String targetClass;
  private String paramClass;

  public String getTargetClass() {
    return targetClass;
  }

  public void setTargetClass(String targetClass) {
    this.targetClass = targetClass;
  }

  public String getParamClass() {
    return paramClass;
  }

  public void setRefClass(String paramClass) {
    this.paramClass = paramClass;
  }

}