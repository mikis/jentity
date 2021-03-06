package org.jentity.datamodel.generator;

import java.util.*;

public class IOMOperation {

  private String name;
  private String returnType;

  private ArrayList<IOMParameter> parameters = new ArrayList<IOMParameter>();
  private IOMClass classParent = null;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getReturnType() {
    return returnType;
  }

  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }

  public ArrayList<IOMParameter> getParameters() {
    return parameters;
  }

  public void addParameter(IOMParameter param) {
    parameters.add(param);
    param.setOperationParent(this);
  }

  public IOMClass getClassParent() {
    return classParent;
  }

  public void setClassParent(IOMClass classParent) {
    this.classParent = classParent;
  }

}