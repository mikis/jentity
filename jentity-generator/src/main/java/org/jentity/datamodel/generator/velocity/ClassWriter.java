package org.jentity.datamodel.generator.velocity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.StringTokenizer;

import org.jentity.datamodel.generator.IOMClass;

public class ClassWriter extends Writer {
    private final BufferedWriter delegatedWriter;

    public ClassWriter(String sourcePath, IOMClass cl) throws IOException {
        super();
        
        StringTokenizer st = new StringTokenizer(cl.getName(), ".");
        StringBuffer path2Clazz = new StringBuffer(sourcePath);
        String clazzName = null;
        while (st.hasMoreElements()) {
            String element = (String)st.nextElement();
            if (st.hasMoreElements()) {
                path2Clazz.append(element+"/");
            } else {
                clazzName = (element+".java");
            }
        }
        
        new File(path2Clazz.toString()).mkdirs();
        delegatedWriter = new BufferedWriter(new FileWriter(path2Clazz.toString()+clazzName));
    }

    public void close() throws IOException {
        delegatedWriter.close();
    }

    public void flush() throws IOException {     
        delegatedWriter.flush();
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        delegatedWriter.write(cbuf, off, len);
    }
}
