package Models;

import Types.ClassType;

/**
 *
 * @author Arthur
 */
public class VariableModel extends ClassModel{
    
    private Object value;
    private ClassType classType;
    private Object parent; //could be a method or class
    
    public VariableModel(Object owner, String name, ClassType type){
        this.parent = owner;
        this.name = name;
        this.classType = type;
        value = new Object();
        /*
         * Todo: variables NEED to have a type specified when theyre made
         * you cant just rely on using value.getClass()
         * because they dont necessarily have to have a value when 
         *  theyre made
         */
    }
    public VariableModel(Object owner, String name, ClassType type, Object value){
        this.parent = owner;
        this.name = name;
        this.classType = type;
        this.value = value;
    }
    
    /*
     * Getters
     */
    
    //class or instance variables
    public ClassType classType(){
        return classType;
    }
    public String name(){
        return name;
    }
    //actual type of the variable(int, string, char, etc...)
    public Object type(){
        return value.getClass();
    }
    
    public Object value(){
        return value;
    }
    public Object parent(){
        return parent;
    }
    /*
     * Setters
     */
    public void setName(String newName){
        name = newName;
    }
    
    public void setValue(Object newVal){
        if(newVal.getClass() == this.type()){
            value = newVal;
        }
        //Todo: throw an error message if false
    }
    public ClassType scope(){
        return scope;
    }
    
    
    @Override
    public String toSourceString(){
        String source = "";
        if(this.scope() != null){
            source = source + (scope.toString().toLowerCase()+" ");
        }
        source= source +(this.value.getClass().getSimpleName()+" ");
        source = source +(this.name().toString());
        if(this.value() != null){
            source = source+(" = "+value.toString());
        }
        source = source + (";\n");
        return source;
    }
}
/*
 * Notes: 
 * Todo: method categories
 */
