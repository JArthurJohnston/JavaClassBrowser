/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;

import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class Parser {
    CodeSnippet rootCode;
    char[] buffer;
    String source;
    LinkedList<CodeSnippet> parseStack;
    int position;
    
    public Parser(){
        position = 0;
        parseStack = new LinkedList();
    }
    
    public Parser(String source){
        this();
        this.setSource(source);
    }
    
    void setSource(String source){
        this.source = source;
        this.buffer = new char[source.length()/2];
    }
    
    void setRootCode(String source){
        rootCode = new CodeSnippet(source);
    }
    
    void parse(){
        int i = 0;
        while(true){
            source.charAt(i)
            
            
            i++;
        }
    }
    
    void openSnippet(char c, int i){
        if(c == '{' || c == '(')
            parseStack.add(new CodeSnippet(new String(source.substring(position, i))));
    }
    
    private class CodeSnippet {
        String source;
        LinkedList<CodeSnippet> subSnippets;
        
        public CodeSnippet(String source){
            this.source = source;
            subSnippets = new LinkedList();
        }
        
        void addSnippet(CodeSnippet newSnippet){
            this.subSnippets.add(newSnippet);
        }
    }
    
}
