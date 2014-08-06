/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Spike;

import Exceptions.DoesNotExistException;
import Models.BaseModel;
import Models.ClassModel;
import Models.MethodSignature;
import Models.ProjectModel;
import Models.VariableModel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
public class MethodCallParser {
    private final ProjectModel project;
    private final String source;
    private final LinkedList<BaseModel> references;
    private final LinkedList<String> notFound;
    private final HashMap<String, ClassModel> symbolTable;
    /*
    should source be final and passed into the constructor, or should it be
    set by a setter. 
    alternatively, we can get rid of source altogether and just pass in whatever
    string we want to parse, and parse it on the fly without saving it.
    */
    
    public MethodCallParser(final BaseModel aModel){
        this(aModel, "");
    }
    
    public MethodCallParser(final BaseModel aModel, final String source){
        this.project = aModel.getProject();
        this.source = source;
        this.references = new LinkedList();
        this.notFound = new LinkedList();
        this.symbolTable = new HashMap();
        if(aModel.isClass())
            this.fillTableWithClassVars(aModel);
    }
    
    private void fillTableWithClassVars(BaseModel aModel){
        //bad implementation
        for (VariableModel aVar : ((ClassModel)aModel).getVariables().values()) {
            this.symbolTable.put(aVar.name(), aVar.getObjectType());
        }
    }

    public ProjectModel getProject() {
        return this.project;
    }

    public String getSource() {
        return source;
    }

    public LinkedList<BaseModel> getReferences() {
        return this.references;
    }
    
    public void parseForReference(final String source){
        try {
            this.references.add(this.project.findClass(source));
        } catch (DoesNotExistException ex) {
            this.notFound.add(source);
        }
    }
    
    private BaseModel findReferenceFor(String source){
        BaseModel ref;
        try {
            ref = this.project.findClass(source);
        } catch (DoesNotExistException ex) {
        }
        return null;
    }
    
    public LinkedList<String> getNotFoundSource(){
        return this.notFound;
    }
    
    public HashMap<String, ClassModel> getSymbolTable(){
        return this.symbolTable;
    }
    
    public void addSymbol(final String symbolName, final ClassModel symbolModel){
        this.symbolTable.put(symbolName, symbolModel);
    }
    
    public MethodSignature methodSignatureFromSource(final String source){
        MethodSignature sig = null;
        String[] tokens = source.split("[()]");
        if(tokens.length > 0)
            sig = new MethodSignature(tokens[0]);
        if(tokens.length > 1)
            this.parseMethodCallArguments(tokens[1].trim(), sig);
        return sig;
    }
    
    public void parseMethodCallArguments(String source, MethodSignature sig){
        String[] arguments = source.split(",");
        for (String arg : arguments) {
            if(this.symbolTable.containsKey(arg.trim()))
                sig.arguments().add(this.symbolTable.get(arg.trim()));
        }
    }
    
    public void parseMethodDeclarationArguments(String source){
        String[] args = source.split(",");
        for (String string : args) {
            //not done yet
        }
    }
}
