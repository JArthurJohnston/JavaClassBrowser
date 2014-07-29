/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Nodes;

import LanguageBase.Parsers.Parser;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class BlockNode extends BaseNode{
    private Parser tree;
    private LinkedList<StatementNode> statements;
    private StatementNode parent;
    private boolean isSingleStatement;
    
    protected BlockNode(){
        statements = new LinkedList();
    }
    
    public BlockNode(Parser tree){
        this();
        this.tree = tree;
    }
    
    public BlockNode(StatementNode parent){
        this(parent.getTree());
        this.parent = parent;
    }
    
    public BlockNode(StatementNode parent, boolean isSingleStatement){
        this(parent);
        this.isSingleStatement = isSingleStatement;
    }
    
    public Parser getTree(){
        return tree;
    }
    
    public LinkedList<StatementNode> getStatements(){
        return statements;
    }
    
    public StatementNode addStatement(int start, int end){
        this.statements.add(new StatementNode(this, start, end));
        return this.statements.getLast();
    }
    
    public StatementNode addStatement(int start){
        this.statements.add(new StatementNode(this, start));
        return this.statements.getLast();
    }
    
    public StatementNode getParentStatement(){
        return this.parent;
    }
    
    public BlockNode getParentBlock(){
        if(this.isRoot())
            return this;
        return this.getParentStatement().getParentBlock();
    }
    
    public void singleStatement(boolean isSingle){
        this.isSingleStatement = isSingle;
    }
    
    public boolean isSingleStatement(){
        return this.isSingleStatement;
    }
    
    public boolean isRoot(){
        return this.parent == null && this.tree != null;
    }
    
    public String source(){
        return tree.source();
    }
    
}
