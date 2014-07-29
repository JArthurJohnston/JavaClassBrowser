/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Nodes;

import LanguageBase.Parsers.Parser;
import Types.ClassType;
import Types.ScopeType;

/**
 *
 * @author arthur
 */
public class StatementNode extends BaseNode{
    private final int start;
    private int end, openParenPtr, closeParenPtr;
    private final BlockNode parentBlock;
    private BlockNode childBlock;
    private ScopeType scope;
    private ClassType side;
    private StatementType type; 
    
    
    public StatementNode(final BlockNode parent, final int start){
        this.parentBlock = parent;
        this.start = start;
    }
    
    public StatementNode(final BlockNode parent, final int start, final int end){
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
    
    public void close(final int end){
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
    
    public int openParenPointer(){
        return this.openParenPtr;
    }
    
    public void setOpenParen(final int index){
        this.openParenPtr = index;
    }
    
    public int closeParenPointer(){
        return this.closeParenPtr;
    }
    
    public void setCloseParen(final int index){
        this.closeParenPtr = index;
    }
    
    public void parseStatement(){
        for(String s : this.getSource().split("\\s+")) { //split at white spaces
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
                    this.findReference(s);
                    break;
            }
        }
    }
    
    private void findReference(String source){
        if(this.getTree().hasModel())
            
    }
    
    private String argumentSegment(){
        return new String(source().substring(this.openParenPtr, this.closeParenPtr)).trim();
    }
    
    public boolean isMethodDeclaration(){
        return this.type == StatementType.MethodDecl;
    }
    
    
}
