/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Exceptions.AlreadyExistsException;
import MainBase.SortedList;
import Models.ClassModel;
import Models.VariableModel;
import Types.ClassType;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public  class VariableModelBuffer extends BaseModelBuffer{
    private ClassModel objectType;
    private String value;
    
    public VariableModelBuffer(VariableModel aVar){
        super(aVar);
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
    protected SortedList testFields(){
        return super.testFields()
                .addElm(type)
                .addElm(scope)
                .addElm(objectType);
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
        this.value = this.removeSemicolon(aString);
    }
    
    @Override
    public void parseSource(String source){
        if(source.contains("=")){
            this.parseDeclaration(source.split("=")[0]);
            this.setValue(source.split("=")[1]);
        }
    }
    
    
    
    public boolean parseDeclaration(String decl){
        ArrayList<String> tokens = this.splitAtWhiteSpaces(decl);
        if(tokens.size() < 2)
            return false;
        //need to check for duplicate names before setting name.
        this.setName(tokens.getLast());
        tokens.removeLast();
        //need to check the project for a class with this object type string
        this.objectType = new ClassModel(tokens.getLast());
        tokens.removeLast();
        if(tokens.isEmpty())
            return true;
        this.parseScope(tokens);
        this.parseClassType(tokens);
        
        if(tokens.isEmpty())
            return true;
        
        if(tokens.contains("final")){
            this.isFinal = true;
            tokens.remove("final");
        }
        return tokens.isEmpty();
    }
    
    @Override
    public void saveToModel(){
        super.saveToModel();
        this.getEntity()
                .setObjectType(objectType)
                .setType(type)
                .setScope(scope)
                .setValue(value)
                .setFinal(isFinal);
    }
    
    public void addModel() throws AlreadyExistsException{
        this.saveToModel();
        this.getEntity().getParent().addVariable(this.getEntity());
    }
    
    public ClassModel getObjectType(){
        return objectType;
    }

    @Override
    public String editableString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
