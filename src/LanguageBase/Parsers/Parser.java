/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

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
    //protected LinkedList<ModelReference> references;
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
                case '\'':
                    this.parseStringLiteral(index);
                    break;
                case '"':
                    this.parseStringLiteral(index);
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
    
    public LinkedList<BaseModel> getReferences(){
        if(references == null)
            return new LinkedList();
        return references;
    }
    
    public void addReference(BaseModel aModel){
        if(aModel == null)
            return;
        references.add(model);
    }
    
    public boolean hasModel(){
        return this.baseModel != null;
    }
    
    public ProjectModel getProject(){
        if(this.hasModel())
            return this.baseModel.getProject();
        return null;
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
    protected abstract void parseStringLiteral(int index);
    protected abstract void parseMultiLineComment(int index) throws ParseException;
    protected abstract void parseSingleLineComment(int index) throws ParseException;
    protected abstract BaseModel modelFromSource(String source);
    
    
    /**
     * 
     */
    public class ModelReference {
        
    }
    
}
