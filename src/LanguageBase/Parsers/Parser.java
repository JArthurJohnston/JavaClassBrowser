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
            
            //ignore comments
            //these NEED refactoring!!!!
            if(this.isCurrentSymbol(index, "//")){
                while(!this.isCurrentSymbol(++index, '\n'));
                this.addStatement(statementStart, index);
                statementStart = index+1;
            }
            if(this.isCurrentSymbol(index, "/*")){
                while(!this.isCurrentSymbol(++index, "*/"));
                this.addStatement(statementStart, ++index);
                statementStart = index+1;
            }
            if(this.isCurrentSymbol(index, '"')){
                while(!this.indexOutOfRange(++index)){
                    if(this.isCurrentSymbol(index, '"'))
                        if(!this.isCurrentSymbol(index-1, '\\'))
                            break;
                }
            }
            if(this.isCurrentSymbol(index, '\'')){
                while(!this.indexOutOfRange(++index)){
                    if(this.isCurrentSymbol(index, '\''))
                        if(!this.isCurrentSymbol(index-1, '\\'))
                            break;
                }
            }
            //^refactor me!!!!
            
            if(this.isCurrentSymbol(index, "do")){
                this.openDoWhileLoop(index, stack);
                break;
            }
            
            if(this.isCurrentSymbol(index, "while"))
                if(this.isDoLoop()){
                    parent().parseFrom(index, stack);
                    break;
                }
            
            if(this.isCurrentSymbol(index, '('))
                stack.push('(');
            if(this.isCurrentSymbol(index, ')')){
                stack.pop(')');
                if(!stack.isOpenParen()){
                    char nextChar = this.nextNonWhiteCharFrom(index+1);
                    if(nextChar != '.'){
                        if(nextChar == '{' || nextChar == ';'){
                            index++;
                            continue;
                        }else{
                            this.addStatement(statementStart, index)
                                    .parseFrom(index+1, stack);
                            break;
                        }
                    }
                }
            }
            if(this.isCurrentSymbol(index, '{')){
                stack.push('{');
                this.addStatement(statementStart, index)
                            .parseFrom(index+1, stack);
                break;
            }
            if(this.isCurrentSymbol(index, ';')){
                if(!stack.isOpenParen()){
                    this.addStatement(statementStart, index);
                    this.parseFrom(index+1, stack);
                    break;
                }
                if(stack.isEmpty()){
                    this.closeBlock(index, stack);
                    break;
                }
            }
            if(this.isCurrentSymbol(index, '}')){
                stack.pop('}');
                this.getLastChild().setEnd(index);
                this.parseFrom(index+1, stack);
                break;
            }
            index++;
        }
    }
    
    protected Parser setEnd(int index){
        return this;
    }
    
    protected Parser getLastChild(){
        if(this.nodes.isEmpty())
            return this;
        return nodes.getLast().getLastChild();
    }
    
    protected Parser parent(){
        return this;
    }
    
    protected void addNode(ParseNode node){
        this.nodes.add(node);
    }
    
    /*
    doesnt call parseFrom()
    */
    protected ParseNode addStatement(int start, int end){
        ParseNode temp = new ParseNode(this, start, end);
        nodes.add(temp);
        return temp;
    }
    
    protected void openDoWhileLoop(int index, ParseStack stack){
        int start = index+2; //to get past "do"
        if(this.nextNonWhiteCharFrom(start) == '{'){
            start = this.nextIndexOfCharFromIndex('{', index);
            stack.push('{');
        }
        this.addStatement(index, start)
                .setDoLoop(true)
                .parseFrom(start+1, stack);
    }
    
    protected void closeBlock(int index, ParseStack stack){
        if(stack.isOpen())
            stack.pop(source().charAt(index));
        this.parseFrom(++index, stack);
    }
    
    protected boolean isDoLoop(){
        return false;
    }
    
    public class ParseNode extends Parser {
        int start, end;
        private Parser parent;
        private boolean isDoLoop;
        
        private ParseNode(){
            super();
        }
        
        private ParseNode(Parser parent){
            this();
            this.parent = parent;
        }
        
        private ParseNode(Parser parent, int start){
            this(parent);
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
        protected Parser parent(){
            return this.parent;
        }
        
        @Override
        protected ParseNode setEnd(int index){
            this.end = index;
            return this;
        }
        
        @Override
        protected void closeBlock(int index, ParseStack stack){
            this.end = index;
            super.closeBlock(index, stack);
        }
        
        @Override
        protected String source(){
            return parent.source();
        }
        
        @Override
        protected boolean isDoLoop(){
            if(!isDoLoop)
                return parent.isDoLoop();
            return isDoLoop;
        }
        public String getSource(){
            return new String(source().substring(start, end+1));
        }
        
        protected ParseNode setDoLoop(boolean isDo){
            this.isDoLoop = isDo;
            return this;
        }
        protected ParseNode setStart(int start){
            this.start = start;
            return this;
        }
        protected ParseNode startParseUsing(ParseStack stack){
            this.parseFrom(this.start, stack);
            return this;
        }
        
    }
}
