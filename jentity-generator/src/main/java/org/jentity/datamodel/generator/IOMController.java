package org.jentity.datamodel.generator;

import java.util.*;

public class IOMController {

  private static ArrayList<IOMClass> classes = new ArrayList<IOMClass>();
  private static ArrayList<IOMAssociation> associations = new ArrayList<IOMAssociation>();

  public static ArrayList<IOMClass> getClasses() {
    return classes;
  }

  public static ArrayList<IOMAssociation> getAssociations() {
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