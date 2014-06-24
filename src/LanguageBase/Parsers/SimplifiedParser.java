/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
public class SimplifiedParser extends BaseParseTree {
    protected LinkedList<BlockNode> nodes;
    private String source;
    private ParseStack stack;
    
    protected SimplifiedParser(){
        nodes = new LinkedList();
    }
    
    public SimplifiedParser(String source){
        this();
        try {
            this.source = source;
            this.parseFrom(0);
        } catch (ParseException ex) {
            errors.add("some error");
        }
    }
    
    @Override
    protected String source(){
        return source;
    }
    
    protected void parseFrom(int index) throws ParseException{
        int statementStart = index;
        while(!this.indexOutOfRange(index)){
            if(this.isBeginningOfComment(index))
                statementStart = this.addPossibleComment(index);
            if(this.isCurrentSymbol(index, ';')){
                if(stack.peek("{{")){
                    /*
                    close block and push "}}"
                    */
                }
                this.addStatement(statementStart, index);
                statementStart = index+1;
            }
            if(this.isCurrentSymbol(index, "("))
                this.pushToStack("(", index, stack);
            if(this.isCurrentSymbol(index, ")")){
                this.popFromStack(")", index, stack);
                if(!stack.isOpenParen()){
                    char nextChar = this.nextNonWhiteCharFrom(index+1);
                    if(nextChar != '.'){
                        if(nextChar != '{' || nextChar != ';'){
                            /*
                            stack .push "{{"
                            openBlock
                            */
                        }
                    }
                }  
            }
            if(this.isCurrentSymbol(index, '{')){
                /*
                statementStar = index+1;
                openBlock
                */
            }
            if(this.isCurrentSymbol(index, '}')){
                /*
                statementStar = index+1;
                close block
                maybe make a node for the '}' to ensure it prints on a
                newline?
                or just add a \n to the end of that nodes source string
                */
            }
        }
    }
    
    
    protected void pushToStack(String c, int index, ParseStack stack) throws ParseException{
        try {
            stack.push(c);
        } catch (StackException ex) {
            throw new ParseException(this.lineNumberFromIndex(index));
        }
    }
    
    protected void popFromStack(String c, int index, ParseStack stack) throws ParseException{
        try {
            stack.pop(c);
        } catch (StackException ex) {
            throw new ParseException(this.lineNumberFromIndex(index));
        }
    }
    
    public class BlockNode extends SimplifiedParser{
        private int start, end;
        private SimplifiedParser parent;
    }
    /*
    Make a new parse stack that deals exclusively with strings
    */
}
