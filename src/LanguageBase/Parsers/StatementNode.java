/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

/**
 *
 * @author arthur
 */
public class StatementNode {
    private int start, end;
    private final BlockNode parentBlock;
    private BlockNode childBlock;
    
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
        return false;
    }
    
    public ScopeType getScope(){
        
    }
    
    private String[] sourceTokens(){
        return this.source().split(" ");
    }
}
