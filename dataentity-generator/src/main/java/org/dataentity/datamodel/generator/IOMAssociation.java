package org.dataentity.datamodel.generator;


public class IOMAssociation {

  private String name = "";

  private IOMRole startRole = new IOMRole();
  private IOMRole endRole = new IOMRole();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public IOMRole getStartRole() {
    return startRole;
  }

  public void setStartRole(IOMRole startRole) {
    this.startRole = startRole;
  }

  public IOMRole getEndRole() {
    return endRole;
  }

  public void setEndRole(IOMRole endRole) {
    this.endRole = endRole;
  }


}