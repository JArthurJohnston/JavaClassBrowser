/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;

import Models.BaseModel;
import com.sun.source.tree.ClassTree;
import com.sun.source.util.Trees;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

/**
 * This should probably be a static class with 
 * the compiler being a singleton variable.
 * 
 * @author arthur
 */
public class Compiler {
    private SourceModel source;
    private JavaCompiler compiler;
    private ModelMemoryManager manager;
    private DiagnosticCollector<JavaFileObject> diagnostics;
    
    public Compiler(BaseModel aModel){
        if(aModel.isMethod())
            this.parseMethod(aModel);
    }
    
    private void parseMethod(BaseModel aModel){
        String methodSource = "class WrapperClass{"
                + aModel.toSourceString() +"}";
        this.source = new SourceModel(methodSource, "WrapperClass");
        
    }
    
    public JavaCompiler getCompiler(){
        if(compiler == null)
            compiler = ToolProvider.getSystemJavaCompiler();
        return compiler;
    }
    
    public Trees getParseTree(){
        return Trees.instance(this.getCompilationTask());
    }
    
    private CompilationTask getCompilationTask(){
        return this.getCompiler().getTask(
                null
                , getDefaultFileManager()
                , getDiagnostics()
                , null
                , null
                , compilationUnits());
    }
    
    public boolean performCompilation(){
        boolean successful = this.getCompilationTask().call();
        try {
            manager.close();
        } catch (IOException ex) {
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return successful;
    }
    
    public void printErrors(){
        for(Diagnostic d : diagnostics.getDiagnostics())        
            System.out.format("Error on line %d in %s", d.getLineNumber(), d);
    }
    
    private ModelMemoryManager getDefaultFileManager(){
        if(manager == null)
            manager = new ModelMemoryManager(this.getCompiler());
        return manager;
    }
    
    public DiagnosticCollector getDiagnostics(){
        diagnostics = new DiagnosticCollector<JavaFileObject>();
        return diagnostics;
    }
    
    private List<SourceModel> compilationUnits(){
        return Arrays.asList(new SourceModel[]{source});
    }
    
    
    
    
    
    /************/
    
    private class SourceModel extends SimpleJavaFileObject{
        private String name;
        private String source;
        
        /*
        could probably move the filename creation logic, ie URI.create
        to some other class. maybe each model can handle their own URI
        maybe It can take a parameter that tells it wether or not to
        write a .class file.
        
        that would render this helper class almost un-necessary.
        */
        
        public SourceModel(String source, String name){
        super(URI.create("string:///" +name.replaceAll("\\.", "/") + Kind.SOURCE.extension)
                , Kind.SOURCE);
            this.name = name;
            this.source = source;
        }
         
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors)
                throws IOException {
            return source ;
        }
 
        public String getQualifiedName() {
            return name;
        }
 
        public void setQualifiedName(String qualifiedName) {
            this.name = qualifiedName;
        }
 
        public String getSource() {
            return source;
        }
 
        public void setSource(String sourceCode) {
            this.source = sourceCode;
        }
    }
    
    private class ModelOutput extends SimpleJavaFileObject {
        private final ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        public ModelOutput(String name, Kind kind){
            super(URI.create("memo:///" + name.replace('.', '/') + kind.extension), kind);
        }
        
        @Override
        public ByteArrayOutputStream openOutputStream() {
            return this.output;
        }
    }
    
    private class ModelMemoryManager extends ForwardingJavaFileManager{
        private HashMap map;
        
        ModelMemoryManager(JavaCompiler compiler){
            super(compiler.getStandardFileManager(null, null, null));
            map = new HashMap();
        }
        
        @Override
        public ModelOutput getJavaFileForOutput
                (Location location, String name, Kind kind, FileObject source) {
            ModelOutput mc = new ModelOutput(name, kind);
            this.map.put(name, mc);
            return mc;
        }
        
    }
    
}
