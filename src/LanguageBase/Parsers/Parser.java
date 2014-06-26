/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Models.BaseModel;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class Parser extends BaseParseTree{
    private String source;
    private BaseModel model;
    private boolean parsed;
    protected LinkedList<ParseNode> nodes;
    
    private Parser(){
        nodes = new LinkedList();
        parsed = false;
        errors = new LinkedList();
    }
    
    public Parser(String source) throws ParseException{
        this();
        this.setAndParseSource(source);
    }
    
    public Parser(BaseModel aModel) throws ParseException{
        this();
        this.model = aModel;
        this.setAndParseSource(aModel.toSourceString());
    }
    
    private void setAndParseSource(String source) throws ParseException{
        this.source = source;
        ParseStack stack = new ParseStack();
        this.parseFrom(0, stack);
        if(!stack.isEmpty())
            this.addError("reached end with unclosed "+ stack.getLast());
        this.parsed = true;
    }
    
    public boolean parseCompleted(){
        return parsed;
    }
    
    public LinkedList<ParseNode> getNodes(){
        return nodes;
    }
    
    @Override
    public LinkedList<ParseNode> getLines(){
        LinkedList lines = new LinkedList();
        for(ParseNode pn : nodes){
            lines.addAll(pn.getLines());
        }
        return lines;
    }
    
    @Override
    protected int lineNumberFromIndex(int index){
        if(!this.indexOutOfRange(index))
            for(int i = 0; i < this.getLines().size(); i++)
                if(index >= this.getLines().get(i).start &&
                    index <= this.getLines().get(i).end )
                        return i+1;
        return -1;
    }
    
    public LinkedList<String> getErrors(){
        return errors;
    }
    
    protected void parseFrom(int index, ParseStack stack) throws ParseException{
        int statementStart = index;
        while(!this.indexOutOfRange(index)){
            
            //remove this later
            char test = source().charAt(index);
            //^remove this later
            
            if(this.isBeginningOfComment(index)){
                int commentStart = index;
                if(this.isCurrentSymbol(index, "//"))
                    while(!this.isCurrentSymbol(++index, '\n'));
                else if(this.isCurrentSymbol(index, "/*"))
                    while(!this.isCurrentSymbol(++index, "*/"));
                if(this.isCurrentSymbol(index, "*/"))
                    index++;
                this.addStatement(commentStart, index);
                statementStart = index+1;
            }
            
            this.skipPossibleStringLiteral(index);
            
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
                this.pushToStack('(', index, stack);
            if(this.isCurrentSymbol(index, ')')){
                this.popFromStack(')', index, stack);
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
                this.pushToStack('{', index, stack);
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
                this.popFromStack('}', index, stack);
                this.getLastChild().setEnd(index);
                this.parseFrom(index+1, stack);
                break;
            }
            index++;
        }
    }
    
    protected Parser getRoot(){
        return this;
    }
    
    protected void addError(String errorMsg){
        this.errors.add(errorMsg);
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
    
    
    @Override
    protected ParseNode addStatement(int start, int end){
        ParseNode temp = new ParseNode(this, start, end);
        nodes.add(temp);
        return temp;
    }
    
    protected void openDoWhileLoop(int index, ParseStack stack) throws ParseException{
        int start = index+2; //to get past "do"
        if(this.nextNonWhiteCharFrom(start) == '{'){
            start = this.nextIndexOfCharFromIndex('{', index);
            this.pushToStack('{', index, stack);
        }
        this.addStatement(index, start)
                .setDoLoop(true)
                .parseFrom(start+1, stack);
    }
    
    protected void closeBlock(int index, ParseStack stack) throws ParseException{
        if(stack.isOpen())
            this.popFromStack(source().charAt(index), index, stack);
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
        
        private ParseNode(Parser parent, int start, ParseStack stack) throws ParseException{
            this(parent, start);
            this.parseFrom(++start, stack);
        }
        
        private ParseNode(Parser parent, int start, int parse, ParseStack stack) throws ParseException{
            this(parent, start);
            this.parseFrom(parse, stack);
        }
        
        @Override
        protected Parser getRoot(){
            return parent.getRoot();
        }
        
        @Override
        protected void addError(String errorMsg){
            this.getRoot().addError(errorMsg);
        }
        
        @Override
        public LinkedList<ParseNode> getLines(){
            LinkedList lines = new LinkedList();
            lines.add(this);
            for(ParseNode n : nodes)
                lines.addAll(n.getLines());
            return lines;
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
        protected void closeBlock(int index, ParseStack stack) throws ParseException{
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
        protected ParseNode startParseUsing(ParseStack stack) throws ParseException{
            this.parseFrom(this.start, stack);
            return this;
        }
        
    }
}
