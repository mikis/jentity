package org.dataentity.datamodel.generator;

import java.util.*;


public class IOMController {

  private static ArrayList classes = new ArrayList();
  private static ArrayList associations = new ArrayList();

  public static ArrayList getClasses() {
    return classes;
  }

  public static ArrayList getAssociations() {
    return associations;
  }

  public static void addClass(IOMClass cl) {
    classes.add(cl);
  }

  public static void addAssociation(IOMAssociation association) {
    associations.add(association);
  }

  public static IOMClass queryClass(String name) {
    for (int i = 0; i < classes.size() ; i++) {
      IOMClass cl = (IOMClass) classes.get(i);
      if(cl.getName().equals(name))
        return cl;
    }
    return null;
  }

}