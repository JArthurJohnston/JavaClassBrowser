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
public class StatementNode extends BaseParseTreeNode{
    private BlockNode block;
    
    public StatementNode(BaseParseTree parent, int start, int end){
        super(parent, start, end);
    }
    public StatementNode(BaseParseTreeNode parent, int start, int end){
        super(parent, start, end);
    }
    
    public BlockNode addBlock(int start){
        block = new BlockNode(this, start);
        return block;
    }
    
    @Override
    public BlockNode getBlock(){
        return this.parentNode.getBlock();
    }
    
    public BlockNode getChildBlock(){
        return block;
    }
}
