/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ClassType;
import Types.ScopeType;

/**
 *
 * @author Arthur
 */
public class VariableModel extends BaseModel{
    private ScopeType scope;
    private ClassModel type;
    private ClassType staticOrInstance;
    private String value;
    
    private VariableModel(){
        //used only for returning a new VariableModel from parsed source code.
    }
    
    public VariableModel(ScopeType scope, ClassModel type, String name){
        this.scope = scope;
        this.type = type;
        this.name = name;
    }
    
    public VariableModel(ClassType staticOrInstance, ClassModel objectType, String name){
        this.staticOrInstance = staticOrInstance;
        this.type = objectType;
        this.name = name;
    }
    
    /*
     * Getters
     */
    public ClassModel getObjectType(){
        return type;
    }
    @Override
    public ClassType getType(){
        return staticOrInstance;
    }
    
    public void setType(ClassType aType){
        staticOrInstance = aType;
    }
    
    /*
     * Setters
     */
    public void setObjectType(ClassModel type){
        this.type = type;
    }
    public void setScope(ScopeType scope){
        this.scope = scope;
    }
    
    public String getValue(){
        return value;
    }
    public void setValue(String aValue){
        this.value = aValue;
    }
    
    /*
     * Overridden Methods
     */
    @Override
    public String toSourceString() {
        String source = new String();
        if(this.scope != ScopeType.NONE)
            source = this.scope.toString().toLowerCase() + " ";
        return source+this.type.name()+" "+this.name()+";";
    }

    @Override
    protected void setUpFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean parseDeclaration(String decl){
        String[] tokens = decl.split("\\s+");
        int size = tokens.length;
        //System.out.println(ScopeType.PRIVATE.toString().toLowerCase());
        if(size < 2)
            return false;
        //need to check for duplicate names before setting name.
        this.setName(tokens[size-1]);
        //need to check the project for a class with this object type string
        this.setObjectType(new ClassModel(tokens[size-2]));
        if(size == 2)
            return true;
        return true;
    }
    
    public static VariableModel parseSource(String source){
        VariableModel newVar = new VariableModel();
        if(source.contains("=")){
            String[] tokens = source.split("=");
            for(String s : tokens)
                System.out.println(s);
            newVar.parseDeclaration(tokens[0]);
            newVar.setValue(tokens[1]);
        }
        String[] tokens = source.split("\\s+");
        for(String s : tokens){
        }
        return new VariableModel();
        /*
        if(source.indexOf("=") == -1)
        parse through the string
        1. check to see if it contains the substring private or public, if not its NONE.
            set its type accordingly
        2. check to see if it contains 'static'
            setits type accordingly
        3. the next substring should be its Object type.
            set its ObjectType as a new ClassModel with that string.
        4. the next substring should be its name
            set the name accordingly
        5. if theres an = sign, everything after it should be saved as the
            variables value.
        */
    }
    
    
}
