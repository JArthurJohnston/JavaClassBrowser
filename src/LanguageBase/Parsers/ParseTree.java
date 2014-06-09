/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanguageBase.Parsers;

import MainBase.SortedList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class ParseTree {

    private LinkedList<String> errors;
    private String source;
    protected LinkedList<TreeNode> children;
    protected int startIndex;
    protected int endIndex;
    protected int lineCount;
    protected final char newLine = '\n';

    protected ParseTree() {
        children = new LinkedList();
        errors = new LinkedList();
    }

    public ParseTree(String source) {
        this();
        this.source = source;
        this.endIndex = source.length();
        this.parseFrom(0);
    }
    
    protected int skipUntilSymbol(int from, String symbol){
        for(int i = from; i< this.getSource().length(); i++){
            String test = source.substring(i, i+symbol.length());
            if(this.isCurrentSymbol(i, symbol))
                return i;
        }
        return -1;
    }
    
    protected boolean isCurrentSymbol(int index, String symbol){
        return this
                .getSource()
                .substring(index, index+symbol.length())
                .compareTo(symbol) == 0;
    }

    protected void parseFrom(int index) {
        for (int i = index; i < this.getSource().length(); i++) {
            if(this.isCurrentSymbol(i, "if"))
                i = this.parseIfStatement(i);
                //System.out.println("hit if");
            else if (this.getSource().charAt(i) == '{')
                children.add(new TreeNode(this, i));
            else if (this.getSource().charAt(i) == '}')
                this.closeNode(++i);
        }
    }

    public int getLineCount() {
        return lineCount;
    }

    protected void closeNode(int x) {
        this.endIndex = x;
        this.parseFrom(x);
    }

    public int getNodeCount() {
        if (children.isEmpty()) {
            return 1;
        } else {
            int count = 1;
            for (ParseTree n : children) {
                count += n.getNodeCount();
            }
            return count;
        }
    }

    protected LinkedList<ParseTree> getNodes() {
        if (!children.isEmpty()) {
            LinkedList nodes = new LinkedList();
            for (ParseTree n : children) {
                nodes.addAll(n.getNodes());
            }
            return nodes;
        }
        return SortedList.with(this);
    }

    public LinkedList<TreeNode> getChildren() {
        return children;
    }

    public String getBody() {
        return this.getSource().substring(startIndex, endIndex);
    }

    public StringBuilder getSource() {
        StringBuilder sb = new StringBuilder();
        if (source == null) {
            return new StringBuilder("");
        }
        return new StringBuilder(source);
    }

    public StatementList getStatements() {
        if(children.isEmpty())
            return new StatementList();
        return this.getStatementsUsing(this.startIndex, this.children.getFirst().startIndex);
    }

    /*
     this wont work on a for loop... just sayin.
     */
    protected StatementList getStatementsUsing(int start, int end) {
        return new StatementList(this.getSource().substring(start, end));
    }

    public String getDeclaration() {
        if(children.isEmpty())
            return "";
        return children.getFirst().getDeclaration();
    }
    
    protected int skipWhiteSpaces(int index){
        while(this.getSource().charAt(++index) == ' ' 
                && index < this.getSource().length());
        return index;
    }
    
    protected int parseIfStatement(int index){
        int start = this.skipWhiteSpaces(this.skipUntilSymbol(index, ")"));
        int end = -1;
        if(this.getSource().charAt(start) == '{')
            end = this.skipUntilSymbol(start, "}");
        else
            end = this.skipUntilSymbol(start, ";");
        if(end > 0)
            this.getChildren().add(new TreeNode(this, index, end));
        return end;
    }

    protected int ignoreComments(int start){
        if(this.getSource().charAt(start) == '/'){
            if(this.getSource().charAt(start + 1) == '/')
                return this.skipUntilSymbol(start, "\n");
            if(this.getSource().charAt(start + 1) == '*')
                return this.skipUntilSymbol(start, "*/");
        }
        return start;
    }
    
    
    /**
     * Represents a block of code. Everything in the block represents code that
     * shares a scope.
     */
    public class TreeNode extends ParseTree {

        protected ParseTree parent;

        private TreeNode() {
            super();
        }

        private TreeNode(int start) {
            this();
            this.startIndex = start;
            this.parseFrom(start);
        }

        private TreeNode(ParseTree parent, int start) {
            this();
            this.parent = parent;
            this.startIndex = start;
            this.parseFrom(++start);
        }
        
        private TreeNode(ParseTree parent, int start, int end){
            this();
            this.parent = parent;
            this.startIndex = start;
            this.endIndex = end;
        }

        @Override
        public StringBuilder getSource() {
            return parent.getSource();
        }

        @Override
        public StatementList getStatements() {
            return this.getStatementsUsing(parent.startIndex, this.startIndex);
        }

        @Override
        public String getDeclaration() {
            if (this.getSource().toString().isEmpty()) {
                return "";
            }
            return this.getStatements().getLast();
        }

        @Override
        protected void closeNode(int x) {
            this.endIndex = x;
            parent.parseFrom(x);
        }

    }

    public class StatementList extends SortedList {
        
        public StatementList(){
            super();
        }

        public StatementList(String source) {
            super.addElements(Arrays.asList(source.split(";")));
        }

        @Override
        public String getFirst() {
            return this.removeLeadingAndTrailingBrackets((String) super.getFirst());
        }

        @Override
        public String getLast() {
            return this.removeLeadingAndTrailingBrackets((String) super.getLast());
        }

        @Override
        public String get(int x) {
            return this.removeLeadingAndTrailingBrackets((String) super.get(x));
        }

        private String removeLeadingAndTrailingBrackets(String originalSource) {
            String s = originalSource;
            int start = 0;
            int end = s.length();
            if ("{}".contains(Character.toString(originalSource.charAt(0)))) {
                start = 1;
            }
            if ("{}".contains(Character.toString(originalSource.charAt(end - 1)))) {
                end = end - 1;
            }
            return new String(originalSource.substring(start, end));
        }
    }
}
