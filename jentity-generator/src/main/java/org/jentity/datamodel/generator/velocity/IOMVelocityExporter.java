package org.jentity.datamodel.generator.velocity;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jentity.datamodel.generator.Exporter;
import org.jentity.datamodel.generator.GeneratorUtility;
import org.jentity.datamodel.generator.IOMAssociation;
import org.jentity.datamodel.generator.IOMAttribute;
import org.jentity.datamodel.generator.IOMClass;
import org.jentity.datamodel.generator.IOMOperation;
import org.jentity.datamodel.generator.IOMParameter;

public class IOMVelocityExporter implements Exporter {

  private final static String TEMPLATE = "IOMTemplate.vm";
  private GeneratorUtility utility = null;

  public void initialize() throws Exception {
    Velocity.init();
    utility = new GeneratorUtility();
  }

  public void startClass(IOMClass cl) throws Exception {

    VelocityContext context = new VelocityContext();
    context.put("class", cl);
    context.put("utility", utility);

    Template template = Velocity.getTemplate(TEMPLATE);

    BufferedWriter writer =
      new BufferedWriter(new FileWriter(cl.getName()+".java"));

    template.merge(context, writer);
    writer.flush();
    writer.close();

    System.out.println("Class " + cl.getName() + " generated!");

  }

  public void endClass(IOMClass cl) throws Exception {
  }

  public void startOperation(IOMOperation operation) throws Exception {
  }

  public void endOperation(IOMOperation operation) throws Exception {
  }

  public void startAttribute(IOMAttribute attr)throws Exception {
  }

  public void endAttribute(IOMAttribute attr)throws Exception {
  }

  public void startParameter(IOMParameter parameter)throws Exception {
  }

  public void endParameter(IOMParameter parameter)throws Exception {
  }

  public void startAssociation(IOMAssociation association, IOMClass currentClass) throws Exception {
  }

  public void endAssociation(IOMAssociation association, IOMClass currentClass) throws Exception {
  }

  public void finalize() throws Exception {
  }

}