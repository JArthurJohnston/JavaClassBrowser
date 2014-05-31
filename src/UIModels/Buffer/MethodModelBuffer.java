/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import MainBase.SortedList;
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
        super(aMethod);
    }
    
    //implement in super
    private void reInitFields(MethodModel aMethod){
        isAbstract = aMethod.isAbstract();
        this.scope = aMethod.getScope();
        this.returnType = aMethod.getReturnType();
        this.body = aMethod.getMethodBody();
    }
    
    @Override
    public SortedList modelFields(){
        return super.modelFields()
                .addElm(isAbstract)
                .addElm(returnType)
                .addElm(body);
    }
    
    public boolean isAbstract(){
        return isAbstract;
    }
    public ClassModel getReturnType(){
        if(returnType == null)
            return this.getEntity().getReturnType();
        return returnType;
    }
    public String getBody(){
        if(body != null)
            return body;
        return this.getEntity().getMethodBody();
    }
    public LinkedList<VariableModel> getParameters(){
        if(parameters == null)
            return this.getEntity().getParameters();
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
        if(!source.isEmpty())
            this.parseName(
                this.parseReturnType(
                    this.parseAbstract(
                        this.parseStatic(
                            this.parseScope(
                                this.splitAtWhiteSpaces(source))))));
    }
    
    @Override
    public void parseSource(String source){
        ArrayList<String> tokens = 
                this.splitAndParseSource(source);
        if(!tokens.isEmpty())
            this.parseDeclaration(tokens.get(0));
        if(tokens.size()>= 2)
            this.parseParameters(tokens.get(1));
        if(tokens.size() >= 3)
            this.body = tokens.get(2);
    }
    
    protected void parseParameters(String source){
        parameters = new LinkedList();
        String[] tokens = source.split(",");
        for(int i = 0; i < tokens.length; i++){
            this.addParameterFromToken(tokens[i].trim().split("\\s+"));
        }
    }
    
    /**
     * totally un-necessary outside of testing
     * consider deleting that test...
     * or pushing this method up and outta the way
     * 
     * @return 
     */
    protected LinkedList<VariableModel> initParams(){
        if(parameters == null)
            parameters = new LinkedList();
        return parameters;
    }
    
    protected void addParameterFromToken(String[] param){
        if(param == null || param.length < 2 || param[0].isEmpty() || param[1].isEmpty())
            return;
        parameters.add(
                new VariableModel(param[1], 
                        entity.getProject()
                                .findClass(param[0])));
    }
    
    protected ArrayList<String> splitAndParseSource(String source){
        return new ArrayList<>(Arrays.asList(source.split("[(){}]")));
    }
    
    //this can probably be pushed up.
    protected ArrayList<String> parseAbstract(ArrayList<String> tokens){
        this.isAbstract = this.clearToken(tokens, "abstract");
        return tokens;
    }
    
    protected ArrayList<String> parseReturnType(ArrayList<String> tokens){
        this.returnType = this.getEntity().getProject().findClass(tokens.get(0));
        tokens.remove(0);
        return tokens;
    }
    
    protected ArrayList<String> parseName(ArrayList<String> tokens){
        this.name = tokens.get(0);
        tokens.remove(0);
        return tokens;
    }
}
