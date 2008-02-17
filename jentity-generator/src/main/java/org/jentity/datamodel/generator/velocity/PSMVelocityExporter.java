package org.jentity.datamodel.generator.velocity;

import java.util.Properties;

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
import org.jentity.datamodel.generator.IOMRole;

public class PSMVelocityExporter implements Exporter {
  private String templateFile = "DataEntity.vm";
  private PSMClass currentClass = null;;
  private String outputpath = "";

  public PSMVelocityExporter() {
      setOutputPath(System.getProperty("com.codegenerator.outputpath", ""));

      System.out.println("Writing to: "+System.getProperty("com.codegenerator.outputpath"));
  }
  
  public void initialize() throws Exception {
      Properties p = new Properties();
      p.setProperty( "resource.loader", "class" );
      p.setProperty( "class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
      Velocity.init(p);
  }
  
  public void setOutputPath(String path) {
      if (path != "") {
          outputpath = path+"/";
      } 
  }

  public void setTemplate(String templateFile) {
      this.templateFile = templateFile;
  }
  
  public void startClass(IOMClass cl) throws Exception {
    currentClass = new PSMClass();
    currentClass.setName(cl.getName());
    currentClass.setJavaDoc(cl.getDoc());
  }

  public void endClass(IOMClass cl) throws Exception {

    VelocityContext context = new VelocityContext();
    context.put("class", currentClass);

    Template template = Velocity.getTemplate(templateFile);

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
    at.setVisitor(GeneratorUtility.firstToUpperCase(attr.getVisitor()));
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