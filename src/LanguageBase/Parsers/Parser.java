/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import LanguageBase.Parsers.Stacks.BracketStack;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public abstract class Parser extends BaseParseTree{
    protected LinkedList<String> errors;
    protected BracketStack stack;
    protected int lineCount;
    protected int statementStart;
    
    protected Parser(){
        errors = new LinkedList();
        stack = new BracketStack();
        lineCount = 1;
    }
    
    protected Parser(String source) throws ParseException{
        this();
        this.source = source;
    }
    
    public LinkedList<String> getErrors(){
        return errors;
    }
    
    protected void parseFrom(int index) throws ParseException{
        statementStart = index;
        while(!this.indexOutOfRange(index)){
            switch(this.source.charAt(index)){
                case '{':
                    this.stackIt("{", index);
                    this.parseOpenCurlyBracket(index);
                    break;
                case '}':
                    this.stackIt("}", index);
                    this.parseCloseCurlyBracket(index);
                    break;
                case '[':
                    this.stackIt("[", index);
                    this.parseOpenBracket(index);
                    break;
                case ']':
                    this.stackIt("]", index);
                    this.parseCloseBracket(index);
                    break;
                case '(':
                    this.stackIt("(", index);
                    this.parseOpenParen(index);
                    break;
                case ')':
                    this.stackIt(")", index);
                    this.parseCloseParen(index);
                    break;
                case ';':
                    this.parseSemicolon(index);
                    break;
                case '.':
                    this.parsePeriod(index);
                    break;
                case '\n':
                    lineCount++;
                    break;
                case 'e':
                    this.parseReservedWord(index);
                    break;
            }
            index++;
        }
    }

    private void stackIt(String symbol, int index) throws ParseException {
        try {
            stack.processSymbol(symbol);
        } catch (BracketStack.StackException ex) {
            throw new ParseException(lineCount);
        }
    }
    
    public int getLineCount(){
        return lineCount;
    }
    
    protected abstract void parseOpenCurlyBracket(int index);
    protected abstract void parseCloseCurlyBracket(int index);
    protected abstract void parseOpenParen(int index);
    protected abstract void parseCloseParen(int index);
    protected abstract void parseOpenBracket(int index);
    protected abstract void parseCloseBracket(int index);
    protected abstract void parsePeriod(int index);
    protected abstract void parseSemicolon(int index);
    protected abstract void parseReservedWord(int index);
}
