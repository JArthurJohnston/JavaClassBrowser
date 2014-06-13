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
    
    protected String source(){
        return source;
    }
    
    protected String getSegment(int startIndex, int endIndex){
        return new String(this.source().substring(startIndex, endIndex));
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
        return this
                .source()
                .substring(index, index+symbol.length())
                .compareTo(symbol) == 0;
    }
    protected boolean isCurrentSymbol(int index, char symbol){
        return this.source().charAt(index) == symbol;
    }
    
}
