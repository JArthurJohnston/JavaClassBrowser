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
public class SimplifiedParser extends BaseParseTree {

    private String error;
    protected LinkedList<BlockNode> nodes;
    private String source;
    private BracketStack stack;

    protected SimplifiedParser() {
        nodes = new LinkedList();
        stack = new BracketStack();
    }

    public SimplifiedParser(String source) {
        this();
        this.source = source;
        this.beginParse();
    }

    /*
     public SimplifiedParser(model)
     public SimplifiedParser(file)
     */
    @Override
    protected String source() {
        return source;
    }

    protected BracketStack stack() {
        return stack;
    }

    private void beginParse() {
        try {
            this.parseFrom(0);
        } catch (ParseException ex) {
            this.error = ex.getMessage();
            System.out.println(error);
            //^remove after done testing
        }
        this.checkForLineErrors();
    }

    private void checkForLineErrors() {
        if (error == null) {
            int lineError = 1;
            for (BlockNode b : this.getLines()) {
                if (b.end < b.start)
                    error = "Parse Error at line: " + lineError;
                lineError++;
            }
        }
    }

    public String getError() {
        return error;
    }

    protected void parseFrom(int index) throws ParseException {
        int statementStart = index;
        while (!this.indexOutOfRange(index)) {
            if (this.isBeginningOfComment(index))
                statementStart = this.addPossibleComment(index);

            if (this.isCurrentSymbol(index, "("))
                this.openStackWithSymbolAtIndex("(", index);

            if (this.isCurrentSymbol(index, ")")) {
                this.closeStackWithSymbolAtIndex(")", index);
                if (!stack().isOpenParen()) {
                    char nextChar = this.nextNonWhiteCharFrom(index + 1);
                    //check for chained method calls
                    if (nextChar != '.')
                        //check to see if its a single-statement block
                        if (nextChar != '{' && nextChar != ';') {
                            this.openStackWithSymbolAtIndex("{{", index);
                            this.addSingleStatementAndBlock(statementStart, index);
                            break;
                        }
                }
            }

            //repeat below for try blocks
            if (this.isCurrentSymbol(index, "else")) {
                index += 3; //put index at end of 'else'
                if (this.nextNonWhiteCharFrom(index + 1) == '{')
                    index = this.nextIndexOfCharFromIndex('{', index);
                this.expandNodeToAndParseFrom(index);
                break;
            }

            if (this.isCurrentSymbol(index, ';'))
                if (!stack().isOpenParen()) {
                    if (stack().peek("{{")) {
                        this.closeStackWithSymbolAtIndex("}}", index);
                        this.closeBlock(index);
                        break;
                    }
                    this.addStatementToBlock(statementStart, index);
                    statementStart = index + 1;
                }

            if (this.isCurrentSymbol(index, '{')) {
                this.openStackWithSymbolAtIndex("{", index);
                this.addStatementWithNewBlock(statementStart, index + 1);
                break;
            }

            if (this.isCurrentSymbol(index, '}')) {
                this.closeStackWithSymbolAtIndex("}", index);
                this.getParent().addStatementToBlock(index, index);
                this.getParent().parseFrom(index + 1);
                break;
            }
            index++;
        }
    }

    protected SimplifiedParser getParent() {
        return this;
    }

    protected void closeBlock(int end) throws ParseException {
        this.error = "Parse error at end of file";
    }

    @Override
    public LinkedList<BlockNode> getLines() {
        LinkedList lines = new LinkedList();
        for (BlockNode pn : nodes)
            lines.addAll(pn.getLines());
        return lines;
    }

    protected void openStackWithSymbolAtIndex(String c, int index) throws ParseException {
        try {
            stack().open(c);
        } catch (StackException ex) {
            throw new ParseException(this.lineNumberFromIndex(index));
        }
    }

    protected void closeStackWithSymbolAtIndex(String c, int index) throws ParseException {
        try {
            stack().close(c);
        } catch (StackException ex) {
            throw new ParseException(this.lineNumberFromIndex(index));
        }
    }

    protected void addSingleStatementAndBlock(int start, int end) throws ParseException {
        this.addStatementToBlock(start, end);
        this.nodes.getLast().addStatementStarting(end + 1);
    }

    protected void addStatementWithNewBlock(int start, int index) throws ParseException {
        this.nodes.add(new BlockNode(this, start, index));
        /*
         in the else block, this is adding a new
         node when it should be further expanding the current node, or 
         just further expand the node in the else case of this method and
         parse from there.
         */
        this.nodes.getLast().parseFrom(index);
    }

    protected void addStatementToBlock(int start, int index) {
        this.getParent().nodes.add(new BlockNode(this, start, index + 1));
    }

    protected void addElseBlockStartingAtIndex(int start) throws ParseException {
        this.addStatementStarting(start + 3);
    }

    protected void addStatementStarting(int index) throws ParseException {
        this.nodes.add(new BlockNode(this, index));
        this.nodes.getLast().parseFrom(index + 1);
    }

