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
public class ScopeTree extends BaseParseTree {

    protected LinkedList<Block> children;
    protected boolean openParen = false;

    private ScopeTree() {
        super();
        children = new LinkedList();
    }

    public ScopeTree(String source) {
        this();
        this.source = source;
        this.parseFrom(0);
    }

    protected void parseFrom(int index) {
        int statementPtr = index;
        while (index < this.source().length()) {
            char test = this.source().charAt(index);
            if (this.isCurrentSymbol(index, '('))
                openParen = true;
            if (this.endOfStatement(index))
                if (this.isSingleStatement())
                    this.closeBlock(index);
                else {
                    this.addStatement(statementPtr, index + 1);
                    statementPtr = index + 1;
                }
            if (this.beginningOfBlock(index)) {
                if (this.isSingleStatement(index))
                    this.addBlock(statementPtr, true, index);
                else
                    this.addBlock(statementPtr, false, index);
                break;
            }
            if (this.endOfBlock(index))
                this.closeBlock(index);
            index++;
        }
    }

    public LinkedList<Block> getChildren() {
        return children;
    }

    protected boolean isSingleStatement() {
        return false;
    }

    protected void addStatement(int start, int end) {
        if (end - start > 0)
            children.add(new Block(this, start, end));
    }

    protected boolean endOfStatement(int index) {
        return this.source().charAt(index) == ';';
    }

    public String getSource() {
        return source;
    }

    protected void closeBlock(int index) {
        //done
    }

    protected boolean beginningOfBlock(int index) {
        if (source().charAt(index) == '{')
            return true;
        if (source().charAt(index) == ')')
            if (openParen)
                return false;
            else if (source().charAt(this.skipWhiteSpaces(index)) != '{')
                return true;
        return false;
    }

    protected boolean isSingleStatement(int index) {
        if (source().charAt(index) == ')')
            if (source().charAt(this.skipWhiteSpaces(index)) != '{')
                return true;
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
                index + 1));
    }

    public class Block extends ScopeTree {

        private int startIndex;
        ScopeTree parent;
        boolean singleStatement;
        private String declaration;

        private Block() {
            super();
        }

        private Block(ScopeTree parent, int startIndex, int endINdex) {
            this();
            this.parent = parent;
            this.source = this.getSegment(startIndex, endINdex);
        }

        private Block(ScopeTree parent, String declaration, boolean singleStatement, int x) {
            this();
            this.declaration = declaration;
            this.singleStatement = singleStatement;
            this.parent = parent;
            this.startIndex = x;
            this.parseFrom(x);
        }

        public String getDeclaration() {
            return declaration;
        }

        @Override
        protected boolean isSingleStatement() {
            return this.singleStatement;
        }

        @Override
        protected void closeBlock(int index) {
            this.source = this.getSegment(startIndex, index);
            parent.parseFrom(index + 1);
        }

        @Override
        protected String source() {
            return parent.source();
        }
    }
}
