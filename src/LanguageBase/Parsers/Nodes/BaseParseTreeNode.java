/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Nodes;

import LanguageBase.Parsers.BaseParseTree;

/**
 *
 * @author arthur
 */
public abstract class BaseParseTreeNode {
    protected int start, end;
    protected BaseParseTree parentTree;
    protected BaseParseTreeNode parentNode;
    
    protected BaseParseTreeNode(){
        //for inheritence?
    } 
    
    public BaseParseTreeNode(BaseParseTree parent, int start){
        this();
        this.parentTree = parent;
        this.start = start;
    }
    
    public BaseParseTreeNode(BaseParseTree parent, int start, int end){
        this(parent, start);
        this.end = end;
    }
    
    public BaseParseTreeNode(BaseParseTreeNode parent, int start){
        this();
        this.parentNode = parent;
        this.start = start;
    }
    
    public BaseParseTreeNode(BaseParseTreeNode parent, int start, int end){
        this(parent, start);
        this.end = end;
    }
    
    public String getSource(){
        while(getParentTree().source().charAt(start) == ' '){
            start++;
        }
        return new String(getParentTree().source().substring(start, end));
    }
    
    public BaseParseTreeNode getParentNode(){
        return parentNode;
    }
    
    public BaseParseTree getParentTree(){
        if(parentTree == null)
            return parentNode.getParentTree();
        return parentTree;
    }
    
    public boolean isRoot(){
        return false;
    }
    
    protected boolean isCurrentSymbol(int index, char symbol){
        if (this.indexOutOfRange(index))
            return false;
        return this.getSource().charAt(index) == symbol;
    }
    protected boolean indexOutOfRange(int index){
        return index < 0 || index >= getSource().length();
    }
    
    public String getFormattedSource(){
        return this.getSource();
    }
    
    
    
    /**
     * Return this nodes parent block
     * @return 
     */
    public abstract BlockNode getBlock();
}
