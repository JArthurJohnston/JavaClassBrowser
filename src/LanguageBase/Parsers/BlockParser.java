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
        if(!stack.isEmpty())
            error = "Uncloased '"+ stack.getLast()+"' on the stack.";
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
                    if (!stack.isOpenParen())
                        if (!this.isCurrentSymbol(index + 1, '.'))
                            if (this.isSingleStatementBlock(index + 1))
                                this.addSingleStatementAndBlockAtIndex(index);
                            else if (this.nextNonWhiteCharFrom(index + 1) == '{')
                                this.addStatementToBlock(index);
                    break;
                case ';':
                    if (!stack.isOpenParen())
                        this.addStatementToBlock(index);
                    break;
                case 'e':
                    if (this.isCurrentSymbol(index, "else"))
                        this.addSpecialSyntaxBlock(index, "else");
                    //else 
                    break;
                case 'd':
                    if (this.isCurrentSymbol(index, "do"))
                        this.addSpecialSyntaxBlock(index, "do");
                    break;
                case 't':
                    if (this.isCurrentSymbol(index, "try"))
                        this.addSpecialSyntaxBlock(index, "try");
                    break;
                case 'c':
                    //catch
                    /*
                     catch statements have brackets, so this may not be necessary
                     */
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

    /**
     * Used for do and else statements.
     */
    private void addSpecialSyntaxBlock(int index, String symbol) throws ParseException {
        statementStart = index;
        if (this.isSingleStatementBlock(index + symbol.length()))
            this.addSingleStatementAndBlockAtIndex(index + symbol.length()-1);
        else
            this.addStatementToBlock(index + symbol.length()-1);

    }

    private void addStatementToBlock(int index) throws ParseException {
        if(this.nextNonWhiteCharFrom(statementStart) == '{')
            statementStart = this.nextIndexOfCharFromIndex('{', statementStart)+1;
        currentStatement = currentBlock
                .addStatement(statementStart, index + 1);
        if (stack.isSingleStatementBlock())
            this.closeBlock(index);
        statementStart = index + 1;
    }

    private void closeBlock(int index) throws ParseException {
        if(stack.isSingleStatementBlock())
            stackIt("}}", index);
        if(this.isCurrentSymbol(index, '}'))
            statementStart++;
        currentBlock = currentBlock.close(index);
    }

    private boolean isSingleStatementBlock(int index) {
        char next = this.nextNonWhiteCharFrom(index);
        return !(next == ';' || next == '{');
    }

    private void addSingleStatementAndBlockAtIndex(int index) throws ParseException {
        stackIt("{{", index);
        currentStatement = currentBlock.addStatement(statementStart, index + 1);
        this.addBlockToStatementAt(index);
        statementStart = index + 1;
    }

    private void addBlockToStatementAt(int index) {
        currentBlock = currentStatement.addBlock(index + 1);
    }

    private void stackIt(String symbol, int index) throws ParseException {
        try {
            stack.processSymbol(symbol);
        } catch (BracketStack.StackException ex) {
            throw new ParseException(this.lineNumberFromIndex(index));
        }
    }

    public BlockNode getTree() {
        return root;
    }

}
