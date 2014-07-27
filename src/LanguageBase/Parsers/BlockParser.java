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
    
    public BlockParser(BaseModel aModel){
        this();
        this.baseModel = aModel;
    }
    
    public LinkedList<BlockNode> getBlocks(){
        return blocks;
    }
    
    public BlockNode getRootBlock(){
        return root;
    }

    @Override
    protected void parseOpenCurlyBracket(int index) {
        currentStatement.close(index);
        this.currentBlock = this.currentStatement.getChildBlock();
        currentStatement = currentBlock.addStatement(index+1);
    }

    @Override
    protected void parseCloseCurlyBracket(int index) {
        this.currentBlock = this.currentBlock.getParentBlock();
        currentStatement = this.currentBlock.addStatement(index+1);
        while(currentBlock.isSingleStatement()){
            currentBlock = currentBlock.getParentBlock();
            currentStatement = this.currentBlock.addStatement(index);
        }
    }

    @Override
    protected void parseOpenParen(int index) {
    }

    @Override
    protected void parseCloseParen(int index) {
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
    protected void parsePeriod(int index) {
    }

    @Override
    protected void parseSemicolon(int index) {
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
    protected void parseStringLiteral(int index){
    }
    
    @Override
    protected void parseMultiLineComment(int index) throws ParseException{
        int end = index;
        while(source.charAt(end)!= '*' && source.charAt(++end) != '/')
            if(source.charAt(end) == '\n')
                lineCount++;
        currentStatement.close(end);
        currentStatement = currentBlock.addStatement(end+1);
        this.parseFrom(end + 1);
    }
    
    @Override
    protected void parseSingleLineComment(int index) throws ParseException{
        int end = index;
        if(currentStatement.isOpen())
            while(source.charAt(++end) != '\n');
        currentStatement.close(end);
        currentStatement = currentBlock.addStatement(end+1);
        lineCount++;
        //parseFrom(end)? and be sure to break from the main loop?
        this.parseFrom(end + 1);
    }
    
    @Override
    protected BaseModel modelFromSource(String source){
        return this.baseModel;
    }
    
    private void parseElseStatement(int index){
        if(this.nextNonWhiteCharFrom(index + 4) != 'i') //if the next thing isnt an 'if'
            this.basicParseReservedSymbol(index, "else");
    }
    
    private void parseDoStatement(int index){
        this.basicParseReservedSymbol(index, "do");
    }
    
    private void basicParseReservedSymbol(int index, String symbol){
        int symbolLen = symbol.length();
        if(this.nextNonWhiteCharFrom(index+symbolLen) == '{')
            return;
        currentStatement.close(index+symbolLen);
        currentBlock = currentStatement.getChildBlock();
        currentBlock.singleStatement(true);
        currentStatement = currentBlock.addStatement(index+symbolLen);
    }
    
}
