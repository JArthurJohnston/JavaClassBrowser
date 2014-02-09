/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Arthur
 */
public abstract class BaseModel {
    protected String description;
    protected String path;
    protected String comment;
    protected String name;
    protected Boolean hasChange;
    protected final String defaultName = "DefaultName";
    protected Boolean isDefault = false;
    
    //getters
    public String name(){
        return name;
    }
    public boolean hasChange(){
        return hasChange;
    }
    public String defaultName(){
        return defaultName;
    }
    public Boolean isDefault(){
        return isDefault;
    }
    public String getDescription(){
        return description;
    }
    public String getComment(){
        return comment;
    }
    //setters
    public void setName(String name){
        this.name = name;
    }
    public void setPath(String path){
        this.path = path;
    }
    public void isDefault(Boolean isDefault){
        this.isDefault = isDefault;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setChanged(boolean isChange){
        this.hasChange = isChange;
    }
    
    protected boolean isClass(){
        return false;
    }
    protected boolean isProject(){
        return false;
    }
    protected boolean isMethod(){
        return false;
    }
    protected boolean isVaribale(){
        return false;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
    
    //Abstract Methods
    abstract public String toSourceString();
    abstract protected void setUpFields();
    abstract public String getPath();
    
    
    
    /*
     * 
     */
    private class Node{
        private Node previous;
        private Node next;
        private BaseModel value;
        
        public Node(BaseModel value){
            this.value = value;
        }
        public Node (BaseModel value, Node next, Node previous){
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
