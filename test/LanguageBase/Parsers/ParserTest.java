/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import LanguageBase.Parsers.Parser.ParseNode;
import java.util.LinkedList;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ParserTest {
    private Parser parser;
    
    public ParserTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        parser = null;
    }
    
    @Test
    public void testInit(){
        parser = new Parser("");
        assertTrue(parser.getNodes().isEmpty());
        assertEquals("", parser.source);
    }
    
    private void verifySimpleParserWithSimpleStatements(String source){
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        assertEquals(1, node.getNodes().size());
        assertEquals(0, node.start);
        assertEquals(source.length()-1, node.end);
    }

    @Test
    public void testParseSimpleStatements() {
        String source = "if(someBoolean()){someMethod();}";
        parser = new Parser(source);
        this.verifySimpleParserWithSimpleStatements(source);
        
        source = "if(someBoolean())someMethod();";
        parser = new Parser(source);
        this.verifySimpleParserWithSimpleStatements(source);
        
        source = "while(someBoolean()){someMethod();}";
        parser = new Parser(source);
        this.verifySimpleParserWithSimpleStatements(source);
        
        source = "while(someBoolean())someMethod();";
        parser = new Parser(source);
        this.verifySimpleParserWithSimpleStatements(source);
        
        source = "for(some;setOf;things){someMethod();}";
        parser = new Parser(source);
        this.verifySimpleParserWithSimpleStatements(source);
        
        source = "for(some;setOf;things)someMethod();";
        parser = new Parser(source);
        this.verifySimpleParserWithSimpleStatements(source);
    }
    
    @Test
    public void testParseDoLoop(){
        String source = "do{someStatement();}while(someBoolean());";
        parser = new Parser(source);
        assertEquals(1, parser.getNodes().size());
        ParseNode node = parser.getNodes().getFirst();
        assertEquals(1, node.getNodes().size());
        assertEquals(0, node.start);
        assertEquals(source.length()-1, node.end);
        fail();
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
    
}
