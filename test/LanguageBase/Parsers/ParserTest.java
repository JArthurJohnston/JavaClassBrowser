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
    public void testParseSimpleStatements() {
        String source = "if(someBoolean()){someMethod();}";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean())", 
                "{someMethod();}");
        
        source = "if(someBoolean())someMethod();";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "if(someBoolean())", 
                "someMethod();");
        
        source = "while(someBoolean()){someMethod();}";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "while(someBoolean())", 
                "{someMethod();}");
        
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
                "for(some;setOf;things)", 
                "{someMethod();}");
        
        source = "for(some;setOf;things)someMethod();";
        parser = new Parser(source);
        this.verifyParserWithSimpleStatements(
                source, 
                "for(some;setOf;things)", 
                "someMethod();");
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
        assertEquals(18, node.end);
        assertEquals("someStatement();", node.getSource());
        
        node = parser.getNodes().get(1);
        assertEquals(19, node.start);
        assertEquals(source.length()-1, node.end);
        this.compareStrings("}while(someBoolean());", node.getSource());
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
        String source = "if(someBoolean()){if(someOtherBoolean()){someStatement();}}";
        //repeat for each statement type
        fail();
    }
    
    @Test
    public void testParseAnonymousInnerClass(){
        fail();
    }
    
    @Test
    public void testParseIgnoresComments(){
        String source = "if(someBoolean()){//commentedOutCode();\nsomeMethod();}";
        parser = new Parser(source);
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        assertEquals(1, node.getNodes().size());
        assertEquals(0, node.start);
        assertEquals(source.length()-1, node.end);
        
        node = node.getNodes().getFirst();
        assertEquals(51, node.start);
        assertEquals(source.length()-1, node.end);
    }
    
    //testIgnoresComment, testIngoresStringLiteral, testIgnoresArrayDeclaration, etc...
    //testParseIfElse
    
}
