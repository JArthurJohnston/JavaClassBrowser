/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Exceptions.DoesNotExistException;
import Models.BaseModel;
import Models.ClassModel;
import Models.InterfaceModel;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class ClassModelBuffer extends BaseModelBuffer{
    private boolean isAbstract;
    private ClassModel parentClass;
    private LinkedList<InterfaceModel> interfaceList;
    
    
    public ClassModelBuffer(ClassModel baseEntity){
        this.initializeFromModel(baseEntity);
    }
    
    @Override
    protected void initializeFromModel(BaseModel aModel){
        super.initializeFromModel(aModel);
        this.initializeFromClass((ClassModel)aModel);
    }
    
    private void initializeFromClass(ClassModel baseEntity){
        isAbstract = baseEntity.isAbstract();
        if(baseEntity.getParent().isClass())
            parentClass = (ClassModel)baseEntity.getParent();
        else
            parentClass = null;
        interfaceList = new LinkedList();
        scope = baseEntity.getScope();
    }
    
    @Override
    public ClassModel getEntity(){
        return (ClassModel)entity;
    }
    
    public LinkedList<InterfaceModel> getInterfaces(){
        return interfaceList;
    }
    
    @Override
    public boolean isValid(){
        if(this.name.compareTo(entity.name()) != 0)
            return entity.getProject().okToAddClass(name);
        return super.isValid();
    }
    public boolean isAbstract(){
        return isAbstract;
    }
    
    public void setAbstract(boolean isAbstract){
        this.isAbstract = isAbstract;
    }
    
    public ClassModel getParentClass(){
        return parentClass;
    }
    
    public String newClassName(){
        String newName = "NewClass";
        if(entity.getProject().okToAddClass(newName))
            return newName;
        int i = 0;
        while(!entity.getProject().okToAddClass(newName + ++i));
        return newName+i;
    }
    
    public ArrayList<String> parseName(ArrayList<String> tokens){
        int index = tokens.indexOf("class");
        if(index >=0 && tokens.size() >= index + 1){
            this.setName(tokens.get(index+1));
            tokens.remove(index);
            tokens.remove(index);
        }
        return tokens;
    }
    
    public ArrayList<String> parseAbstract(ArrayList<String> tokens){
        if(this.clearToken(tokens, "abstract"))
            this.setAbstract(true);
        else
            this.setAbstract(false);
        return tokens;
    }
    
    public ArrayList<String> parseParentClass(ArrayList<String> tokens){
        int index = tokens.indexOf("extends");
        if(index >=0 && tokens.size() >= index + 1){
            try {
                this.parentClass = entity.getProject().findClass(tokens.get(index+1));
            } catch (DoesNotExistException ex) {
                warnings.add("Couldnt find class" + tokens.get(index+1));
            }
            tokens.remove(index);
            tokens.remove(index);
        }else
            this.parentClass = null;
        return tokens;
    }
    
    public ArrayList<String> parseImplements(ArrayList<String> tokens){
        interfaceList.clear();
        while(tokens.contains("implements")){
            int index = tokens.indexOf("implements");
            if(index >=0 && tokens.size() >= index + 1){
                try {
                    this.interfaceList.add(
                            (InterfaceModel)entity
                                    .getProject()
                                    .findClass(tokens.get(index+1))
                    );
                } catch (DoesNotExistException ex) {
                    warnings.add("couldnt find class "+ tokens.get(index+1));
                }
                tokens.remove(index);
                tokens.remove(index);
            }
        }
        return tokens;
    }
    
    @Override
    public void saveToModel(){
        super.saveToModel();
        this.getEntity().setScope(this.getScope());
        this.getEntity().moveToClass(this.getParentClass());
        this.setModelInterfaces();
    }
    
    private void setModelInterfaces(){
        this.getEntity().setInterfaces(interfaceList);
    }
    
    @Override
    public String editableString() {
        return this.getEntity().getDeclaration();
    }
    
    @Override
    public void parseSource(String source){
        this.parseScope(
            this.parseName(
                this.parseAbstract(
                    this.parseParentClass(
                        this.parseImplements(
                            this.splitAtWhiteSpaces(source))))));
    }
    
}
