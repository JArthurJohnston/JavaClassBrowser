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
public class ScopeTree extends BaseParseTree{

    protected LinkedList<Block> children;
    protected LinkedList<String> statements;

    private ScopeTree() {
        children = new LinkedList();
        statements = new LinkedList();
    }

    public ScopeTree(String source) {
        this();
        this.source = source;
        this.parseFrom(0);
    }

    protected void parseFrom(int index) {
        int statementPtr = index;
        while (index < this.source().length()) {
            if(this.endOfStatement(index))
                if(this.isSingleStatement())
                    this.closeBlock(index);
                else{
                    this.addStatement(this.getSegment(statementPtr, index));
                    statementPtr = index;
                }
            if (this.beginningOfBlock(index)) {
                if (this.isSingleStatement(index))
                    this.addBlock(statementPtr, true, index+1);
                else 
                    this.addBlock(statementPtr, false, index+1);
                break;
            }
            if (this.endOfBlock(index)) {
                this.closeBlock(index);
            }
            index++;
        }
    }
    
    protected boolean isSingleStatement(){
        return false;
    }
    
    protected void addStatement(String statement){
        if(!statement.isEmpty())
            statements.add(statement);
    }
    
    protected boolean endOfStatement(int index){
        return this.source().charAt(index) == ';';
    }
    
    public String getSource(){
        return source;
    }

    protected void closeBlock(int index) {
        //done
    }

    protected boolean beginningOfBlock(int index) {
        if (source().charAt(index) == '{') {
            return true;
        }
        if (source().charAt(index) == ')') {
            if (source().charAt(this.skipWhiteSpaces(index)) != '{') {
                return true;
            }
        }
        return false;
    }

    protected boolean isSingleStatement(int index) {
        if (source().charAt(index) == ')') {
            if (source().charAt(this.skipWhiteSpaces(index)) != '{') {
                return true;
            }
        }
        return false;
    }

    protected boolean endOfBlock(int index) {
        return source().charAt(index) == '}';
    }

    protected void addBlock(int statementPtr, boolean isSingleStatement, int index) {
        this.children.add(new Block(
                this, 
                this.getSegment(statementPtr, index), 
                isSingleStatement, 
                index+1));
    }

    public class Block extends ScopeTree {

        private int startIndex;
        ScopeTree parent;
        boolean singleStatement;
        private String declaration;

        private Block() {
            super();
        }

        private Block(ScopeTree parent, String declaration, boolean singleStatement, int x) {
            this();
            this.declaration = declaration;
            this.singleStatement = singleStatement;
            this.parent = parent;
            this.startIndex = x;
            this.parseFrom(x);
        }
        
        @Override
        protected boolean isSingleStatement(){
            return this.singleStatement;
        }

        @Override
        protected void closeBlock(int index) {
            this.source = new String(source().substring(startIndex, index));
            parent.parseFrom(index + 1);
        }

        @Override
        protected String source() {
            return parent.source();
        }
    }
}
