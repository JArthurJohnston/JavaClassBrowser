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
        int statementStart = index;
        while(!this.indexOutOfRange(index)){
            
            //remove this later
            char test = source().charAt(index);
            //^remove this later
            
            if(this.isCurrentSymbol(index, "do")){
                this.parseDoWhileLoop(index);
                break;
            }
            
            if(this.isCurrentSymbol(index, '('))
                stack.open('(');
            if(this.isCurrentSymbol(index, ')')){
                stack.close(')');
                if(!stack.isOpenParen())
                    if(this.nextNonWhiteCharFrom(index+1) != '{' &&
                            this.nextNonWhiteCharFrom(index+1) != ';'){
                        this.addNodeStartingFromParsingFrom(statementStart, ++index, stack);
                        break;
                    }
            }
            if(this.isCurrentSymbol(index, ';')){
                if(!stack.isOpenParen()){
                    this.addStatement(statementStart, index);
                    statementStart = index;
                }
                if(stack.isEmpty()){
                    this.closeBlock(index, stack);
                    break;
                }
            }
            if(this.isCurrentSymbol(index, '{')){
                stack.open('{');
                this.addNodeStartingFromParsingFrom(statementStart, ++index, stack);
                break;
            }
            if(this.isCurrentSymbol(index, '}')){
                this.closeBlock(index, stack);
                break;
            }
            index++;
        }
    }
    
    /*
    doesnt call parseFrom()
    */
    protected void addStatement(int start, int end){
        nodes.add(new ParseNode(this, start, end));
    }
    
    protected void parseDoWhileLoop(int index){
        int start = index;
        index += 2; //do start after the "do"
        int statementStart = index;
        ParseStack stack = new ParseStack();
        LinkedList<String> doStatements = new LinkedList();
        do{
            if(this.isCurrentSymbol(index, ';')){
                doStatements.add(source().substring(statementStart, index));
                statementStart = index +1;
            }
        }while(!this.indexOutOfRange(index++));
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
                if(this.nextNonWhiteCharFrom(index+1) == '{'){
                    index = this.advanceToCharacterFrom('{', index);
                    stack.open('{');
                }
                if(!stack.isOpenParen()){
                    this.addNodeStartingFromParsingFrom(start, ++index, stack);
                    break;
                }
            }
        }while(!indexOutOfRange(++index));
    }
    
    protected void addNodeStartingFromParsingFrom(int start, int parse, ParseStack stack){
        this.nodes.add(new ParseNode(this, start, parse, stack));
    }
    
    public class ParseNode extends Parser {
        int start, end;
        private Parser parent;
        
        private ParseNode(){
            super();
        }
        
        private ParseNode(Parser parent, int start){
            this();
            this.parent = parent;
            this.start = start;
        }
        
        private ParseNode(Parser parent, int start, int end){
            this(parent, start);
            this.end = end;
        }
        
        private ParseNode(Parser parent, int start, ParseStack stack){
            this(parent, start);
            this.parseFrom(++start, stack);
        }
        
        private ParseNode(Parser parent, int start, int parse, ParseStack stack){
            this(parent, start);
            this.parseFrom(parse, stack);
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
