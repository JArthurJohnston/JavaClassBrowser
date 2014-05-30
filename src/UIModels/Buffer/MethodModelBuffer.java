/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Models.BaseModel;
import Models.ClassModel;
import Models.MethodModel;
import Models.VariableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class MethodModelBuffer extends BaseModelBuffer{
    private boolean isAbstract;
    private ClassModel returnType;
    private String body;
    private LinkedList<VariableModel> parameters;
    
    public MethodModelBuffer(MethodModel aMethod){
        this.initializeFromModel(aMethod);
    }
    
    @Override
    protected void initializeFromModel(BaseModel aModel){
        super.initializeFromModel(aModel);
        this.initializeFromMethod((MethodModel)aModel);
    }
    
    private void initializeFromMethod(MethodModel aMethod){
        isAbstract = aMethod.isAbstract();
        this.scope = aMethod.getScope();
        this.returnType = aMethod.getReturnType();
        this.body = aMethod.getMethodBody();
    }
    
    public boolean isAbstract(){
        return isAbstract;
    }
    public ClassModel getReturnType(){
        return returnType;
    }
    public String getBody(){
        return body;
    }
    public LinkedList getParameters(){
        return parameters;
    }
    
    @Override
    public MethodModel getEntity(){
        return (MethodModel)entity;
    }

    @Override
    public String editableString() {
        return this.getEntity().toSourceString();
    }

    public void parseDeclaration(String source) {
        this.parseAbstract(
            this.parseStatic(
                this.parseScope(
                    this.splitAtWhiteSpaces(source))));
        
    }
    
    @Override
    public void parseSource(String source){
        ArrayList<String> tokens = this.splitAndParseSource(source);
        this.parseDeclaration(tokens.get(0));
        if(tokens.size() < 2)
            return;
        this.body = this.removeSemicolon(tokens.get(1));
    }
    
    private void parseParameters(String source){
        String[] tokens = source
                .substring(source.indexOf('('), source.indexOf(')'))
                .split(",");
        for(int i = 0; i < tokens.length; i++){
            this.addParameterFromToken(tokens[i].split("\\s+"));
        }  
    }
    
    private void addParameterFromToken(String[] param){
        parameters.add(
                new VariableModel(param[1], 
                        entity.getProject()
                                .findClass(param[0])));
    }
    
    private ArrayList<String> splitAndParseSource(String source){
        return new ArrayList<String>(Arrays.asList(source.split("=")));
    }
    
    public ArrayList<String> parseAbstract(ArrayList<String> tokens){
        this.isAbstract = this.clearToken(tokens, "abstract");
        return tokens;
    }
    
    private ArrayList<String> parseReturnType(ArrayList<String> tokens){
        
    }
}
