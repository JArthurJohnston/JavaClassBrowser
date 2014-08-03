/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Exceptions.DoesNotExistException;
import LanguageBase.Parsers.Stacks.BracketStack;
import Models.BaseModel;
import Models.ProjectModel;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public abstract class Parser extends BaseParseTree{
    protected BaseModel baseModel;
    protected LinkedList<String> errors;
    protected LinkedList<BaseModel> references;
    protected BracketStack stack;
    protected int lineCount;
    
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
    
    public BaseModel getModel(){
        return baseModel;
    }
    
    public void parseSource(String source) throws ParseException{
        this.source = source;
        this.parseFrom(0);
    }
    
    protected void parseFrom(int index) throws ParseException{
        while(!this.indexOutOfRange(index)){
            switch(this.source.charAt(index)){
                case '{':
                    this.stackIt("{");
                    this.parseOpenCurlyBracket(index);
                    break;
                case '}':
                    this.stackIt("}" );
                    this.parseCloseCurlyBracket(index);
                    break;
                case '[':
                    this.stackIt("[" );
                    this.parseOpenBracket(index);
                    break;
                case ']':
                    this.stackIt("]" );
                    this.parseCloseBracket(index);
                    break;
                case '(':
                    this.stackIt("(" );
                    this.parseOpenParen(index);
                    break;
                case ')':
                    this.stackIt(")" );
                    this.parseCloseParen(index);
                    break;
                case ';':
                    this.parseSemicolon(index);
                    break;
                case '.':
                    this.parsePeriod(index);
                    break;
                case '\'':
                    this.parseStringLiteral(index);
                    break;
                case '"':
                    this.parseStringLiteral(index);
                    break;
                case ',':
                    this.parseComma(index);
                    break;
                case '/':
                    if(this.isCurrentSymbol(index+1, '*')){
                        this.parseMultiLineComment(index);
                        return;
                    }
                    if(this.isCurrentSymbol(index+1, '/')){
                        this.parseSingleLineComment(index);
                        return; // since these methods call parseFrom()
                    }
                    break;
                case '\n':
                    lineCount++;
                    break;
                case 'e':
                    this.parseReservedWord(index);
                    break;
                case 'd':
                    this.parseReservedWord(index);
                    break;
                case ' ':
                    this.parseEmptySpace(index);
                    break;
            }
            index++;
        }
    }

    private void stackIt(final String symbol) throws ParseException {
        try {
            stack.processSymbol(symbol);
        } catch (BracketStack.StackException ex) {
            throw new ParseException(lineCount);
        }
    }
    
    public int getLineCount(){
        return lineCount;
    }
    
    public LinkedList<BaseModel> getReferences(){
        if(this.references == null)
            this.fillReferences();
        return references;
    }
    
    protected void fillReferences(){
        references = new LinkedList();
    }
    
    public boolean hasModel(){
        return this.baseModel != null;
    }
    
    public ProjectModel getProject(){
        if(this.hasModel())
            return this.baseModel.getProject();
        return null;
    }
    
    public BaseModel getModelFromSource(final String source) throws DoesNotExistException{
        if(!this.hasModel())
            return null;
        return this.getProject().findClass(source);
    }
    
    protected abstract void parseOpenCurlyBracket(final int index);
    protected abstract void parseCloseCurlyBracket(final int index);
    protected abstract void parseOpenParen(final int index);
    protected abstract void parseCloseParen(final int index);
    protected abstract void parseOpenBracket(final int index);
    protected abstract void parseCloseBracket(final int index);
    protected abstract void parsePeriod(final int index);
    protected abstract void parseSemicolon(final int index);
    protected abstract void parseReservedWord(final int index);
    protected abstract void parseStringLiteral(final int index);
    protected abstract void parseComma(final int index);
    protected abstract void parseEmptySpace(final int index);
    protected abstract void parseMultiLineComment(final int index) throws ParseException;
    protected abstract void parseSingleLineComment(final int index) throws ParseException;
    protected abstract BaseModel modelFromSource(final String source);
    public abstract void addReference(String source);
}
