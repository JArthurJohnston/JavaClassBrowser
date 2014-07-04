/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Nodes;

import LanguageBase.Parsers.BaseParseTree;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class BlockNode extends BaseParseTreeNode{
    private LinkedList<StatementNode> statements;
    private boolean isSingleStatement;
    
    
    protected BlockNode(){
        statements = new LinkedList();
    }
    
    public BlockNode(BaseParseTree parentTree){
        this();
        this.parentTree = parentTree;
    }
    
    public BlockNode(StatementNode parent, int index){
        this();
        this.parentNode = parent;
        this.start = index;
    }
    
    public void isSingleStatement(boolean single){
        this.isSingleStatement = single;
    }
    
    public boolean isSingleStatement(){
        return this.isSingleStatement;
    }
    
    public BlockNode close(int index){
        this.end = index;
        if(this.isRoot())
            return this;
        return this.parentNode.getBlock();
    }
    
    public LinkedList<StatementNode> getStatements(){
        return statements;
    }
    
    public StatementNode addStatement(int start, int end){
        this.statements.add(new StatementNode(this, start, end));
        return this.statements.getLast();
    }
    
    @Override
    public boolean isRoot(){
        return !(this.parentTree == null);
    }
    
    @Override
    public StatementNode getParentNode(){
        return (StatementNode)super.getParentNode();
    }
    
    @Override
    public BlockNode getBlock(){
        if(this.isRoot())
            return this;
        return parentNode.getBlock();
    }
    
    @Override
    protected String tabString(){
        if(this.isRoot())
            return super.tabString();
        return '\t' + this.getBlock().tabString();
    }
    
    @Override
    public String getFormattedSource(){
        return this.buildFormattedSource(new StringBuilder()).toString();
    }
    
    public StringBuilder buildFormattedSource(StringBuilder sb){
        if(!this.isSingleStatement){
            sb.append(" {\n");
        }
        for(StatementNode sn : this.getStatements()){
            sn.buildFormattedSource(sb);
        }
        if(!this.isSingleStatement){
            sb.append(this.getBlock().tabString());
            sb.append("}");
        }
        return sb;
    }
}
