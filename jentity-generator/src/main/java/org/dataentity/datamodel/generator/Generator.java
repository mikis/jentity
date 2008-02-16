package org.dataentity.datamodel.generator;

import org.dataentity.datamodel.generator.velocity.IOMVelocityExporter;
import org.dataentity.datamodel.generator.velocity.PSMVelocityExporter;

public class Generator {

  public static void main(String args[]) throws Exception {

    if (args.length!=3) {
      System.out.println("Syntax: Generator <input-file> <importer> <exporter>");
      System.exit(1);
    }

    Importer importer = null;
    if (args[1].equals("XML"))
      importer = new XMLImporter(args[0]);
    else {
      System.out.println("Importer " + args[1] + " non found!");
      System.exit(1);
    }

    Exporter exporter = null;
    if (args[2].equals("IOMVelocityExporter"))
        exporter = new IOMVelocityExporter();
    else if (args[2].equals("PSMVelocityExporter")) {
        exporter = new PSMVelocityExporter();
    } else {
        System.out.println("Exporter " + args[2] + " non found!");
        System.exit(1);
    }

    importer.start();
    IOMNavigator navigator = new IOMNavigator(exporter);
    navigator.start();

  }

}