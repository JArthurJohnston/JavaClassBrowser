/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Internal.BaseTest;
import LanguageBase.Parsers.Parser.ParseNode;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ParserTest extends BaseTest{
    private Parser parser;
    
    public ParserTest() {
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
    
    @Test
    public void testInit(){
        parser = new Parser("");
        assertTrue(parser.getNodes().isEmpty());
        assertEquals("", parser.source);
    }
    
    private void verifyParserWithSimpleStatements(String source, String decl, String stmt){
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        this.compareStrings(decl, node.getSource());
        assertEquals(1, node.getNodes().size());
        assertEquals(0, node.start);
        
        node = node.getNodes().getFirst();
        assertEquals(source.length()-1, node.end);
        this.compareStrings(stmt, node.getSource());
    }
    
    @Test
    public void testParseMultipleSimpleStatements(){
        String source = "if(someBoolean()){\n"
                + "someMethod();\n"
                + "someOtherMethod();\n"
                + "}";
        parser = new Parser(source);
        
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        assertEquals(2, node.getNodes().size());
        this.compareStrings("if(someBoolean()){", node.getSource());
        this.compareStrings("\nsomeMethod();", node.getNodes().getFirst().getSource());
        this.compareStrings("\nsomeOtherMethod();\n}", node.getNodes().getLast().getSource());
    }

    @Test
    public void testParseSimpleStatements() {
        String source = "if(someBoolean()){someVar = aVar;}";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean()){", 
                "someVar = aVar;}");
        
        source = "if(someBoolean()){someMethod();}";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean()){", 
                "someMethod();}");
        
        source = "if(someBoolean())someMethod();";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean())", 
                "someMethod();");
        
        source = "if(someBoolean)someMethod();";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean)", 
                "someMethod();");
        
        source = "while(someBoolean()){someMethod();}";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "while(someBoolean()){", 
                "someMethod();}");
        
        source = "while(someBoolean())someMethod();";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "while(someBoolean())", 
                "someMethod();");
        
        source = "for(some;setOf;things){someMethod();}";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "for(some;setOf;things){", 
                "someMethod();}");
        
        source = "for(some;setOf;things)someMethod();";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "for(some;setOf;things)", 
                "someMethod();");
    }
    
    @Test
    public void testNoSoSimpleStatements(){
        String source = "if(someBoolean()){someMethod().chainedMethod();}";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean()){", 
                "someMethod().chainedMethod();}");
    }
    
    @Test
    public void testParseDoLoop(){
        String source = "do{someStatement();}while(someBoolean());";
        parser = new Parser(source);
        assertEquals(2, parser.getNodes().size());
        
        ParseNode node = parser.getNodes().get(0);
        assertEquals(0, node.start);
        assertEquals(2, node.end);
        assertEquals(1, node.getNodes().size());
        assertEquals("do{", node.getSource());
        
        node = node.getNodes().get(0);
        assertEquals(3, node.start);
        assertEquals(19, node.end);
        this.compareStrings("someStatement();}", node.getSource());
        
        node = parser.getNodes().get(1);
        assertEquals(20, node.start);
        assertEquals(source.length()-1, node.end);
        this.compareStrings("while(someBoolean());", node.getSource());
    }
    
    @Test
    public void testParseDoLoopWithoutBracktes(){
        String source = "do someStatement(); while(someBoolean());";
        parser = new Parser(source);
        assertEquals(2, parser.getNodes().size());
        
        ParseNode node = parser.getNodes().get(0);
        assertEquals(0, node.start);
        assertEquals(2, node.end);
        assertEquals(1, node.getNodes().size());
        assertEquals("do ", node.getSource());
        
        node = node.getNodes().get(0);
        assertEquals(3, node.start);
        assertEquals(18, node.end);
        assertEquals("someStatement();", node.getSource());
        
        node = parser.getNodes().get(1);
        assertEquals(20, node.start);
        assertEquals(source.length()-1, node.end);
        this.compareStrings("while(someBoolean());", node.getSource());
    }
    
    @Test
    public void testParseNestedStatements(){
        String source = "if(someBoolean()){"
                + "if(someOtherBoolean()){"
                + "someStatement();}}";
        parser = new Parser(source);
        assertEquals(1, parser.getNodes().size());
        
        ParseNode node = parser.getNodes().getFirst();
        this.compareStrings("if(someBoolean()){", node.getSource());
        node = node.getNodes().getFirst();
        this.compareStrings("if(someOtherBoolean()){", node.getSource());
        node = node.getNodes().getFirst();
        this.compareStrings("someStatement();}}", node.getSource());
    }
    
    @Test
    public void testParseAnonymousInnerClass(){
        String source = "someMethod(new AnonymClass{"
                + "someMethod();"
                + "});";
        parser = new Parser(source);
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        assertEquals(1, node.getNodes().size());
        this.compareStrings("someMethod(new AnonymClass{", node.getSource());
        node = node.getNodes().getFirst();
        this.compareStrings("someMethod();});", node.getSource());
        /*
        This test is currently failing. the result the code produces is
        ugly, but technically correct. Ignore for now.
        
        its currently storing the trailing ');' in its own node. which, while ugly,
        isnt THAT bad an outcome. 
        */
    }
    
    @Test
    public void testParseAddsComments(){
        String source = "if(someBoolean()){//commentedOutCode();\nsomeMethod();}";
        parser = new Parser(source);
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        this.compareStrings("if(someBoolean()){", node.getSource());
        assertEquals(2, node.getNodes().size());
        
        this.compareStrings("//commentedOutCode();\n", node.getNodes().getFirst().getSource());
        this.compareStrings("someMethod();}", node.getNodes().getLast().getSource());
        
        source = "if(someBoolean()){/**commentedOutCode();\n */someMethod();}";
        parser = new Parser(source);
        assertEquals(1, parser.getNodes().size());
        node = parser.getNodes().getFirst();
        this.compareStrings("if(someBoolean()){", node.getSource());
        assertEquals(2, node.getNodes().size());
        
        this.compareStrings("/**commentedOutCode();\n */", node.getNodes().getFirst().getSource());
        this.compareStrings("someMethod();}", node.getNodes().getLast().getSource());
    }
    
    private void verifyParseSimpleString(String stmt, String body){
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        assertEquals(1, node.getNodes().size());
        this.compareStrings(stmt, node.getSource());
        node = node.getNodes().getFirst();
        this.compareStrings(body, node.getSource());
    }
    
    @Test
    public void testParseIgnoresStringLiteral(){
        String source = "if(something){String thing = \"some string\";}";
        parser = new Parser(source);
        this.verifyParseSimpleString("if(something){", 
                "String thing = \"some string\";}");
        
        source = "if(something){String thing = \"some \"string\"\";}";
        parser = new Parser(source);
        this.verifyParseSimpleString("if(something){", 
                "String thing = \"some \"string\"\";}");
        
        source = "if(something){String thing = \'some \'string\'\';}";
        parser = new Parser(source);
        this.verifyParseSimpleString("if(something){", 
                "String thing = \'some \'string\'\';}");
        String[] test = new String[]{"",""};
    }
    
    @Test
    public void testParseEnum(){
        String source = "public enum ClassType {\n" +
                            "STATIC, INSTANCE;\n" +
                        "}";
        parser = new Parser(source);
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        this.compareStrings("public enum ClassType {", node.getSource());
        node = node.getNodes().getFirst();
        this.compareStrings("\nSTATIC, INSTANCE;\n}", node.getSource());
    }
    
    @Test
    public void testParseIfElse(){
        String source = "if(someBoolean()){"
                + "someMethod();"
                + "} else {"
                + "someOtherMethod();"
                + "}";
        parser = new Parser(source);
        assertEquals(2, parser.getNodes().size());
        
        ParseNode node = parser.getNodes().getFirst();
        assertEquals(1, node.getNodes().size());
        this.compareStrings("if(someBoolean()){", node.getSource());
        
        node = node.getNodes().getFirst();
        assertTrue(node.getNodes().isEmpty());
        this.compareStrings("someMethod();}", node.getSource());
        
        node = parser.getNodes().getLast();
        assertEquals(1, node.getNodes().size());
        this.compareStrings(" else {", node.getSource());
        
        node = node.getNodes().getFirst();
        assertTrue(node.getNodes().isEmpty());
        this.compareStrings("someOtherMethod();}", node.getSource());
    }
    
    //testParseIfElseWithoutBraces
    // testIgnoresArrayDeclaration
    //eventually gotta test for errors
    
}
