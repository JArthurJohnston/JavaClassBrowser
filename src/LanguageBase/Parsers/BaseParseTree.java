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
    protected String error;
    
    protected BaseParseTree(){
        //stupid stupid stupid
    }
    protected BaseParseTree(String source){
        this.source = source;
    }
    
    public String source(){
        return source;
    }
    
    public String getError(){
        return error;
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
        if(!this.isAlphaNumeric(index-1) || 
                !this.isAlphaNumeric(index+symbol.length()+1))
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
    
    protected int advanceToNextNonWhiteCharFrom(int index){
        while(!this.indexOutOfRange(index))
            if(!this.isWhiteChar(source.charAt(++index)))
                return index;
        return -1;
    }
    
    protected boolean isNextSymbolFromIndex(String symbol, int index){
        return this.isCurrentSymbol(
                this.advanceToNextNonWhiteCharFrom(index), symbol);
    }
    
    protected boolean isWhiteChar(char c){
        return Character.isWhitespace(c);
    }
    
    protected boolean isWhiteChar(int index){
        if(this.indexOutOfRange(index))
            return false;
        return this.isWhiteChar(source.charAt(index));
    }
    
    protected boolean isAlphaNumeric(char c){
        return Character.isAlphabetic(c) || Character.isDigit(c);
    }
    
    protected boolean isAlphaNumeric(int index){
        if(this.indexOutOfRange(index))
            return false;
        return this.isAlphaNumeric(source.charAt(index));
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
    
    protected boolean isBeginningOfComment(int index){
        return this.isCurrentSymbol(index, "//") ||
                this.isCurrentSymbol(index, "/*");
    }
    
    protected boolean isBeginningOfStringLiteral(int index){
        return this.isCurrentSymbol(index, '"') ||
                this.isCurrentSymbol(index, '\'');
    }
    
    protected void skipPossibleStringLiteral(int index){
            if(this.isBeginningOfStringLiteral(index)){
                char stringStart = source().charAt(index);
                while(!this.indexOutOfRange(++index)){
                    if(this.isCurrentSymbol(index, stringStart))
                        if(!this.isCurrentSymbol(index-1, '\\'))
                            break;
                }
            }
    }
    
    protected int addPossibleComment(int index){
            if(this.isBeginningOfComment(index)){
                int commentStart = index;
                if(this.isCurrentSymbol(index, "//"))
                    while(!this.isCurrentSymbol(++index, '\n'));
                else if(this.isCurrentSymbol(index, "/*"))
                    while(!this.isCurrentSymbol(++index, "*/"));
                if(this.isCurrentSymbol(index, "*/"))
                    index++;
                //this.addStatement(commentStart, index);
            }
            return index;
    }
    
    protected LinkedList getLines(){
        return new LinkedList();
    }
    
    protected int lineNumberFromIndex(int index){
        return -1;
    }
    
    public String formattedSource(){
        return source;
    }
    
    /***************************/
    
    public class ParseException extends BaseException {
        public ParseException(int lineNum){
            super("Parse error at line: "+ lineNum);
        }
    }
    
    
}
