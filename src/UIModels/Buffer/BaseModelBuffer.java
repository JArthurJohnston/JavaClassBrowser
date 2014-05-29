/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import MainBase.SortedList;
import Models.BaseModel;
import Models.ProjectModel;
import Types.ClassType;
import Types.ScopeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created to hold changes and check their
 * validity before saving them to a model.
 * 
 * @author arthur
 */
public abstract class BaseModelBuffer {
    protected BaseModel entity;
    
    protected String name;
    protected ScopeType scope;
    protected boolean isFinal;
    protected ClassType type; // will probably be pushed down
    protected LinkedList<String> warnings;
    
    /*
    I wonder if i can replace these with a HashMap...
    -no, javas not dynamic enough
    */
    protected BaseModelBuffer(){}
    
    public BaseModelBuffer(BaseModel aModel){
        this.initializeFromModel(aModel);
    }
    
    protected void initializeFromModel(BaseModel aModel){
        warnings = new LinkedList();
        entity = aModel;
        name = aModel.name();
    }
    
    protected SortedList testFields(){
        return new SortedList().addElm(name);
    }
    
    public boolean isValid(){
        for(Object o : this.testFields())
            if(o == null)
                return false;
        if(this.name.isEmpty())
            return false;
        return true;
    }
    
    public void setClassType(ClassType aType){
        this.type = aType;
    }
    
    public ClassType getClasType(){
        if(type == null)
            return ClassType.INSTANCE;
        return type;
    }
    
    public BaseModel saveChanges(){
        if(this.isValid())
            return null;
        return this.getEntity();
    }
    
    public void revertChanges(){
        this.initializeFromModel(entity);
    }
    
    public BaseModel getEntity(){
        return entity;
    }
    
    public void setName(String aName){
        if(aName == null)
            return;
        if(!aName.isEmpty()
                || !ProjectModel.getReservedWords().contains(aName))
            this.name = this.removeSemicolon(aName);
    }
    
    protected String removeSemicolon(String aString){
        if(aString.charAt(aString.length()-1) == ';')
            return aString.substring(0, aString.length()-1);
        return aString;
    }
    
    public void saveToModel(){
        if(this.isValid())
            this.getEntity().setName(name);
    }
    
    public String getName(){
        if(this.name == null)
            this.name = new String();
        return this.name;
    }
    
    public ScopeType getScope(){
        if(this.scope == null)
            this.scope = ScopeType.NONE;
        return this.scope;
    }
    
    public void setScope(ScopeType scope){
        this.scope = scope;
    }
    
    public ClassType getType(){
        return this.type;
    }
    
    public boolean isFinal(){
        return isFinal;
    }
    
    public String getSourceString(){
        return this.getEntity().toSourceString();
    }
    
    protected ArrayList<String> splitAtWhiteSpaces(String source){
        if(source.isEmpty())
            return new ArrayList();
        return new ArrayList(Arrays.asList(source.split("\\s+")));
    }
    
    public ArrayList<String> parseScope(ArrayList<String> sourceTokens){
        for(ScopeType s : ScopeType.values())
            if(sourceTokens.contains(s.toString().toLowerCase())){
                this.setScope(s);
                sourceTokens.remove(s.toString().toLowerCase());
                break;
            }else
                this.setScope(ScopeType.NONE);
        return sourceTokens;
    }
    
    public ArrayList<String> parseStatic(ArrayList<String> tokens){
        if(this.clearToken(tokens, "static"))
            this.setClassType(ClassType.STATIC);
        else
            this.setClassType(ClassType.INSTANCE);
        return tokens;
    }
    
    public ArrayList<String> parseClassType(ArrayList<String> sourceTokens){
        for(ClassType c : ClassType.values())
            if(sourceTokens.contains(c.toString().toLowerCase())){
                this.setClassType(c);
                sourceTokens.remove(c.toString().toLowerCase());
                break;
            }
        return sourceTokens;
    }
    
    public boolean clearToken(ArrayList<String> tokens, String value){
        try{
            tokens.remove(tokens.indexOf(value));
        }catch (IndexOutOfBoundsException ex){
            return false;
        }
        return true;
    }
    
    public abstract String editableString();
    public abstract void parseSource(String source);
}
