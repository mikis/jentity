package org.dataentity.datamodel.generator;


public interface Exporter {
  public void initialize() throws Exception;
  public void startClass(IOMClass cl) throws Exception;
  public void endClass(IOMClass cl) throws Exception;
  public void startAssociation(IOMAssociation association, IOMClass currentClass) throws Exception;
  public void endAssociation(IOMAssociation association, IOMClass currentClass) throws Exception;
  public void startOperation(IOMOperation operation) throws Exception;
  public void endOperation(IOMOperation operation) throws Exception;
  public void startAttribute(IOMAttribute attribute)throws Exception;
  public void endAttribute(IOMAttribute attribute)throws Exception;
  public void startParameter(IOMParameter parameter)throws Exception;
  public void endParameter(IOMParameter parameter)throws Exception;
  public void finalize() throws Exception;
}