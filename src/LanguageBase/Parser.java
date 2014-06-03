/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;

import Exceptions.VeryVeryBadException;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class Parser {
    CodeSnippet current;
    String source;
    LinkedList<CodeSnippet> segments;
    int position;
    
    public Parser(){
        position = 0;
        segments = new LinkedList();
    }
    
    public Parser(String source){
        this();
        this.setSource(source);
    }
    
    void setSource(String source){
        this.source = source;
    }
    
    void parse(){
        for(int i=0; i < source.length(); i++){
            openSnippet(source.charAt(i), i);
            closeSnippet(source.charAt(i), i);
        }
    }
    
    void setCurrent(CodeSnippet newSnippet){
        if(current == null){
            current = newSnippet;
            segments.add(current);
            return;
        }
        current.addSnippet(newSnippet);
        current = newSnippet;
    }
    
    void openSnippet(char c, int i){
        if(c == '(' || c == '{'){
            setCurrent(new CodeSnippet(new String(source.substring(position, i)), c));
            segments.add(current);
            position = i;
        }
    }
    
    void closeSnippet(char c, int i){
        if(c == ')' || c == '}'){
            try {
                current.closeSnippet(c);
            } catch (VeryVeryBadException ex) {
                System.out.printf("Failed to parse. Bracket mismatch");
            }
            current.setBody(new String(source.substring(position, i)));
            current = current.getParent();
            position = i;
        }
    }
    
    public LinkedList<CodeSnippet> getSegments(){
        return segments;
    }
    
    public class CodeSnippet {
        CodeSnippet parent;
        String head;
        String body;
        LinkedList<CodeSnippet> subSnippets;
        char symbol;
        
        public CodeSnippet(String source, char symbol){
            this.head = source;
            subSnippets = new LinkedList();
            this.symbol = symbol;
        }
        
        void addSnippet(CodeSnippet newSnippet){
            newSnippet.setParent(this);
            this.subSnippets.add(newSnippet);
        }
        
        boolean isOpen(){
            return symbol == '(' || symbol == '{';
        }
        
        void setParent(CodeSnippet parent){
            this.parent = parent;
        }
        
        CodeSnippet getParent(){
            return parent;
        }
        
        CodeSnippet getRoot(){
            if(parent == null)
                return this;
            return parent.getRoot();
        }
        
        void closeSnippet(char c) throws VeryVeryBadException{
            if(c == ')')
                if(symbol != '(')
                    throw new VeryVeryBadException(this.symbol, c);
            if(c == '}')
                if(symbol != '}')
                    throw new VeryVeryBadException(this.symbol, c);
            symbol = c;
        }
        
        void setBody(String source){
            this.body = source;
        }
        
        String getBody(){
            return body;
        }
    }
    
}
