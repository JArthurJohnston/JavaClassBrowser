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
        
    }

    @Test
    public void testSkipUntilAfterSymbol() {
    }

    @Test
    public void testIsCurrentSymbol_int_String() {
    }

    @Test
    public void testIsCurrentSymbol_int_char() {
    }

    @Test
    public void testNextNonWhiteCharFrom() {
    }

    @Test
    public void testSourceFrom() {
    }

    @Test
    public void testSourceTo() {
    }

    @Test
    public void testSourceFromTo() {
    }
}
