/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Types.ClassType;
import Types.ScopeType;

/**
 *
 * @author arthur
 */
public class StatementNode {
    private int start, end;
    private final BlockNode parentBlock;
    private BlockNode childBlock;
    private ScopeType scope;
    private ClassType side;
    private StatementType type; 
    
    
    public StatementNode(BlockNode parent, int start){
        this.parentBlock = parent;
        this.start = start;
    }
    
    public StatementNode(BlockNode parent, int start, int end){
        this(parent, start);
        this.end = end;
    }
    
    public BlockNode getParentBlock(){
        return this.parentBlock;
    }
    
    public BlockNode getChildBlock(){
        if(this.childBlock == null)
            childBlock = new BlockNode(this);
        return this.childBlock;
    }
    
    public Parser getTree(){
        return this.parentBlock.getTree();
    }
    
    public String getSource(){
        if(end < start)
            return "";
        return this.source().substring(start, end).trim();
    }
    
    public void close(int end){
        this.end = end;
    }
    
    public boolean isOpen(){
        return this.end == 0;
    }
    
    private String source(){
        return this.parentBlock.source();
    }
    
    public boolean isClassDeclaration(){
        return this.type == StatementType.ClassDecl;
    }
    
    public ScopeType getScope(){
        if(this.scope == null)
            return ScopeType.NONE;
        return scope;
    }
    
    public ClassType getClassType(){
        if(this.side == null)
            return ClassType.INSTANCE;
        return side;
    }
    
    private String[] sourceTokens(){
        return this.source().split(" ");
    }
    
    public void parseStatement(){
        for(String s : this.source().split("\\s+")) //split at white spaces
            switch(s){
                case "class":
                    this.type = StatementType.ClassDecl;
                    break;
                case "static":
                    this.side = ClassType.STATIC;
                    break;
                case "private":
                    this.scope = ScopeType.PRIVATE;
                    break;
                case "public":
                    this.scope = ScopeType.PUBLIC;
                    break;
                case "protected":
                    this.scope = ScopeType.PROTECTED;
                    break;
                default:
                    break;
            }
    }
    
    public enum StatementType {
        ClassDecl, MethodDecl, VarDecl;
    }
    
    
}
