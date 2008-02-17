package org.jentity.datamodel.generator;


public interface Importer {
  public void start() throws Exception;
  public IOMClass createClass(Object data) throws Exception;
  public IOMAttribute createAttribute(Object data) throws Exception;
  public IOMOperation createOperation(Object data) throws Exception;
  public IOMParameter createParameter(Object data) throws Exception;
  public IOMAssociation createAssociation(Object data) throws Exception;
  public IOMRole createRole(Object data) throws Exception;
}