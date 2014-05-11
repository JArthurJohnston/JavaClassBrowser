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
import Types.ScopeType;
import java.util.Arrays;
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
    
    public boolean parseDeclaration(String decl){
        LinkedList<String> tokens = new LinkedList(Arrays.asList(decl.split("\\s+")));
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
        for(ScopeType s : ScopeType.values())
            if(tokens.contains(s.toString().toLowerCase())){
                this.scope = s;
                tokens.remove(s.toString().toLowerCase());
                break;
            }
        
        for(ClassType c : ClassType.values())
            if(tokens.contains(c.toString().toLowerCase())){
                this.setType(c);
                tokens.remove(c.toString().toLowerCase());
                break;
            }
        if(tokens.isEmpty())
            return true;
        
        if(tokens.contains("final")){
            this.isFinal = true;
            tokens.remove("final");
        }
        return tokens.isEmpty();
    }
    
    public boolean parseSource(String source){
        if(source.contains("=")){
            this.parseDeclaration(source.split("=")[0]);
            this.setValue(source.split("=")[1]);
            return true;
        }
        return this.parseDeclaration(source);
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
    
}
