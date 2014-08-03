/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Nodes;

import LanguageBase.Parsers.Parser;
import Models.ClassModel;
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
    private ClassModel objectType;
    private ScopeType scope;
    private ClassType side;
    private StatementType type; 
    private boolean isOpenParen;
    private final LinkedList<String> arguments;
    private final LinkedList<String> references;
    
    public StatementNode(final BlockNode parent, final int start){
        this.arguments = new LinkedList();
        this.references = new LinkedList();
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
    
    private void setOpenParen(final int index){
        this.isOpenParen = true;
        this.openParenPtr = index;
    }
    
    private void setCloseParen(final int index){
        this.isOpenParen = false;
        this.closeParenPtr = index;
        this.parseArgumentSegment();
    }
    
    private void parseArgumentSegment(){
        String[] argumentSegments = source()
                .substring(this.openParenPtr+1, 
                        this.closeParenPtr).split(",");
        for (String arg : argumentSegments) {
            String[] methodParamArg = arg.trim().split(" ");
            if(methodParamArg.length > 1){
                this.arguments
                        .add(this.addReferenceForArgument(methodParamArg));
            }else
                this.arguments.add(arg.trim());
        }
    }
    
    private String addReferenceForArgument(String[] argument){
        return argument[0];
    }
    
    private void parseStatementModifier(String source){
            switch(source){
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
    
    public void parseSegment(final int currentPtr){
        int segmentStart = this.parsePtr;
        this.parsePtr = currentPtr+1;
        if(isValidReference(segmentStart, parsePtr)){
            if(!this.isOpenParen){
                this.parentBlock.addReference(source().substring(segmentStart, currentPtr).trim());
                this.references.add(source().substring(segmentStart, currentPtr).trim());
            }
            if(this.source().charAt(currentPtr) == '('){
                this.setOpenParen(currentPtr);
            }
            if(this.source().charAt(currentPtr) == ')'){
                this.setCloseParen(currentPtr);
            }
        }
    }
    
    private boolean isValidReference(final int startIndex, final int endIndex){
        if(this.source().isEmpty())
            return false;
        if(startIndex >= endIndex)
            return false;
        if(endIndex == 0)
            return false;
        if(endIndex - startIndex <= 1)
            return false;
        String source = this.source().substring(startIndex, endIndex).trim();
        if(source.isEmpty())
            return false;
        if(ProjectModel.getReservedWords().containsKey(source)){
            this.parseStatementModifier(source);
            if(ClassModel.getPrimitive(source) != null)
                this.objectType = ClassModel.getPrimitive(source);
            return false;
        }
        return true;
    }
    
    public LinkedList<String> getArguments(){
        return this.arguments;
    }
    
    public boolean isMethodDeclaration(){
        return this.type == StatementType.MethodDecl;
    }
    
    public ClassModel getObjectType(){
        return objectType;
    }
    
    public boolean isOpenParen(){
        return this.isOpenParen;
    }
    
    public LinkedList<String> getReferences(){
        return this.references;
    }
}
