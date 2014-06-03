/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;

import LanguageBase.Parser.CodeSnippet;
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
        parser = new Parser();
    }
    
    @After
    public void tearDown() {
        parser = null;
    }

    /**
     * Test of parse method, of class Parser.
     */
    @Test
    public void testParse() {
        String source = "public class SomeClass {"
                    + "int x;"
                    + "char y;"
                    + "void someMethod(){}"
                    + "int someOtherMethod(){"
                        +  "x = 5;"
                    + "}"
                    + "}";
        
        parser.setSource(source);
        parser.parse();
        assertEquals(1, parser.getSegments().size());
        CodeSnippet root = parser.getSegments().getFirst();
    }
    
}
