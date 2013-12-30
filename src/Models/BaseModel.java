/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Arthur
 */
abstract class BaseModel {
    protected BaseModel parent;
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
    //getters
    public String getDescription(){
        return description;
    }
    public String getComment(){
        return comment;
    }
    public BaseModel getParent(){
        return parent;
    }
    
    
    //logic
    public boolean isMethod(){
        return false;
    }
    public String getPath(){
        return parent.getPath() + this.path;
    }
    @Override
    public String toString(){
        return this.name;
    }
    
    //Abstract Methods
    abstract public String toSourceString();
    abstract protected void setUpFields();
    
    
    
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
