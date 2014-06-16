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
    
    protected int skipUntilAfterSymbol(int startIndex, String symbol){
        while(!this.isCurrentSymbol(startIndex++, symbol)){
            if(startIndex >= this.source().length()){
                errors.add("parse error"); // make more meaningful errors
                return -1;
            }
        }
        return startIndex;
    }
    
    protected boolean isCurrentSymbol(int index, String symbol){
        if(index+symbol.length() > source().length())
            return false;
        return this
                .source()
                .substring(index, index+symbol.length())
                .compareTo(symbol) == 0;
    }
    protected boolean isCurrentSymbol(int index, char symbol){
        if (index > source().length())
            return false;
        return this.source().charAt(index) == symbol;
    }
    
    protected char nextNonWhiteCharFrom(int index){
        while(this.source().charAt(index++) != ' ');
        return this.source().charAt(index);
    }
    
    protected String sourceFrom(int startIndex){
        return new String(source().substring(startIndex));
    }
    
    protected String sourceTo(int endIndex){
        return new String(source().substring(0, endIndex));
    }
    
    protected String sourceFromTo(int start, int end){
        return new String(source().substring(start, end));
    }
    
}
