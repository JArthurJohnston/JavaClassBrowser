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
        start = this.trimWhiteSpaces(start, 1);
        end = this.trimWhiteSpaces(end, -1);
        if(end < start)
            return "";
        return new String(this.source().substring(start, end));
    }
    
    public void close(int end){
        this.end = end;
    }
    
    public boolean isOpen(){
        return this.end == 0;
    }
    
    private String source(){
        return this.getTree().source();
    }
    
    private int trimWhiteSpaces(int index, int step){
        if(index >= end)
            return index;
        while(true){
            if(Character.isWhitespace(source().charAt(index)) && 
                    index + step < end &&
                    index + step >=0)
                index += step;
            else 
                break;
        }
        return index;
    }
}
