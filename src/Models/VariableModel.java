package Models;

import Types.ClassType;

/**
 *
 * @author Arthur
 */
public class VariableModel {
    private Object owner; //Todo: may not be needed
    private Object value;
    private String name;
    private ClassType type;
    
    public VariableModel(Object owner, String name, ClassType type){
        this.owner = owner;
        this.name = name;
        this.type = type;
    }
    public VariableModel(Object owner, String name, ClassType type, Object value){
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.value = value;
    }
    
    /*
     * Getters
     */
    public ClassType classType(){
        return type;
    }
    public String name(){
        return name;
    }
    public Object type(){
        return value.getClass();
    }
    /*
     * Mutators
     */
    public void changeName(String newName){
        name = newName;
    }
    
}
/*
 * Notes: 
 * Todo: method categories
 */
