/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Exceptions.BaseException;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class BaseParseTree {
    protected String source;
    protected LinkedList<String> errors;
    
    protected BaseParseTree(){
        errors = new LinkedList();
    }
    
    protected BaseParseTree(String source){
        this();
        this.source = source;
    }
    
    protected String source(){
        return source;
    }

    protected int skipWhiteSpaces(int index) {
        while (this.source().charAt(++index) == ' '
                && index < this.source().length());
        return index;
    }
    
    protected int skipUntilAfterSymbol(int index, String symbol){
        while(index < 0 || index + symbol.length() < source().length()){
            if(this.isCurrentSymbol(index, symbol))
                return symbol.length() + index;
            index++;
        }
        return -1;
    }
    
    protected int nextIndexOfCharFromIndex(char c, int index){
        if(this.indexOutOfRange(index))
            return -1;
        do{
            if(source().charAt(index) == c)
                return index;
        }while(++index < source().length());
        return -1;
    }
    
    protected boolean isCurrentSymbol(int index, String symbol){
        if(index < 0 || index+symbol.length() > source().length())
            return false;
        return this.sourceFromTo(
                index, index+symbol.length()).compareTo(symbol) == 0;
    }
    protected boolean isCurrentSymbol(int index, char symbol){
        if (this.indexOutOfRange(index))
            return false;
        return this.source().charAt(index) == symbol;
    }
    
    protected char nextNonWhiteCharFrom(int index){
        if(this.indexOutOfRange(index))
            return '\0';
        while(index < source().length()){
            if(!this.isCurrentSymbol(index, ' ') &&
                    !this.isCurrentSymbol(index, '\n') &&
                    !this.isCurrentSymbol(index, '\t'))
                return source().charAt(index);
            index++;
            }
        return '\0';
    }
    
    protected int advanceToCharacterFrom(char c, int index){
        do{
            if(source().charAt(index) == c)
                return index;
        }while(!this.indexOutOfRange(++index));
        return -1;
    }
    
    protected boolean indexOutOfRange(int index){
        return index < 0 || index >= source().length();
    }
    
    protected String sourceFrom(int startIndex){
        if(this.indexOutOfRange(startIndex))
            return null;
        return new String(source().substring(startIndex));
    }
    
    protected String sourceTo(int endIndex){
        if(this.indexOutOfRange(endIndex))
            return null;
        return new String(source().substring(0, endIndex));
    }
    
    protected String sourceFromTo(int start, int end){
        if(start < 0 || end >= source().length()+1)
            return null;
        return new String(source().substring(start, end));
    }
    
    /***************************/
    
    protected class ParseStack extends LinkedList{
        /*
        Need to add errors
        */
        void push(char c) throws StackException{
            if(this.isOpenChar(c))
                this.add(c);
            else
                throw new StackException(c);
        }
        
        void pop(char c) throws StackException{
            if(closesScope(c))
                this.removeLast();
            else
                throw new StackException(c);
        }
        
        
        private boolean closesScope(char c){
            if(this.isEmpty())
                return false;
            if(c == '}')
                return (char)this.getLast() == '{';
            if(c == ')')
                return (char)this.getLast() == '(';
            return false;
        }
        
        private boolean isOpenChar(char c){
            return c =='{' || c == '(';
        }
        
        public boolean isOpen(){
            return this.isOpenBraces() || this.isOpenParen();
        }
        
        private boolean isOpenSymbol(char symbol){
            if(!this.isEmpty())
                return (char)this.getLast() == symbol;
            return false;
        }
        
        public boolean isOpenBraces(){
            return this.isOpenSymbol('{');
        }
        
        public boolean isOpenParen(){
            return this.isOpenSymbol('(');
        }
    }
    
    public class StackException extends BaseException {
        public StackException(char c){
            super("Stack error with char: " + c);
        }
    }
    
    public class ParseException extends BaseException {
        public ParseException(int lineNum){
            super("Parse error at line: "+ lineNum);
        }
    }
    
}
