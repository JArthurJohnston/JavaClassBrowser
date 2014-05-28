/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Models.ClassModel;
import Models.InterfaceModel;
import Models.PackageModel;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class ClassModelBuffer extends BaseModelBuffer{
    private boolean isAbstract;
    private ClassModel parentClass;
    private PackageModel parentPackage;
    private LinkedList<InterfaceModel> interfaceList;
    
    
    public ClassModelBuffer(ClassModel baseEntity){
        super(baseEntity);
        isAbstract = baseEntity.isAbstract();
        if(baseEntity.getParent().isClass())
            parentClass = (ClassModel)baseEntity.getParent();
        interfaceList = baseEntity.getInterfaces();
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
        if(this.name != entity.name())
            return entity.getProject().okToAddClass(name);
        return true;
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
    public PackageModel getParentPackage(){
        return parentPackage;
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
            this.parentClass = entity.getProject().findClass(tokens.get(index+1));
            tokens.remove(index);
            tokens.remove(index);
        }else
            this.parentClass = null;
        return tokens;
    }
    
    public ArrayList<String> parseImplements(ArrayList<String> tokens){
        while(tokens.contains("implements")){
            int index = tokens.indexOf("implements");
            if(index >=0 && tokens.size() >= index + 1){
                this.interfaceList.add(
                        (InterfaceModel)entity
                        .getProject()
                        .findClass(tokens.get(index+1))
                );
                tokens.remove(index);
                tokens.remove(index);
            }
        }
        return tokens;
    }
    
    
    

    @Override
    public String editableString() {
        return this.getEntity().getDeclaration();
    }
    
    @Override
    public void parseSource(String source){
        ArrayList<String> tokens = this.splitAtWhiteSpaces(source);
        if(tokens.size()< 2)
            return;
        this.parseScope(tokens);
        this.parseName(tokens);
        this.parseAbstract(tokens);
        this.parseParentClass(tokens);
        this.parseImplements(tokens);
    }
    
}
