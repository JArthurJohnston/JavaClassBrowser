/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Internal.BaseTest;
import LanguageBase.Parsers.SimplifiedParser.BlockNode;
import MainBase.SortedList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class SimplifiedParserTest extends BaseTest{
    private SimplifiedParser parser;
    
    public SimplifiedParserTest() {
    }
    
    @Before
    @Override
    public void setUp() {
    }
    
    @After
    @Override
    public void tearDown() {
        parser = null;
    }
    
    public void initializeParserWithSource(String source){
        parser = new SimplifiedParser(source);
        if(parser.getError() != null){
            fail(parser.getError());
        }
    }
    
    private void verifyParserLines(String[] a, String[] b){
        if(a.length != b.length)
            fail();
        for(int i=0; i< a.length; i++)
            this.compareStrings(a[i], b[i]);
    }
    
    //can probably be pushed up
    private void validateParserLinesAgainstSize(int size){
        if(parser.getLines().size() != size){
            System.out.println("Incorrect lines size\n");
            this.displayParserLines();
            fail();
        }
    }
    
    //can probably be pushed up
    private void displayParserLines(){
        for(BlockNode b : parser.getLines())
            System.out.println(b.getSource());
    }
    
    private String[] StringArrayFromLines(){
        String[] lineSources = new String[parser.getLines().size()];
        for(int i = 0; i < parser.getLines().size(); i++)
            lineSources[i] = parser.getLines().get(i).getSource();
        return lineSources;
    }
    
    private void verifyParserWithStatementsWithBrackets(String a, String b, String c){
        assertEquals(2, parser.nodes.size());
        assertEquals(3, parser.getLines().size());
        this.verifyParserLines(new String[]{a,b,c}, this.StringArrayFromLines());
        
        BlockNode node = parser.nodes.getFirst();
        assertEquals(1, node.nodes.size());
        this.compareStrings(a, node.getSource());
        
        node = node.nodes.getFirst();
        assertTrue(node.nodes.isEmpty());
        this.compareStrings(b,node.getSource());
        
        node = parser.nodes.getLast();
        this.compareStrings(c, node.getSource());
    }
    
    private void verifyParserWithStatementsWithoutBrackets(String a, String b){
        assertEquals(1, parser.nodes.size());
        assertEquals(2, parser.getLines().size());
        this.verifyParserLines(new String[]{a,b}, this.StringArrayFromLines());
        
        BlockNode node = parser.nodes.getFirst();
        assertEquals(1, node.nodes.size());
        this.compareStrings(a, node.getSource());
        
        node = node.nodes.getFirst();
        assertTrue(node.nodes.isEmpty());
        this.compareStrings(b,node.getSource());
    }

    
    @Test
    public void testParseSimpleStatements() {
        String source = "if(someBoolean()){someMethod();}";
        this.initializeParserWithSource(source);
        this.verifyParserWithStatementsWithBrackets(
                "if(someBoolean()){", 
                "someMethod();", 
                "}");
        
        source = "if(someBoolean().somethingElse()){someMethod();}";
        this.initializeParserWithSource(source);
        this.verifyParserWithStatementsWithBrackets(
                "if(someBoolean().somethingElse()){", 
                "someMethod();", 
                "}");
        
        source = "while(someBoolean()){someMethod().someVar;}";
        this.initializeParserWithSource(source);
        this.verifyParserWithStatementsWithBrackets(
                "while(someBoolean()){", 
                "someMethod().someVar;", 
                "}");
        
        source = "for(some; kindOf; stuff){someMethod();}";
        this.initializeParserWithSource(source);
        this.verifyParserWithStatementsWithBrackets(
                "for(some; kindOf; stuff){", 
                "someMethod();", 
                "}");
    }
    
    @Test
    public void testParseSimpleStatementsWithoutBrackets(){
        String source = "if(someBoolean())someMethod();";
        this.initializeParserWithSource(source);
        this.verifyParserWithStatementsWithoutBrackets(
                "if(someBoolean())", 
                "someMethod();");
        
        source = "while(someBoolean().someVar)someMethod();";
        this.initializeParserWithSource(source);
        this.verifyParserWithStatementsWithoutBrackets(
                "while(someBoolean().someVar)", 
                "someMethod();");
        
        source = "for(some; kindOf; stuff)someMethod();";
        this.initializeParserWithSource(source);
        this.verifyParserWithStatementsWithoutBrackets(
                "for(some; kindOf; stuff)", 
                "someMethod();");
    }
    
    @Test
    public void testIfElseStatement(){
        String source = "if(someBoolean()){"
                            + "someMethod();"
                        + "} else {"
                            + "someOtherMethod();"
                        + "}";
        this.initializeParserWithSource(source);
        assertEquals(3, parser.nodes.size());
        this.validateParserLinesAgainstSize(5);
        
        BlockNode node = parser.nodes.getFirst();
        this.compareStrings("if(someBoolean()){", node.getSource());
        node = node.nodes.getFirst();
        this.compareStrings("someMethod();", node.getSource());
        
        node = parser.nodes.get(1);
        this.compareStrings("} else {", node.getSource());
        node = node.nodes.getFirst();
        this.compareStrings("someOtherMethod();", node.getSource());
        
        node = parser.nodes.getLast();
        this.compareStrings("}", node.getSource());
    }
    
    @Test
    public void testIfElseStatementWithoutBrackets(){
        String source = "if(someBoolean())"
                            + "someMethod();"
                        +"else "
                            + "someOtherMethod();";
        this.initializeParserWithSource(source);
        assertEquals(2, parser.nodes.size());
        this.validateParserLinesAgainstSize(4);
    }
    
    
    @Test
    public void testParseMultipleSimpleStatements(){
        String source = "if(someBoolean()){\n"
                + "someMethod();\n"
                + "someOtherMethod();\n"
                + "}";
        this.initializeParserWithSource(source);
        assertEquals(2, parser.getScopes().size());
        BlockNode node = parser.getScopes().getFirst();
        assertEquals(2, node.getScopes().size());
        this.compareStrings("if(someBoolean()){", node.getSource());
        this.compareStrings("\nsomeMethod();", node.getScopes().getFirst().getSource());
        this.compareStrings("\nsomeOtherMethod();", node.getScopes().getLast().getSource());
        this.compareStrings("}", parser.getScopes().getLast().getSource());
    }
    
    @Test
    public void testParseNestedStatements(){
        String source = "if(someBoolean()){"
                + "if(someOtherBoolean()){"
                + "someStatement();}}";
        this.initializeParserWithSource(source);
        assertEquals(2, parser.getScopes().size());
        
        BlockNode node = parser.getScopes().getFirst();
        assertEquals(2, node.getScopes().size());
        this.compareStrings("if(someBoolean()){", node.getSource());
        
        node = node.getScopes().getFirst();
        assertEquals(1, node.getScopes().size());
        this.compareStrings("if(someOtherBoolean()){", node.getSource());
        
        node = node.getScopes().getFirst();
        assertTrue(node.getScopes().isEmpty());
        this.compareStrings("someStatement();", node.getSource());
        
        node = parser.getScopes().getFirst().getScopes().getLast();
        assertTrue(node.getScopes().isEmpty());
        this.compareStrings("}", node.getSource());
        
        node = parser.getScopes().getLast();
        assertTrue(node.getScopes().isEmpty());
        this.compareStrings("}", node.getSource());
    }
    
    @Test
    public void testParseNestedStatementsWithoutBracktes(){
        String source = "if(someBoolean())"
                        + "if(someOtherBoolean())"
                            + "someStatement();";
        this.initializeParserWithSource(source);
        assertEquals(1, parser.getScopes().size());
        
        BlockNode node = parser.getScopes().getFirst();
        assertEquals(1, node.getScopes().size());
        this.compareStrings("if(someBoolean())", node.getSource());
        
        node = node.getScopes().getFirst();
        assertEquals(1, node.getScopes().size());
        this.compareStrings("if(someOtherBoolean())", node.getSource());
        
        node = node.getScopes().getFirst();
        assertTrue(node.getScopes().isEmpty());
        this.compareStrings("someStatement();", node.getSource());
    }
    
    @Test
    public void testParseMultipleStatements(){
        String source = "if(someBoolean()){"
                            + "someMethod().chainedMethod();"
                            + "if(someOtherBoolean)"
                                + "someMethod().someVar;"
                        + "}";
        this.initializeParserWithSource(source);
        assertEquals(2, parser.getScopes().size());
        
        BlockNode node = parser.getScopes().getFirst();
        assertEquals(2, node.getScopes().size());
        this.compareStrings("if(someBoolean()){", node.getSource());
        
        this.compareStrings("someMethod().chainedMethod();", node.getScopes().getFirst().getSource());
        assertTrue(node.getScopes().getFirst().getScopes().isEmpty());
        
        node = node.getScopes().getLast();
        assertEquals(1, node.getScopes().size());
        this.compareStrings("if(someOtherBoolean)", node.getSource());
        
        node = node.getScopes().getLast();
        assertTrue(node.getScopes().isEmpty());
        this.compareStrings("someMethod().someVar;", node.getSource());
        
        node = parser.getScopes().getLast();
        assertTrue(node.getScopes().isEmpty());
        this.compareStrings("}", node.getSource());
    }
    
    @Test
    public void testParseEnum(){
        String source = "public enum ClassType {\n" +
                            "STATIC, INSTANCE;\n" +
                        "}";
        this.initializeParserWithSource(source);
        assertEquals(2, parser.getScopes().size());
        
        BlockNode node = parser.getScopes().getFirst();
        assertEquals(1, node.getScopes().size());
        this.compareStrings("public enum ClassType {",node.getSource());
        
        node = node.getScopes().getFirst();
        assertTrue(node.getScopes().isEmpty());
        this.compareStrings("\nSTATIC, INSTANCE;",node.getSource());
        
        node = parser.getScopes().getLast();
        assertTrue(node.getScopes().isEmpty());
        this.compareStrings("}",node.getSource());
    }
    
    @Test
    public void testIfWithTryCatch(){
        String source = "if(someBoolean())"
                        + "try{"
                            + "someExpression();"
                        + "} catch(someException e) {"
                            + "doSomethingWithTheException(e);"
                        + "}";
        this.initializeParserWithSource(source);
        assertEquals(1, parser.getScopes().size());
        
        BlockNode node = parser.getScopes().getFirst();
        assertEquals(3, node.getScopes().size());
    }
    
    /*
    a lot of these could be fixed with a 
    nodeThatOpenedBlock variable in the stack...
    just sayin.
    */
    
    @Test
    public void testDoWhileLoop(){
        String source = "do{"
                            + "someStatement();"
                        + "}while(someBoolean());";
        this.initializeParserWithSource(source);
        assertEquals(2, parser.getScopes().size());
    }
    
    @Test
    public void testParseAnonymousInnerClass(){
        String source = "someMethod(new AnonymClass{"
                + "someMethod();"
                + "});";
        this.initializeParserWithSource(source);
        assertEquals(2, parser.getScopes().size());
        
        BlockNode node = parser.getScopes().getFirst();
        this.compareStrings("someMethod(new AnonymClass{", node.getSource());
        assertEquals(1, node.getScopes().size());
        
        node = node.getScopes().getLast();
        this.compareStrings("someMethod();", node.getSource());
        assertTrue(node.getScopes().isEmpty());
        
        this.compareStrings("});", parser.getScopes().getLast().getSource());
        assertTrue(parser.getScopes().getLast().getScopes().isEmpty());
    }
}
