/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ClassType;
import Types.ScopeType;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class VariableModel extends BaseModel{
    private ScopeType scope;
    private ClassModel type;
    private ClassType staticOrInstance;
    private String value;
    private boolean isFinal;
    
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
    
    public boolean isFinal(){
        return this.isFinal;
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
    @Override
    public void setName(String aString){
        this.name = this.removeSemicolon(aString);
    }
    public void setObjectType(ClassModel type){
        this.type = type;
    }
    public void setScope(ScopeType scope){
        this.scope = scope;
    }
    public ScopeType getScope(){
        return scope;
    }
    
    public String getValue(){
        return value;
    }
    public void setValue(String aValue){
        this.value = this.removeSemicolon(aValue);
    }
    
    private String removeSemicolon(String aString){
        if(aString.charAt(aString.length()-1) == ';')
            return aString.substring(0, aString.length()-1);
        return aString;
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
    public boolean isVariable(){
        return true;
    }
    
    @Override
    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean parseDeclaration(String decl){
        LinkedList<String> tokens = new LinkedList(Arrays.asList(decl.split("\\s+")));
        if(tokens.size() < 2)
            return false;
        //need to check for duplicate names before setting name.
        this.setName(tokens.getLast());
        tokens.removeLast();
        //need to check the project for a class with this object type string
        this.setObjectType(new ClassModel(tokens.getLast()));
        tokens.removeLast();
        if(tokens.isEmpty())
            return true;
        for(ScopeType s : ScopeType.values())
            if(tokens.contains(s.toString().toLowerCase())){
                this.setScope(s);
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
        if(!tokens.isEmpty())
            return false;
        return true;
    }
    
    public boolean parseSource(String source){
        if(source.contains("=")){
            this.parseDeclaration(source.split("=")[0]);
            this.setValue((source.split("=")[1]));
            return true;
        }
        return this.parseDeclaration(source);
    }
    
    public static VariableModel newFromSource(String source){
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

    @Override
    protected void setUpFields() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
