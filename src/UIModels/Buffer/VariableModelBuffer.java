/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Exceptions.AlreadyExistsException;
import Models.BaseModel;
import Models.ClassModel;
import Models.VariableModel;
import Types.ClassType;
import java.util.ArrayList;

/**
 *
 * @author arthur
 */
public  class VariableModelBuffer extends BaseModelBuffer{
    private ClassModel objectType;
    private String value;
    
    public VariableModelBuffer(VariableModel aVar){
        this.initializeFromModel(aVar);
    }
    
    @Override
    protected void initializeFromModel(BaseModel aModel){
        super.initializeFromModel(aModel);
        this.intitializeFromVar((VariableModel)aModel);
    }
    
    private void intitializeFromVar(VariableModel aVar){
        this.type = aVar.getType();
        this.scope = aVar.getScope();
        this.isFinal = aVar.isFinal();
        this.value = aVar.getValue();
        this.objectType = aVar.getObjectType();
    }
    
    @Override
    public VariableModel getEntity(){
        return (VariableModel)super.getEntity();
    }
    
    @Override
    public boolean isValid(){
        if(!this.getEntity().getParent().okToAddVariable(this.name))
            return false;
        return super.isValid();
    }
    
    public void setType(ClassType aType){
        this.type = aType;
    }
    
    public void setValue(String aString){
        this.value = this.removeSemicolon(aString).trim();
    }
    public String getValue(){
        return value;
    }
    
    public void setObjectType(ClassModel objectType){
        this.objectType = objectType;
    }
    
    public ClassModel getObjectType(){
        return objectType;
    }
    
    private void splitAndParseSource(String source){
        if(source.contains("=")){
            this.parseDeclaration(source.split("=")[0]);
            this.setValue(source.split("=")[1]);
        }else
            this.parseDeclaration(source);
    }
    
    @Override
    public void parseSource(String source){
        this.splitAndParseSource(this.removeSemicolon(source));
    }
    
    public ArrayList<String> parseName(ArrayList<String> tokens){
        this.setName(tokens.get(tokens.size()-1));
        tokens.remove(tokens.size()-1);
        return tokens;
    }
    
    public ArrayList<String> parseDeclaration(String decl){
        ArrayList<String> tokens = this.splitAtWhiteSpaces(decl);
        this.parseScope(tokens);
        this.parseFinal(tokens);
        this.parseStatic(tokens);
        this.parseObjectType(tokens);
        this.parseName(tokens);
        return tokens;
    }
    
    public ArrayList<String> parseObjectType(ArrayList<String> tokens){
        this.setObjectType(entity.getProject().findClass(tokens.get(0)));
        return tokens;
    }
    
    public ArrayList<String> parseFinal(ArrayList<String> tokens){
        if(this.clearToken(tokens, "final"))
            this.isFinal = true;
        else
            this.isFinal = false;
        return tokens;
    }
    
    @Override
    public void saveToModel(){
        super.saveToModel();
        this.getEntity()
                .setObjectType(objectType)
                .setType(type)
                .setScope(scope)
                .setValue(value)
                .setType(this.getType())
                .setFinal(isFinal);
    }
    
    public void addModel() throws AlreadyExistsException{
        this.saveToModel();
        this.getEntity().getParent().addVariable(this.getEntity());
    }

    @Override
    public String editableString() {
        return this.getEntity().toSourceString();
    }
    
}
