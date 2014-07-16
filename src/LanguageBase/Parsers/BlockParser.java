/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanguageBase.Parsers;

import Exceptions.DoesNotExistException;
import LanguageBase.Parsers.Nodes.BlockNode;
import LanguageBase.Parsers.Nodes.StatementNode;
import LanguageBase.Parsers.Stacks.BracketStack;
import Models.BaseModel;
import Models.ProjectModel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
public class BlockParser extends BaseParseTree {
    private BaseModel model;
    private BracketStack stack; 
    private int statementStart, lineCount;
    private BlockNode root;
    private BlockNode currentBlock;
    private StatementNode currentStatement;
    private HashMap<String, BaseModel> references;

    private BlockParser() {
        root = new BlockNode(this);
        currentBlock = root;
        stack = new BracketStack();
        references = new HashMap();
    }

    public BlockParser(String source) {
        this();
        this.source = source;
        this.startParse();
    }
    
    public BlockParser(BaseModel aModel){
        this();
        this.model = aModel;
        this.source = aModel.toSourceString();
        this.startParse();
    }
    
    private void startParse(){
        try {
            this.parse();
        } catch (ParseException ex) {
            error = ex.getMessage();
        }
        if(!stack.isEmpty())
            error = "Uncloased '"+ stack.getLast()+"' on the stack.";
    }

    private void parse() throws ParseException {
        statementStart = 0;
        lineCount = 1;
        int index = 0;
        while (!this.indexOutOfRange(index)) {
            switch (this.source.charAt(index)) {
                case '{':
                    stackIt("{", index);
                    this.addBlockToStatementAt(index);
                    break;
                case '}':
                    stackIt("}", index);
                    this.closeBlock(index);
                    break;
                case '(':
                    stackIt("(", index);
                    break;
                case ')':
                    stackIt(")", index);
                    if (!stack.isOpenParen())
                        if (!this.isCurrentSymbol(index + 1, '.'))
                            if (this.isSingleStatementBlock(index + 1))
                                this.addSingleStatementAndBlockAtIndex(index);
                            else if (this.nextNonWhiteCharFrom(index + 1) == '{')
                                this.addStatementToBlock(index);
                    break;
                case ';':
                    if (!stack.isOpenParen())
                        this.addStatementToBlock(index);
                    break;
                case 'e':
                    if (this.isCurrentSymbol(index, "else"))
                        this.addSpecialSyntaxBlock(index, "else");
                    //else 
                    break;
                case 'd':
                    if (this.isCurrentSymbol(index, "do"))
                        this.addSpecialSyntaxBlock(index, "do");
                    break;
                case 't':
                    if (this.isCurrentSymbol(index, "try"))
                        this.addSpecialSyntaxBlock(index, "try");
                    break;
                case '"':
                    //skip over string literals
                    break;
                case '\'':
                    //skip over string literals
                    break;
                case '\n':
                    lineCount++;
                    break;
                case '/':
                    //add statement for comments
                    if (source.charAt(index + 1) == '/') {
                    } else if (source.charAt(index + 1) == '*') {
                    }
                    break;
                default:
            }
            index++;
        }
    }
    
    private ProjectModel project(){
        if(model == null)
            return null;
        return model.getProject();
    }

    /**
     * Used for do and else statements.
     */
    private void addSpecialSyntaxBlock(int index, String symbol) throws ParseException {
        statementStart = index;
        if (this.isSingleStatementBlock(index + symbol.length()))
            this.addSingleStatementAndBlockAtIndex(index + symbol.length()-1);
        else
            this.addStatementToBlock(index + symbol.length()-1);

    }

    private void addStatementToBlock(int index) throws ParseException {
        if(this.nextNonWhiteCharFrom(statementStart) == '{')
            statementStart = this.nextIndexOfCharFromIndex('{', statementStart)+1;
        currentStatement = currentBlock
                .addStatement(statementStart, index + 1);
        if (stack.isSingleStatementBlock())
            this.closeBlock(index);
        statementStart = index + 1;
    }

    private void closeBlock(int index) throws ParseException {
        if(currentBlock.isSingleStatement())
            stackIt("}}", index);
        
        if(this.isCurrentSymbol(index, '}'))
            statementStart++;
        currentBlock = currentBlock.close(index);
    }

    private boolean isSingleStatementBlock(int index) {
        char next = this.nextNonWhiteCharFrom(index);
        return !(next == ';' || next == '{');
    }

    private void addSingleStatementAndBlockAtIndex(int index) throws ParseException {
        stackIt("{{", index);
        currentStatement = currentBlock.addStatement(statementStart, index + 1);
        this.addBlockToStatementAt(index);
        statementStart = index + 1;
    }

    private void addBlockToStatementAt(int index) {
        if(currentStatement == null){
            currentStatement = currentBlock.addStatement(statementStart, index);
            statementStart = index+1;
        }
        currentBlock = currentStatement.addBlock(index + 1);
        if(stack.peek("{{"))
            currentBlock.isSingleStatement(true);
    }

    private void stackIt(String symbol, int index) throws ParseException {
        try {
            stack.processSymbol(symbol);
        } catch (BracketStack.StackException ex) {
            throw new ParseException(lineCount);
        }
    }

    public BlockNode getTree() {
        return root;
    }
    
    @Override
    public String formattedSource(){
        if(error != null)
            return source();
        root.isSingleStatement(true); //a hack, to stop root from printing {}
        return root.getFormattedSource();
    }
    
    public void findReferences(String source){
        for(String token : source.split("[{(., )}]"))
            if(!ProjectModel.getReservedWords().containsKey(token)){
                try {
                    if(this.project().findClass(token)!= null)
                        references.put(token, this.project().findClass(token));
                } catch (DoesNotExistException ex) {
                    //add not found tokens to list
                }
            }
    }
    
    public HashMap<String, BaseModel> getReferences(){
        return references;
    }

}
