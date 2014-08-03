/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Exceptions.DoesNotExistException;
import LanguageBase.Parsers.Nodes.BlockNode;
import LanguageBase.Parsers.Nodes.StatementNode;
import Models.BaseModel;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class BlockParser extends Parser{
    private LinkedList<BlockNode> blocks;
    private BlockNode root;
    private BlockNode currentBlock;
    private StatementNode currentStatement;
    
    protected BlockParser(){
        super();
        blocks = new LinkedList();
        blocks.add(root =  new BlockNode(this));
        currentBlock = root;
        currentStatement = currentBlock.addStatement(0);
    }
    
    public BlockParser(String source) throws ParseException{
        this();
        this.source = source;
        this.parseFrom(0);
    }
    
    public BlockParser(BaseModel aModel) throws ParseException{
        this();
        this.baseModel = aModel;
        this.source = aModel.toSourceString();
        this.parseFrom(0);
    }
    
    public LinkedList<BlockNode> getBlocks(){
        return blocks;
    }
    
    public BlockNode getRootBlock(){
        return root;
    }

    @Override
    protected void parseOpenCurlyBracket(int index) {
        this.parseForReference(index);
        currentStatement.close(index);
        this.currentBlock = this.currentStatement.getChildBlock();
        currentStatement = currentBlock.addStatement(index+1);
    }

    @Override
    protected void parseCloseCurlyBracket(int index) {
        this.parseForReference(index);
        this.currentBlock = this.currentBlock.getParentBlock();
        currentStatement = this.currentBlock.addStatement(index+1);
        while(currentBlock.isSingleStatement()){
            currentBlock = currentBlock.getParentBlock();
            currentStatement = this.currentBlock.addStatement(index);
        }
    }

    @Override
    protected void parseOpenParen(int index) {
        this.parseForReference(index);
    }

    @Override
    protected void parseCloseParen(int index) {
        this.parseForReference(index);
        for(char c : new char[]{';', '{', '.'})
            if(this.nextNonWhiteCharFrom(index+1) == c)
                return;
        if(stack.isOpenParen())
            return;
        if(currentStatement.isOpen()){
            currentStatement.close(index+1);
            currentBlock = currentStatement.getChildBlock();
            currentBlock.singleStatement(true);
            currentStatement = currentBlock.addStatement(index+1);
        }
    }

    @Override
    protected void parseOpenBracket(int index) {
    }

    @Override
    protected void parseCloseBracket(int index) {
    }

    @Override
    protected void parsePeriod(final int index) {
        this.parseForReference(index);
    }

    @Override
    protected void parseSemicolon(final int index) {
        this.parseForReference(index);
        if(stack.isOpenParen())
            return;
        currentStatement.close(index+1);
        currentStatement = currentBlock.addStatement(index+1);
        while(currentBlock.isSingleStatement()){
            currentBlock = currentBlock.getParentBlock();
            currentStatement = this.currentBlock.addStatement(index+1);
        }
    }
    
    @Override
    protected void parseReservedWord(int index){
        switch(source.charAt(index)){
            case 'e':
                if(this.isCurrentSymbol(index, "else"))
                    this.parseElseStatement(index);
                break;
            case 'd':
                if(this.isCurrentSymbol(index, "do"))
                    this.parseDoStatement(index);
                break;
        }
    }
    
    @Override
    protected void parseStringLiteral(final int index){
    }
    
    @Override
    protected void parseMultiLineComment(final int index) throws ParseException{
        int end = index;
        while(source.charAt(end)!= '*' && source.charAt(++end) != '/')
            if(source.charAt(end) == '\n')
                lineCount++;
        currentStatement.close(end);
        currentStatement = currentBlock.addStatement(end+1);
        this.parseFrom(end + 1);
    }
    
    @Override
    protected void parseEmptySpace(final int index){
        this.parseForReference(index);
    }
    
    @Override
    protected void parseSingleLineComment(final int index) throws ParseException{
        int end = index;
        if(currentStatement.isOpen())
            while(source.charAt(++end) != '\n');
        currentStatement.close(end);
        currentStatement = currentBlock.addStatement(end+1);
        lineCount++;
        //parseFrom(end)? and be sure to break from the main loop?
        //or, this method(and others like it could return an int
        //which would then be set to the index var
        this.parseFrom(end + 1);
    }
    
    @Override
    protected BaseModel modelFromSource(String source){
        return this.baseModel;
    }
    
    @Override
    protected void parseComma(final int index){
        this.parseForReference(index);
    }
    
    @Override
    protected void fillReferences(){
        this.references = new LinkedList();
        if(!this.hasModel())
            return;
        for(String s : this.root.getReferences()){
            try {
                references.add(this.getProject().findClass(s));
            } catch (DoesNotExistException ex) {
                //maybe add not-found objects here(?)
            }
        }
    }
    
    private void parseElseStatement(int index){
        if(this.nextNonWhiteCharFrom(index + 4) != 'i') //if the next thing isnt an 'if'
            this.basicParseReservedSymbol(index, "else");
    }
    
    private void parseDoStatement(final int index){
        this.basicParseReservedSymbol(index, "do");
    }
    
    private void basicParseReservedSymbol(final int index, String symbol){
        int symbolLen = symbol.length();
        if(this.nextNonWhiteCharFrom(index+symbolLen) == '{')
            return;
        currentStatement.close(index+symbolLen);
        currentBlock = currentStatement.getChildBlock();
        currentBlock.singleStatement(true);
        currentStatement = currentBlock.addStatement(index+symbolLen);
    }
    
    private void parseForReference(final int index){
        currentStatement.parseSegment(index);
    }
    
    @Override
    public void addReference(String source){
        if(!this.hasModel())
            return;
        try {
            this.references.add(this.getProject().findClass(source));
        } catch (DoesNotExistException ex) {
            //add not found stuff here.
        }
    }
    
}
