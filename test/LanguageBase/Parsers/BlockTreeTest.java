/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Internal.BaseTest;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                this.getVariableFromClass(parser, "currentBlock"));
        assertTrue(parser.getRootNode().getStatements().isEmpty());
        assertFalse(parser.getRootNode().isSingleStatement());
        assertEquals(1, parser.getBlocks().size());
        
        assertEquals(1, parser.getLineCount());
        assertNull(this.getVariableFromClass(parser, "currentStatement"));
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
    public void testStackThrowsException(){
        for(String symbol : new String[]{"}", ")", "]",})
            try {
                parser = new BlockTree(symbol);
                fail("Exception not thrown");
            } catch (BaseParseTree.ParseException ex) {
                this.compareStrings("Parse error at line: 1", ex.getMessage());
            }
    }
    
    @Test
    public void testParseMethodStatement() throws Exception{
        String source = "someMethod();";
        parser = new BlockTree(source);
        assertEquals(1, parser.getRootNode().getStatements().size());
        
        StatementNode statement = parser.getRootNode().getStatements().getFirst();
        assertSame(statement, 
                this.getVariableFromClass(parser, "currentStatement"));
        
        assertSame(parser.getRootNode(), statement.getParentBlock());
        assertSame(parser, statement.getTree());
        assertNull(this.getVariableFromClass(statement, "childBlock"));
        this.compareStrings(source, statement.getSource());
    }
    
    @Test
    public void testStatementGetSource() throws Exception{
        String source = "someMethod();";
        parser = new BlockTree(source);
        StatementNode statement = parser.getRootNode().getStatements().getFirst();
        assertEquals(source, statement.getSource());
        
        source = "\nsomeMethod();";
        parser = new BlockTree(source);
        statement = parser.getRootNode().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\nsomeMethod();\n";
        parser = new BlockTree(source);
        statement = parser.getRootNode().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\t\nsomeMethod();\n";
        parser = new BlockTree(source);
        statement = parser.getRootNode().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\t\nsomeMethod();\t\n\t";
        parser = new BlockTree(source);
        statement = parser.getRootNode().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\t \nsomeMethod(); \t\n \t";
        parser = new BlockTree(source);
        statement = parser.getRootNode().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
    }
    
    @Test
    public void testParseStatementWithBlock() throws Exception{
        String source = "someMethod(){\n"
                + "someStatement();"
                + "}";
        parser = new BlockTree(source);
        
        StatementNode statement = parser.getRootNode().getStatements().getFirst();
        BlockNode block = statement.getChildBlock();
        assertSame(statement, block.getParentStatement());
        assertEquals(1, block.getStatements().size());
        assertFalse(block.isSingleStatement());
        
        this.compareStrings("someMethod()", statement.getSource());
        statement = block.getStatements().getFirst();
        this.compareStrings("someStatement();", statement.getSource());
    }
    
    @Test
    public void testParseSimgleStatementBlock() throws Exception{
        String source = "someMethod()\n"
                + "someStatement();";
        parser = new BlockTree(source);
        
        assertEquals(1, parser.getRootNode().getStatements().size());
        assertFalse(parser.getRootNode().isSingleStatement());
        
        StatementNode statement = parser.getRootNode().getStatements().getFirst();
        //this.compareStrings("someMethod()", statement.getSource());
        
        BlockNode block = statement.getChildBlock();
        assertEquals(1, block.getStatements().size());
    }
    
}
