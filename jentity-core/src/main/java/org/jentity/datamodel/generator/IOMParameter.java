package org.jentity.datamodel.generator;


public class IOMParameter {

  private String name = "";
  private String type = "";

  private IOMOperation operationParent = null;

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

  public IOMOperation getOperationParent() {
    return operationParent;
  }

  public void setOperationParent(IOMOperation operation) {
    this.operationParent = operation;
  }


  public boolean isLast() {
    IOMParameter last = (IOMParameter) operationParent.getParameters().get(
      operationParent.getParameters().size()-1);
    if (last==this) return true;
    return false;
  }

}