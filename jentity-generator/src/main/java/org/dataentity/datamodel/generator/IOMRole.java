package org.dataentity.datamodel.generator;


public class IOMRole {

  private String name = "";
  private String multiplicity = "1";
  private boolean navigable = true;

  private IOMClass classInvolved = null;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMultiplicity() {
    return multiplicity;
  }

  public void setMultiplicity(String multiplicity) {
    this.multiplicity = multiplicity;
  }

  public void setNavigable(boolean navigable) {
    this.navigable = navigable;
  }

  public boolean isNavigable() {
    return navigable;
  }

  public IOMClass getClassInvolved() {
    return classInvolved;
  }

  public void setClassInvolved(IOMClass classInvolved) {
    this.classInvolved = classInvolved;
  }

}