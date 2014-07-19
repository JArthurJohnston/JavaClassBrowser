/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class BlockTree extends Parser{
    private LinkedList<BlockNode> blocks;
    private BlockNode root;
    private BlockNode currentNode;
    
    public BlockTree(String source) throws ParseException{
        super(source);
        root =  new BlockNode(this);
        currentNode = root;
        blocks = new LinkedList();
        blocks.add(root);
    }
    
    public LinkedList<BlockNode> getBlocks(){
        return blocks;
    }
    
    public BlockNode getRootNode(){
        return root;
    }

    @Override
    protected void parseOpenCurlyBracket(int index) {
    }

    @Override
    protected void parseCloseCurlyBracket(int index) {
    }

    @Override
    protected void parseOpenParen(int index) {
    }

    @Override
    protected void parseCloseParen(int index) {
    }

    @Override
    protected void parseOpenBracket(int index) {
    }

    @Override
    protected void parseCloseBracket(int index) {
    }

    @Override
    protected void parsePeriod(int index) {
    }

    @Override
    protected void parseSemicolon(int index) {
    }
    
}
