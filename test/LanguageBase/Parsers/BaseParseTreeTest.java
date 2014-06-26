/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LanguageBase.Parsers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arthur
 */
public class BaseParseTreeTest {
    private BaseParseTree tree;
    
    public BaseParseTreeTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        tree = null;
    }

    @Test
    public void testSource() {
        tree = new BaseParseTree("someSource()");
        assertEquals("someSource()", tree.source());
    }

    @Test
    public void testSkipWhiteSpaces() {
        String source = "if    (something);";
        tree = new BaseParseTree(source);
        assertEquals(1, tree.skipWhiteSpaces(0));
        assertEquals(6, tree.skipWhiteSpaces(1));
        assertEquals('(', source.charAt(tree.skipWhiteSpaces(1)));
    }

    @Test
    public void testSkipUntilAfterSymbol() {
        String source = "some.testMethod(String thing);";
        tree = new BaseParseTree(source);
        assertEquals('o', source.charAt(tree.skipUntilAfterSymbol(0, "s")));
        assertEquals('t', source.charAt(tree.skipUntilAfterSymbol(0, ".")));
        assertEquals('e', source.charAt(tree.skipUntilAfterSymbol(0, "M")));
        assertEquals('.', source.charAt(tree.skipUntilAfterSymbol(0, "some")));
        assertEquals('M', source.charAt(tree.skipUntilAfterSymbol(0, "test")));
        assertEquals(';', source.charAt(tree.skipUntilAfterSymbol(0, ")")));
        assertEquals(-1, tree.skipUntilAfterSymbol(0, "something"));
        assertEquals(-1, tree.skipUntilAfterSymbol(0, ";"));
    }
    
    @Test
    public void testNextIndexOf(){
        String source = "some.testMethod(String thing);";
        tree = new BaseParseTree(source);
        assertEquals(-1, tree.nextIndexOfCharFromIndex(';', -1));
        assertEquals(-1, tree.nextIndexOfCharFromIndex(';', 30));
        assertEquals(-1, tree.nextIndexOfCharFromIndex('z', 0));
        
        assertEquals(0, tree.nextIndexOfCharFromIndex('M',0));
    }

    @Test
    public void testIsCurrentSymbol_int_String() {
        String source = "some.testMethod(String thing);";
        tree = new BaseParseTree(source);
        assertFalse(tree.isCurrentSymbol(-1, "s"));
        
        assertTrue(tree.isCurrentSymbol(0, "some"));
        assertTrue(tree.isCurrentSymbol(4, "."));
        assertTrue(tree.isCurrentSymbol(5, "test"));
        
        assertFalse(tree.isCurrentSymbol(1, "some"));
        assertFalse(tree.isCurrentSymbol(5, "."));
        assertFalse(tree.isCurrentSymbol(6, "test"));
        
        assertFalse(tree.isCurrentSymbol(29, "thing"));
    }

    @Test
    public void testIsCurrentSymbol_int_char() {
        String source = "some.testMethod(String thing);";
        tree = new BaseParseTree(source);
        assertFalse(tree.isCurrentSymbol(-1, 's'));
        
        assertTrue(tree.isCurrentSymbol(0, 's'));
        assertTrue(tree.isCurrentSymbol(4, '.'));
        assertTrue(tree.isCurrentSymbol(5, 't'));
        assertTrue(tree.isCurrentSymbol(29, ';'));
        
        assertFalse(tree.isCurrentSymbol(1, 'e'));
        assertFalse(tree.isCurrentSymbol(5, '.'));
        assertFalse(tree.isCurrentSymbol(6, 'M'));
        
        assertFalse(tree.isCurrentSymbol(29, ')'));
    }

    @Test
    public void testNextNonWhiteCharFrom() {
        String source = "01 23    456";
        tree = new BaseParseTree(source);
        assertEquals('\0', tree.nextNonWhiteCharFrom(-1));
        assertEquals('\0', tree.nextNonWhiteCharFrom(12));
        
        assertEquals('0', tree.nextNonWhiteCharFrom(0));
        assertEquals('1', tree.nextNonWhiteCharFrom(1));
        assertEquals('2', tree.nextNonWhiteCharFrom(2));
        assertEquals('2', tree.nextNonWhiteCharFrom(3));
        assertEquals('3', tree.nextNonWhiteCharFrom(4));
        assertEquals('4', tree.nextNonWhiteCharFrom(5));
        assertEquals('4', tree.nextNonWhiteCharFrom(6));
        assertEquals('4', tree.nextNonWhiteCharFrom(7));
        assertEquals('5', tree.nextNonWhiteCharFrom(10));
    }
    
    @Test
    public void testIgnoreComments(){
        String source = "//some single line comment text\n someStatement();";
        
        source = "/*some multiple line\ncomment text*/someStatement();";
        fail();
    }
    
    @Test
    public void testNonWhiteCharFromWithNewLineAndTabs(){
        String source = "01 34  \n8  \t2345";
        tree = new BaseParseTree(source);
        
        assertEquals('3', tree.nextNonWhiteCharFrom(3));
        assertEquals('8', tree.nextNonWhiteCharFrom(5));
        assertEquals('2', tree.nextNonWhiteCharFrom(9));
    }

    @Test
    public void testSourceFrom() {
        String source = "some.testMethod(String thing);";
        tree = new BaseParseTree(source);
        
        assertNull(tree.sourceFrom(-1));
        assertNull(tree.sourceFrom(30));
        
        assertEquals(".testMethod(String thing);", tree.sourceFrom(4));
        assertEquals("Method(String thing);", tree.sourceFrom(9));
    }

    @Test
    public void testSourceTo() {
        String source = "some.testMethod(String thing);";
        tree = new BaseParseTree(source);
        
        assertNull(tree.sourceTo(-1));
        assertNull(tree.sourceTo(30));
        
        assertEquals("some.testMethod(String", tree.sourceTo(22));
        assertEquals("some.test", tree.sourceTo(9));
    }

    @Test
    public void testSourceFromTo() {
        String source = "some.testMethod(String thing);";
        tree = new BaseParseTree(source);
        
        assertNull(tree.sourceFromTo(-1, 31));
        
        assertEquals("some.testMethod(String", tree.sourceFromTo(0, 22));
        assertEquals("some.test", tree.sourceFromTo(0, 9));
        
        assertEquals("testMethod(String thing);", tree.sourceFromTo(5, 30));
        assertEquals("Method(String thing);", tree.sourceFromTo(9, 30));
        
        assertEquals("estMethod", tree.sourceFromTo(6, 15));
    }
    
    @Test
    public void testPreviousCharFromIndex(){
        fail();
    }
    
    
    @Test
    public void testAdvanceToNextNonWhiteChar(){
        String source = "some    thing";
        tree = new BaseParseTree(source);
        
        assertEquals(-1, tree.advanceToNextNonWhiteCharFrom(-1));
        assertEquals(1, tree.advanceToNextNonWhiteCharFrom(0));
        assertEquals(8, tree.advanceToNextNonWhiteCharFrom(3));
        assertEquals(8, tree.advanceToNextNonWhiteCharFrom(4));
        assertEquals(8, tree.advanceToNextNonWhiteCharFrom(5));
        assertEquals(8, tree.advanceToNextNonWhiteCharFrom(6));
        assertEquals(-1, tree.advanceToNextNonWhiteCharFrom(13));
    }
    
    @Test
    public void testIsNextSymbolFromIndex(){
        String source = "if else   if \n  something \t else";
        tree = new BaseParseTree(source);
        
        assertFalse(tree.isNextSymbolFromIndex("else", 0));
        assertTrue(tree.isNextSymbolFromIndex("else", 1));
        assertTrue(tree.isNextSymbolFromIndex("if", 6));
        assertTrue(tree.isNextSymbolFromIndex("something", 11));
        assertTrue(tree.isNextSymbolFromIndex("else", 27));
    }
}
