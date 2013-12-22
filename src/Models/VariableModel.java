package Models;

import Types.ClassType;

/**
 *
 * @author Arthur
 */
public class VariableModel extends ClassModel{
    private Object value;
    private ClassType classType;
    
    public VariableModel(ClassModel owner, String name, ClassType type){
        this.parentClass = owner;
        this.name = name;
        this.classType = type;
    }
    public VariableModel(ClassModel owner, String name, ClassType type, Object value){
        this.parentClass = owner;
        this.name = name;
        this.classType = type;
        this.value = value;
    }
    
    /*
     * Getters
     */
    /**
     * Whether or not the variable is static, or instance
     * @return 
     */
    public ClassType classType(){
        return classType;
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
