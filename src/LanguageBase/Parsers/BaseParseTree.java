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
            if(this.source().charAt(index) != ' ')
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
        void open(char c){
            this.add(c);
        }
        
        void close(char c){
            if(c == ';')
                return;
            if(closesScope(c))
                this.removeLast();
        }
        
        private boolean closesScope(char c){
            if(c == '}')
                return (char)this.getLast() == '{';
            if(c == ')')
                return (char)this.getLast() == '(';
            return false;
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
    
}
