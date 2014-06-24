/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Internal.BaseTest;
import LanguageBase.Parsers.Parser.ParseNode;
import MainBase.SortedList;
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
        this.initializeParserWith("");
        assertTrue(parser.getNodes().isEmpty());
        assertEquals("", parser.source);
    }
    
    private void initializeParserWith(String source){
        try {
            parser = new Parser(source);
        } catch (BaseParseTree.ParseException ex) {
            System.out.println(ex.getMessage());
            fail();
        }
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
        
        assertEquals(2, parser.getLines().size());
        this.compareStrings(decl, parser.getLines().getFirst().getSource());
        this.compareStrings(stmt, parser.getLines().getLast().getSource());
        
        for(ParseNode pn : parser.getLines())
            assertEquals(parser, pn.getRoot());
    }
    
    @Test
    public void testParseMultipleSimpleStatements(){
        String source = "if(someBoolean()){\n"
                + "someMethod();\n"
                + "someOtherMethod();\n"
                + "}";
        this.initializeParserWith(source);
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
        this.initializeParserWith(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean()){", 
                "someVar = aVar;}");
        
        source = "if(someBoolean()){someMethod();}";
        this.initializeParserWith(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean()){", 
                "someMethod();}");
        
        source = "if(someBoolean())someMethod();";
        this.initializeParserWith(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean())", 
                "someMethod();");
        
        source = "if(someBoolean)someMethod();";
        this.initializeParserWith(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean)", 
                "someMethod();");
        
        source = "while(someBoolean()){someMethod();}";
        this.initializeParserWith(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "while(someBoolean()){", 
                "someMethod();}");
        
        source = "while(someBoolean())someMethod();";
        this.initializeParserWith(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "while(someBoolean())", 
                "someMethod();");
        
        source = "for(some;setOf;things){someMethod();}";
        this.initializeParserWith(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "for(some;setOf;things){", 
                "someMethod();}");
        
        source = "for(some;setOf;things)someMethod();";
        this.initializeParserWith(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "for(some;setOf;things)", 
                "someMethod();");
    }
    
    @Test
    public void testNoSoSimpleStatements(){
        String source = "if(someBoolean()){someMethod().chainedMethod();}";
        this.initializeParserWith(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean()){", 
                "someMethod().chainedMethod();}");
    }
    
    public void testParseMultipleIfs(){
        String source = "if(someBoolean()){"
                            + "someMethod().chainedMethod();"
                            + "if(someOtherBoolean)"
                                + "someMethod().someVar;"
                        + "}";
        this.initializeParserWith(source);
        
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        this.compareStrings("if(someBoolean()){", node.getSource());
        assertEquals(2, node.getNodes().size());
        this.compareStrings("someMethod().chainedMethod();"
                , node.getNodes().getFirst().getSource());
        
        node = node.getNodes().getLast();
        this.compareStrings("if(someOtherBoolean)"
                , node.getSource());
        assertEquals(1, node.getNodes().size());
        
        node = node.getNodes().getFirst();
        this.compareStrings("someMethod().someVar;}", node.getSource());
        
        
        assertEquals(4, parser.getLines().size());
        this.compareLists(parser.getLines(), new SortedList()
                .addElm("if(someBoolean()){")
                .addElm("someMethod().chainedMethod();")
                .addElm("if(someOtherBoolean)")
                .addElm("someMethod().someVar;}"));
    }
    
    @Test
    public void testParseDoLoop(){
        String source = "do{someStatement();}while(someBoolean());";
        this.initializeParserWith(source);
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
        this.initializeParserWith(source);
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
        this.initializeParserWith(source);
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
        this.initializeParserWith(source);
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
        this.initializeParserWith(source);
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        this.compareStrings("if(someBoolean()){", node.getSource());
        assertEquals(2, node.getNodes().size());
        
        this.compareStrings("//commentedOutCode();\n", node.getNodes().getFirst().getSource());
        this.compareStrings("someMethod();}", node.getNodes().getLast().getSource());
        
        source = "if(someBoolean()){/**commentedOutCode();\n */someMethod();}";
        this.initializeParserWith(source);
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
        this.initializeParserWith(source);
        this.verifyParseSimpleString("if(something){", 
                "String thing = \"some string\";}");
        
        source = "if(something){String thing = \"some \"string\"\";}";
        this.initializeParserWith(source);
        this.verifyParseSimpleString("if(something){", 
                "String thing = \"some \"string\"\";}");
        
        source = "if(something){String thing = \'some \'string\'\';}";
        this.initializeParserWith(source);
        this.verifyParseSimpleString("if(something){", 
                "String thing = \'some \'string\'\';}");
        String[] test = new String[]{"",""};
    }
    
    @Test
    public void testParseEnum(){
        String source = "public enum ClassType {\n" +
                            "STATIC, INSTANCE;\n" +
                        "}";
        this.initializeParserWith(source);
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        this.compareStrings("public enum ClassType {", node.getSource());
        node = node.getNodes().getFirst();
        this.compareStrings("\nSTATIC, INSTANCE;\n}", node.getSource());
    }
    
    private void verifyParseIfElse(String[] source){
        assertEquals(2, parser.getNodes().size());
        
        ParseNode node = parser.getNodes().getFirst();
        assertEquals(1, node.getNodes().size());
        this.compareStrings(source[0], node.getSource());
        
        node = node.getNodes().getFirst();
        assertTrue(node.getNodes().isEmpty());
        this.compareStrings(source[1], node.getSource());
        
        node = parser.getNodes().getLast();
        assertEquals(1, node.getNodes().size());
        this.compareStrings(source[2], node.getSource());
        
        node = node.getNodes().getFirst();
        assertTrue(node.getNodes().isEmpty());
        this.compareStrings(source[3], node.getSource());
        
    }
    
    @Test
    public void testParseIfElse(){
        String source = "if(someBoolean()){"
                + "someMethod();"
                + "} else {"
                + "someOtherMethod();"
                + "}";
        this.initializeParserWith(source);
        
        this.verifyParseIfElse(new String[]{
                "if(someBoolean()){",
                "someMethod();}",
                " else {",
                "someOtherMethod();}"});
    }
    
    @Test
    public void testParseIfElseIf(){
        String source = "if(someBoolean()){"
                + "someMethod();"
                + "} else if(someOtherBoolean()){"
                + "someOtherMethod();"
                + "}";
        this.initializeParserWith(source);
        this.verifyParseIfElse(new String[]{
                "if(someBoolean()){",
                "someMethod();}",
                " else if(someOtherBoolean()){",
                "someOtherMethod();}"});
    }
    
    @Test
    public void testParsedLines(){
        String source = "if(something){String thing = \"some string\";}";
        this.initializeParserWith(source);
        
        assertEquals(2, parser.getLines().size());
        this.compareStrings("if(something){", 
                parser.getLines().getFirst().getSource());
        this.compareStrings("String thing = \"some string\";}", 
                parser.getLines().getLast().getSource());
        
        source = "if(someBoolean()){"
                + "if(someOtherBoolean()){"
                + "someStatement();}}";
        this.initializeParserWith(source);
        assertEquals(3, parser.getLines().size());
        this.compareStrings("if(someBoolean()){", 
                parser.getLines().get(0).getSource());
        this.compareStrings("if(someOtherBoolean()){", 
                parser.getLines().get(1).getSource());
        this.compareStrings("someStatement();}}", 
                parser.getLines().get(2).getSource());
    }
    
    @Test
    public void testLineNumberFromIndex(){
        String source = "if(someBoolean()){"
                + "if(someOtherBoolean()){"
                + "someStatement();}}";
        this.initializeParserWith(source);
        
        assertEquals(-1, parser.lineNumberFromIndex(-1));
        assertEquals(-1, parser.lineNumberFromIndex(61));
        
        assertEquals(1, parser.lineNumberFromIndex(0));
        assertEquals(1, parser.lineNumberFromIndex(9));
        assertEquals(1, parser.lineNumberFromIndex(17));
        
        assertEquals(2, parser.lineNumberFromIndex(18));
        assertEquals(2, parser.lineNumberFromIndex(22));
        assertEquals(2, parser.lineNumberFromIndex(40));
        
        assertEquals(3, parser.lineNumberFromIndex(41));
        assertEquals(3, parser.lineNumberFromIndex(49));
        assertEquals(3, parser.lineNumberFromIndex(58));
    }
    
    @Test
    public void testParserErrors(){
        String source = "if(someBoolean()){"
                + "if(someOtherBoolean()){"
                + "someStatement();}";
        this.initializeParserWith(source);
        
        assertEquals(1, parser.getErrors().size());
        this.compareStrings("reached end with unclosed {", 
                parser.getErrors().getFirst());
        
        source = "if(someBoolean()){"
                + "if(someOtherBoolean())"
                + "someStatement();}}";
        this.initializeParserWith(source);
        
        assertEquals(1, parser.getErrors().size());
        this.compareStrings("Parse error at line: 3", 
                parser.getErrors().getFirst());
    }
    
    //testParseIfElseWithoutBraces
    // testIgnoresArrayDeclaration
    
}
