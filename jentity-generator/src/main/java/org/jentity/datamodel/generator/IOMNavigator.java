package org.jentity.datamodel.generator;

import java.util.*;

public class IOMNavigator {

  private Exporter exporter = null;

  public IOMNavigator(Exporter exporter) {
    this.exporter = exporter;
  }

  public void start() throws Exception {

    exporter.initialize();

    // Class navigation
    for (int i = 0; i < IOMController.getClasses().size(); i++) {
      IOMClass cl = (IOMClass) IOMController.getClasses().get(i);
      exporter.startClass(cl);

      // Attribute navigation
      for (int j = 0; j < cl.getAttributes().size(); j++) {
        IOMAttribute attribute = (IOMAttribute) cl.getAttributes().get(j);
        exporter.startAttribute(attribute);
        exporter.endAttribute(attribute);
      }

      // Associtaion Navigation
      ArrayList<IOMAssociation> associations = cl.getMyAssociations();
      for (int j = 0; j < associations.size(); j++) {
        IOMAssociation ass = (IOMAssociation) associations.get(j);
        exporter.startAssociation(ass,cl);
        exporter.endAssociation(ass,cl);
      }

      // Operation navigation
      for (int k = 0; k < cl.getOperations().size(); k++) {
        IOMOperation operation = (IOMOperation) cl.getOperations().get(k);
        exporter.startOperation(operation);

        // Parameter navigation
        for (int t = 0; t < operation.getParameters().size(); t++) {
          IOMParameter param = (IOMParameter) operation.getParameters().get(t);
          exporter.startParameter(param);
          exporter.endParameter(param);
        }
        exporter.endOperation(operation);
      }

      exporter.endClass(cl);
    }

    exporter.finalize();
  }

}