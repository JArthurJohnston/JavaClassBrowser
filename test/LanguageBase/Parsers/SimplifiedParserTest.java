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
    
    private void verifyParserLines(String[] a, String[] b){
        if(a.length != b.length)
            fail();
        for(int i=0; i< a.length; i++)
            this.compareStrings(a[i], b[i]);
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
        parser = new SimplifiedParser(source);
        this.verifyParserWithStatementsWithBrackets(
                "if(someBoolean()){", 
                "someMethod();", 
                "}");
        
        source = "if(someBoolean().somethingElse()){someMethod();}";
        parser = new SimplifiedParser(source);
        this.verifyParserWithStatementsWithBrackets(
                "if(someBoolean().somethingElse()){", 
                "someMethod();", 
                "}");
        
        source = "while(someBoolean()){someMethod().someVar;}";
        parser = new SimplifiedParser(source);
        this.verifyParserWithStatementsWithBrackets(
                "while(someBoolean()){", 
                "someMethod().someVar;", 
                "}");
        
        source = "for(some; kindOf; stuff){someMethod();}";
        parser = new SimplifiedParser(source);
        this.verifyParserWithStatementsWithBrackets(
                "for(some; kindOf; stuff){", 
                "someMethod();", 
                "}");
    }
    
    @Test
    public void testParseSimpleStatementsWithoutBrackets(){
        String source = "if(someBoolean())someMethod();";
        parser = new SimplifiedParser(source);
        this.verifyParserWithStatementsWithoutBrackets(
                "if(someBoolean())", 
                "someMethod();");
        
        source = "while(someBoolean().someVar)someMethod();";
        parser = new SimplifiedParser(source);
        this.verifyParserWithStatementsWithoutBrackets(
                "while(someBoolean().someVar)", 
                "someMethod();");
        
        source = "for(some; kindOf; stuff)someMethod();";
        parser = new SimplifiedParser(source);
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
        parser = new SimplifiedParser(source);
        assertEquals(3, parser.nodes.size());
        assertEquals(5, parser.getLines().size());
    }
    
}
