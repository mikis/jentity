package org.dataentity.datamodel.generator.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.dataentity.datamodel.generator.Exporter;
import org.dataentity.datamodel.generator.GeneratorUtility;
import org.dataentity.datamodel.generator.IOMAssociation;
import org.dataentity.datamodel.generator.IOMAttribute;
import org.dataentity.datamodel.generator.IOMClass;
import org.dataentity.datamodel.generator.IOMOperation;
import org.dataentity.datamodel.generator.IOMParameter;
import org.dataentity.datamodel.generator.IOMRole;

public class PSMVelocityExporter implements Exporter {
  private final static String TEMPLATE = "DataEntity.vm";
  private PSMClass currentClass = null;;
  private String outputpath = "";

  public PSMVelocityExporter() {
      setOutputPath(System.getProperty("com.codegenerator.outputpath", ""));

      System.out.println("Writing to: "+System.getProperty("com.codegenerator.outputpath"));
  }
  
  public void initialize() throws Exception {
    Velocity.init();
  }
  
  public void setOutputPath(String path) {
      if (path != "") {
          outputpath = path+"/";
      } 
  }

  public void startClass(IOMClass cl) throws Exception {
    currentClass = new PSMClass();
    currentClass.setName(cl.getName());
    currentClass.setJavaDoc(cl.getDoc());
  }

  public void endClass(IOMClass cl) throws Exception {

    VelocityContext context = new VelocityContext();
    context.put("class", currentClass);

    Template template = Velocity.getTemplate(TEMPLATE);

    ClassWriter writer = new ClassWriter(outputpath, cl);


    template.merge(context, writer);
    writer.flush();
    writer.close();

    System.out.println("Class " + cl.getName() + " generated!");

  }

  public void startAttribute(IOMAttribute attr)throws Exception {
    PSMAttribute at = new PSMAttribute();
    at.setName(attr.getName());
    at.setNameUpper(GeneratorUtility.firstToUpperCase(attr.getName()));
    at.setType(GeneratorUtility.getJavaType(attr.getType()));
    at.setJavaDoc(attr.getDoc());
    currentClass.addAttribute(at);
  }
  
  public void endAttribute(IOMAttribute attr)throws Exception {
  }

  public void startOperation(IOMOperation operation) throws Exception {
  }

  public void endOperation(IOMOperation operation) throws Exception {
  }

  public void startParameter(IOMParameter parameter)throws Exception {
  }

  public void endParameter(IOMParameter parameter)throws Exception {
  }

  public void startAssociation(IOMAssociation association, IOMClass sourceClass) throws Exception {

    IOMRole role = association.getStartRole();

    if (role.getClassInvolved()==sourceClass)
      role = association.getEndRole();

    if (role.isNavigable()) {

      PSMAssociation ast = new PSMAssociation();
      ast.setTargetClass(role.getClassInvolved().getName());
      ast.setRefClass(GeneratorUtility.firstToLowerCase(role.getClassInvolved().getName()));

      if (role.getMultiplicity().equals("1"))
        currentClass.addSingleAssociation(ast);
      else
        currentClass.addMultiAssociation(ast);
    }
  }

  public void endAssociation(IOMAssociation association, IOMClass currentClass) throws Exception {
  }

  public void finalize() throws Exception {
  }

}