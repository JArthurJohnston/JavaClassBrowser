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
public class Parser extends BaseParseTree{
    LinkedList<ParseNode> nodes;
    
    private Parser(){
        nodes = new LinkedList();
    }
    
    public Parser(String source){
        this();
        this.source = source;
        this.parseFrom(0, new ParseStack());
    }
    
    public LinkedList<ParseNode> getNodes(){
        return nodes;
    }
    
    protected void parseFrom(int index, ParseStack stack){
        while(!this.indexOutOfRange(index)){
            if(isIfOrLoopStatement(index)){
                parseStatement(stack, index);
                break;
            }
            if(this.isEndOfBlock(index, stack)){
                this.closeBlock(index, stack);
                break;
            }
            if(this.isOpenChar(index))
                this.pushOpenToStack(index, stack);
            if(this.isCloseChar(index))
                this.pushCloseToStack(index, stack);
            index++;
        }
    }
    
    protected boolean isOpenChar(int index){
        return source().charAt(index) == '{' ||
                source().charAt(index) == '(';
    }
    protected boolean isCloseChar(int index){
        return source().charAt(index) == '}' ||
                source().charAt(index) == ')';
    }
    
    protected void pushOpenToStack(int index, ParseStack stack){
        stack.open(source().charAt(index));
    }
    protected void pushCloseToStack(int index, ParseStack stack){
        stack.close(source().charAt(index));
    }
    
    protected void ifOpenerPushToStack(int index, ParseStack stack){
        if(this.isCurrentSymbol(index, '{') || this.isCurrentSymbol(index, '('))
            stack.open(source().charAt(index));
    }
    
    protected boolean isEndOfBlock(int index, ParseStack stack){
        if(stack.isEmpty())
            return this.isCurrentSymbol(index, ';');
        return this.isCurrentSymbol(index, '}');
    }
    
    protected boolean isIfOrLoopStatement(int index){
        return this.isCurrentSymbol(index, "if") ||
                this.isCurrentSymbol(index, "for") ||
                this.isCurrentSymbol(index, "while");
    }
    
    protected void closeBlock(int index, ParseStack stack){
        if(stack.isOpen())
            stack.close(source().charAt(index));
        this.parseFrom(++index, stack);
    }
    
    protected void parseStatement(ParseStack stack, int index){
        int start = index;
        do{
            if(isCurrentSymbol(index, '('))
                stack.open('(');
            if(this.isCurrentSymbol(index, ')')){
                stack.close(')');
                //needs to skip past the '{' if its the next nonWhite symbol
                    //and push it to the stack
                if(stack.isEmpty()){
                    this.nodes.add(
                            new ParseNode(this, start, new ParseStack()));
                    break;
                }
            }
        }while(!indexOutOfRange(++index));
    }
    
    public class ParseNode extends Parser {
        int start, end;
        private Parser parent;
        
        private ParseNode(){
            super();
        }
        
        private ParseNode(Parser parent, int start, ParseStack stack){
            this();
            this.parent = parent;
            this.start = start;
            this.parseFrom(++start, stack);
        }
        
        @Override
        protected void closeBlock(int index, ParseStack stack){
            this.end = index;
            if(source().charAt(index) == '}')
                stack.close('}');
            parent.closeBlock(index, stack);
        }
        
        @Override
        protected String source(){
            return parent.source();
        }
        
    }
}
