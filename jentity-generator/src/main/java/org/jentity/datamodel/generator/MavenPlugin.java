package org.jentity.datamodel.generator;

import java.net.URL;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.jentity.datamodel.generator.velocity.PSMVelocityExporter;

/**
 * @goal generate
 * @phase generate-sources
 * @description Facade generator for data entities
 */
public class MavenPlugin extends AbstractMojo {

    /**
     * @parameter expression="${model}"
     * @required
     * @description The location of the xml file defining the data entity model 
     */
    private String model;
    
    /**
     * @parameter expression="DataEntity.vm"
     */
    private String template;

    /**
     * @parameter expression="${project.build.directory}/generated-sources/"
     * @required
     */
    private String outputDirectory;

    /**
     * @parameter expression="${project}"
     * @required
     */
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        // ----------------------------------------------------------------------
        //
        // ----------------------------------------------------------------------
        URL url = MavenPlugin.class.getClassLoader().getResource(template);
        getLog().info("Using template "+url);
        getLog().info("Loading model "+model);
        getLog().info("Outputting to "+outputDirectory);
        
        Importer importer = new XMLImporter(model);
        
        PSMVelocityExporter exporter = new PSMVelocityExporter();
        exporter.setOutputPath(outputDirectory);
        exporter.setTemplate(template);

        try {
            importer.start();
            IOMNavigator navigator = new IOMNavigator(exporter);
            navigator.start();            
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to execute generator", e);
        }
        
        if (project != null) {
            project.addCompileSourceRoot(outputDirectory);
        }
    }
}