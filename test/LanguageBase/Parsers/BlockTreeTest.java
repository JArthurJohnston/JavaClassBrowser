/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Internal.BaseTest;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class BlockTreeTest extends BaseTest{
    private BlockTree parser;
    
    public BlockTreeTest() {
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
    public void testConstructor() throws Exception{
        String source = "";
        parser = new BlockTree(source);
        assertTrue(parser.stack.isEmpty());
        assertEquals(source, parser.source);
        assertTrue(parser.getErrors().isEmpty());
        
        assertEquals(parser, parser.getRootNode().getTree());
        assertEquals(parser.getRootNode(), 
                this.getVariableFromClass(parser, "currentNode"));
        assertTrue(parser.getRootNode().getStatements().isEmpty());
        assertEquals(1, parser.getBlocks().size());
        
        assertEquals(1, parser.getLineCount());
    }
    
    @Test
    public void testParserLineCount() throws Exception{
        String source = "someMethod(); \n someOtherMethod();";
        parser = new BlockTree(source);
        assertEquals(2, parser.getLineCount());
    }
    
    @Test
    public void testOpenStack() throws Exception{
        parser = new BlockTree("{");
        assertTrue(parser.stack.peek("{"));
        parser = new BlockTree("(");
        assertTrue(parser.stack.peek("("));
        parser = new BlockTree("[");
        assertTrue(parser.stack.peek("["));
    }
    
    @Test
    public void testStackCloses() throws Exception {
        parser = new BlockTree("{}");
        assertTrue(parser.stack.isEmpty());
        parser = new BlockTree("()");
        assertTrue(parser.stack.isEmpty());
        parser = new BlockTree("[]");
        assertTrue(parser.stack.isEmpty());
    }
    
    @Test
    public void testParseMethodStatement() throws Exception{
        String source = "someMethod();";
        parser = new BlockTree(source);
        assertEquals(1, parser.getRootNode().getStatements().size());
    }
    
}
