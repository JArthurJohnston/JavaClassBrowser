/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Nodes;

import LanguageBase.Parsers.Parser;
import LanguageBase.Parsers.SourceReference;
import Models.ProjectModel;
import Types.ClassType;
import Types.ScopeType;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class StatementNode extends BaseNode{
    private final int start;
    private int end, openParenPtr, closeParenPtr, parsePtr;
    private final BlockNode parentBlock;
    private BlockNode childBlock;
    private ScopeType scope;
    private ClassType side;
    private StatementType type; 
    private boolean isOpenParen;
    private final LinkedList<SourceReference> arguments;
    
    public StatementNode(final BlockNode parent, final int start){
        this.arguments = new LinkedList();
        this.parentBlock = parent;
        this.start = start;
    }
    
    public StatementNode(final BlockNode parent, final int start, final int end){
        this(parent, start);
        this.end = end;
    }
    
    public BlockNode getParentBlock(){
        return this.parentBlock;
    }
    
    public BlockNode getChildBlock(){
        if(this.childBlock == null)
            childBlock = new BlockNode(this);
        return this.childBlock;
    }
    
    public Parser getTree(){
        return this.parentBlock.getTree();
    }
    
    public String getSource(){
        if(end < start)
            return "";
        return this.source().substring(start, end).trim();
    }
    
    public void close(final int end){
        this.end = end;
    }
    
    public boolean isOpen(){
        return this.end == 0;
    }
    
    private String source(){
        return this.parentBlock.source();
    }
    
    public boolean isClassDeclaration(){
        return this.type == StatementType.ClassDecl;
    }
    
    public ScopeType getScope(){
        if(this.scope == null)
            return ScopeType.NONE;
        return scope;
    }
    
    public ClassType getClassType(){
        if(this.side == null)
            return ClassType.INSTANCE;
        return side;
    }
    
    public int openParenPointer(){
        return this.openParenPtr;
    }
    
    public void setOpenParen(final int index){
        this.isOpenParen = true;
        this.openParenPtr = index;
    }
    
    public int closeParenPointer(){
        return this.closeParenPtr;
    }
    
    public void setCloseParen(final int index){
        this.isOpenParen = false;
        this.closeParenPtr = index;
    }
    
    public void parseStatement(){
        for(String s : this.getSource().split("\\s+")){ //split at white spaces
            switch(s){
                case "class":
                    this.type = StatementType.ClassDecl;
                    break;
                case "static":
                    this.side = ClassType.STATIC;
                    break;
                case "private":
                    this.scope = ScopeType.PRIVATE;
                    break;
                case "public":
                    this.scope = ScopeType.PUBLIC;
                    break;
                case "protected":
                    this.scope = ScopeType.PROTECTED;
                    break;
                default:
                    break;
            }
        }
    }
    
    public SourceReference parseSegment(final int currentPtr){
        int temp = this.parsePtr;
        this.parsePtr = currentPtr+1;
        if(!this.isValidSegment(temp, parsePtr))
            return null;
        if(this.isOpenParen){
            this.arguments.add(new SourceReference(this.getTree(), temp, parsePtr));
            return this.arguments.getLast();
        }
        return new SourceReference(this.getTree(), temp, parsePtr);
    }
    
    private boolean isValidSegment(final int startIndex, final int endIndex){
        if(startIndex >= endIndex)
            return false;
        if(endIndex == 0)
            return false;
        String source = this.source().substring(startIndex, endIndex);
        if(source.isEmpty())
            return false;
        if(ProjectModel.getReservedWords().containsKey(source))
            return false;
        return true;
    }
    
    public LinkedList<SourceReference> getArguments(){
        return this.arguments;
    }
    
    public boolean isMethodDeclaration(){
        return this.type == StatementType.MethodDecl;
    }
    
    private ProjectModel getProject(){
        return this.getTree().getProject();
    }
    
    public boolean isOpenParen(){
        return this.isOpenParen;
    }
}