    /*
     Clean this up!!!
     */
    protected void expandNodeToAndParseFrom(int index) throws ParseException {
        BlockNode last = this.getParent().nodes.getLast();
        last.end = index + 1;
        if (!this.isCurrentSymbol(index, '{')){
            this.openStackWithSymbolAtIndex("{{", index);
        }else{
            this.openStackWithSymbolAtIndex("{", index);
        }
        last.addStatementStarting(index+1);
        /*
        youre always gonna be adding an 'else' statement, which could either be...
        
        'else'
        '} else'
        'else {'
        or
        '} else {'
        
        so expanding to the right index, then adding that statement sould
        always be the first step.
        then ...
        
        if(!else block ends with '{')
            stack.open({{)
            addStatementStarting()
        else
            stack.open({)
            addStatementStarting()
        */
        if(false){ // block commented out for testing
        if (!this.isCurrentSymbol(index, '{')) {
            this.openStackWithSymbolAtIndex("{{", index);
            this.nodes.getLast().addStatementStarting(index + 1);
        } else {
            this.openStackWithSymbolAtIndex("{", index);
            this.nodes.getLast().parseFrom(index + 1);
        }
        }
    }

    /**
     * Each node represents one scope in the original source code
     */
    public class BlockNode extends SimplifiedParser {

        private int start, end;
        private final SimplifiedParser parent;

        /**
         * A constructor for adding a node at the start of a multiple statement
         * block.
         *
         * @param parent
         * @param start
         * @throws LanguageBase.Parsers.BaseParseTree.ParseException
         */
        private BlockNode(SimplifiedParser parent, int start) {
            this.parent = parent;
            this.start = start;
        }

        /**
         * A constructor for adding a node that represents a single statement
         * such as an if, or for. Then it adds the following statements.
         *
         * @param parent
         * @param start
         * @param end
         * @throws LanguageBase.Parsers.BaseParseTree.ParseException
         */
        private BlockNode(SimplifiedParser parent, int start, int end) {
            this.parent = parent;
            this.start = start;
            this.end = end;
        }

        @Override
        protected BracketStack stack() {
            return this.parent.stack();
        }

        @Override
        protected String source() {
            return parent.source();
        }

        public String getSource() {
            return new String(
                    this.source()
                    .substring(start, end));
        }

        @Override
        protected SimplifiedParser getParent() {
            return this.parent;
        }

        @Override
        protected void closeBlock(int end) throws ParseException {
            this.end = end + 1;
            this.parent.parseFrom(end + 1);
        }

        @Override
        public LinkedList<BlockNode> getLines() {
            LinkedList lines = new LinkedList();
            lines.add(this);
            for (BlockNode n : nodes)
                lines.addAll(n.getLines());
            return lines;
        }
    }

    /*
     Make a new parse stack that deals exclusively with strings
     */
    protected class BracketStack extends LinkedList<String> {

        private final String[] validSymbols;
        private String lastSymbol;

        private BracketStack() {
            validSymbols = new String[]{"(", ")", "{", "}", "{{", "}}"};
        }

        public boolean isLastSymbol(String symbol) {
            if (lastSymbol == null)
                return false;
            return lastSymbol.compareTo(symbol) == 0;
        }

        private boolean peek(String symbol) {
            if (this.isEmpty())
                return false;
            return this.getLast().compareTo(symbol) == 0;
        }

        private void open(String openSymbol) throws StackException {
            if (this.isValidSymbol(openSymbol)) {
                this.add(openSymbol);
                lastSymbol = openSymbol;
            } else
                throw new StackException(openSymbol);
        }

        private void close(String closeSymbol) throws StackException {
            if (this.isValidSymbol(closeSymbol))
                if (matchSymbol(closeSymbol)) {
                    lastSymbol = closeSymbol;
                    this.removeLast();
                } else
                    throw new StackException(closeSymbol);
        }

        private boolean matchSymbol(String symbol) {
            if (this.isEmpty())
                return false;
            if (symbol.compareTo("}") == 0)
                return this.getLast().compareTo("{") == 0;
            if (symbol.compareTo("}}") == 0)
                return this.getLast().compareTo("{{") == 0;
            if (symbol.compareTo(")") == 0)
                return this.getLast().compareTo("(") == 0;
            return false;
        }

        private boolean isOpen() {
            return this.isOpenBracket() || this.isOpenParen();
        }

        private boolean isOpenParen() {
            if (this.isEmpty())
                return false;
            return this.getLast().compareTo("(") == 0;
        }

        private boolean isOpenBracket() {
            if (this.isEmpty())
                return false;
            return this.getLast().compareTo("{") == 0
                    || this.getLast().compareTo("{{") == 0;
        }

        private boolean isValidSymbol(String symbol) {
            for (String validSymbol : validSymbols)
                if (symbol.compareTo(validSymbol) == 0)
                    return true;
            return false;
        }
    }
}
