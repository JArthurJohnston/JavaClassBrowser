/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanguageBase.Parsers;

import LanguageBase.Parsers.Nodes.BlockNode;
import LanguageBase.Parsers.Nodes.StatementNode;
import LanguageBase.Parsers.Stacks.BracketStack;

/**
 *
 * @author arthur
 */
public class BlockParser extends BaseParseTree {

    private BracketStack stack;
    private int statementStart;
    private BlockNode currentBlock;
    private BlockNode root;
    private StatementNode currentStatement;

    private BlockParser() {
        root = new BlockNode(this);
        currentBlock = root;
        stack = new BracketStack();
    }

    public BlockParser(String source) {
        this();
        this.source = source;
        try {
            this.parseFrom(0);
        } catch (ParseException ex) {
            error = ex.getMessage();
        }
    }

    private void parseFrom(int index) throws ParseException {
        statementStart = index;
        while (!this.indexOutOfRange(index)) {
            switch (this.source.charAt(index)) {
                case '{':
                    stackIt("{", index);
                    this.addBlockToStatementAt(index);
                    break;
                case '}':
                    stackIt("}", index);
                    this.closeBlock(index);
                    break;
                case '(':
                    stackIt("(", index);
                    break;
                case ')':
                    stackIt(")", index);
                    if(!stack.isOpenParen())
                        if(!this.isCurrentSymbol(index+1, '.'))
                            if (this.isSingleStatementBlock(index+1))
                                this.addSingleStatementAndBlockAtIndex(index);
                            else if(this.nextNonWhiteCharFrom(index+1) == '{')
                                this.addStatementToBlock(index);
                    break;
                case ';':
                    this.addStatementToBlock(index);
                    break;
                case '"':
                    //skip over string literals
                    break;
                case '\'':
                    //skip over string literals
                    break;
                case '/':
                    //add statement for comments
                    if (source.charAt(index + 1) == '/') {
                    } else if (source.charAt(index + 1) == '*') {
                    }
                    break;
                default:
            }
            index++;
        }
    }

    private void addStatementToBlock(int index) {
        currentStatement = currentBlock
                .addStatement(statementStart, index);
        if(currentBlock.isSingleStatement())
            this.closeBlock(index);
        statementStart = index + 1;
    }

    private boolean isSpecialStatement(int index) {
        return this.isCurrentSymbol(index, "while")
                || this.isCurrentSymbol(index, "else");
    }

    private void closeBlock(int index) {
        currentBlock = currentBlock.close(index);
    }

    private boolean isSingleStatementBlock(int index) {
        return !(this.isCurrentSymbol(index, ';')
                || this.isCurrentSymbol(index, '{'));
    }

    private void addSingleStatementAndBlockAtIndex(int index) throws ParseException {
        stackIt("{{", index);
        currentStatement = currentBlock.addStatement(statementStart, index);
        this.addBlockToStatementAt(index);
        statementStart = index + 1;
    }
    
    private void addBlockToStatementAt(int index){
        currentBlock = currentStatement.addBlock(index + 1);
    }

    private void stackIt(String symbol, int index) throws ParseException {
        try {
            stack.processSymbol(symbol);
        } catch (BracketStack.StackException ex) {
            throw new ParseException(this.lineNumberFromIndex(index));
        }
    }
    
    public BlockNode getTree(){
        return root;
    }

}
