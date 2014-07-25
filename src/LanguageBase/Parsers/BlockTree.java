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
    private BlockNode currentBlock;
    private StatementNode currentStatement;
    
    public BlockTree(String source) throws ParseException{
        super(source);
        this.setUpRootNodeAndBlocks();
        this.parseFrom(0);
    }
    
    private void setUpRootNodeAndBlocks(){
        root =  new BlockNode(this);
        currentBlock = root;
        blocks = new LinkedList();
        blocks.add(root);
        
        /*
        start off with an open statement
        */
        currentStatement = currentBlock.addStatement(0);
    }
    
    public LinkedList<BlockNode> getBlocks(){
        return blocks;
    }
    
    public BlockNode getRootBlock(){
        return root;
    }

    @Override
    protected void parseOpenCurlyBracket(int index) {
        //this.addStatementToBlockEndingAt(index);
        currentStatement.close(index);
        this.currentBlock = this.currentStatement.getChildBlock();
        currentStatement = currentBlock.addStatement(index+1);//eliminates the need for statement start?
    }

    @Override
    protected void parseCloseCurlyBracket(int index) {
        statementStart = index+1;
        this.currentBlock = this.currentBlock.getParentBlock();
    }

    @Override
    protected void parseOpenParen(int index) {
    }

    @Override
    protected void parseCloseParen(int index) {
        for(char c : new char[]{';', '{', '.'})
            if(this.nextNonWhiteCharFrom(index +1) == c)
                return;
        if(stack.isOpenParen())
            return;
        if(currentStatement.isOpen()){
            currentStatement.close(index+1);
            currentBlock = currentStatement.getChildBlock();
            currentBlock.singleStatement(true);
            currentStatement = currentBlock.addStatement(index+1);
        }else{
            //this should be obsolete
            currentBlock = 
                this.addStatementToBlockEndingAt(index+1)
                        .getChildBlock();
            currentBlock.singleStatement(true);
            this.statementStart = index+1;
        }
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
        if(stack.isOpenParen())
            return;
        //this.addStatementToBlockEndingAt(index+1);
        currentStatement.close(index+1);
        this.statementStart = index+1;
    }
    
    @Override
    protected void parseReservedWord(int index){
        switch(source.charAt(index)){
            case 'e':
                if(this.isCurrentSymbol(index, "else"))
                    this.parseElseStatement(index);
                break;
        }
    }
    
    private void parseElseStatement(int index){
        int end = index + 4;
        /*
        while(true){
            if(this.isWhiteChar(++index))
                continue;
            else if(this.isCurrentSymbol(index, "if"))
                while()
            else if()
        }
        
        
        
        I could make an "open" statement. a statement with an end
        of 0. if the parser hits say, a '{' with an open statement, instead
        of creating a new statement and block, it will close the currently open statement 
        and add a block to it.
                */
        if(this.isNextSymbolFromIndex("if", index)){
            
        } else if(this.isNextSymbolFromIndex("{", index))
            end = this.nextIndexOfCharFromIndex('{', index);
        currentStatement = currentBlock.addStatement(index, end);
    }
    
    private StatementNode addStatementToBlockEndingAt(int index){
        this.currentStatement = 
                this.currentBlock
                        .addStatement(statementStart, index);
        return currentStatement;
    }
    
    /**
     * Tests I need to write
     * 
     * test skips string literals
     * test parse array declarations
     * test do-while
     * test references
     */
    
}
