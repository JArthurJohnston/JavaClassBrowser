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
    
    public StatementNode(BlockNode parent, int start, int end){
        this.start = start;
        this.end = end;
        this.parentBlock = parent;
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
        return new String(this.source().substring(start, end));
    }
    
    public void setEnd(int end){
        this.end = end;
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
