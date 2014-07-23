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
    
    private BlockNode verifyAndGetChildBlockFromStatement(StatementNode aStatement){
        //errors if the block is null and asserts its the right class
        //since BlockNodes are lazy-initialized
        assertSame(BlockNode.class, 
                this.getVariableFromClass(aStatement, "childBlock").getClass());
        return aStatement.getChildBlock();
    }

    @Test
    public void testConstructor() throws Exception{
        String source = "";
        parser = new BlockTree(source);
        assertTrue(parser.stack.isEmpty());
        assertEquals(source, parser.source);
        assertTrue(parser.getErrors().isEmpty());
        
        assertEquals(parser, parser.getRootBlock().getTree());
        assertEquals(parser.getRootBlock(), 
                this.getVariableFromClass(parser, "currentBlock"));
        assertTrue(parser.getRootBlock().getStatements().isEmpty());
        assertFalse(parser.getRootBlock().isSingleStatement());
        assertEquals(1, parser.getBlocks().size());
        
        assertEquals(1, parser.getLineCount());
        assertNull(this.getVariableFromClass(parser, "currentStatement"));
        
        assertTrue(parser.getRootBlock().isRoot());
        assertEquals(parser.getRootBlock(), parser.getRootBlock().getParentBlock());
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
        assertEquals(1, parser.getRootBlock().getStatements().size());
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        assertSame(statement, 
                this.getVariableFromClass(parser, "currentStatement"));
        
        assertSame(parser.getRootBlock(), statement.getParentBlock());
        assertSame(parser, statement.getTree());
        assertNull(this.getVariableFromClass(statement, "childBlock"));
        this.compareStrings(source, statement.getSource());
    }
    
    @Test
    public void testStatementGetSource() throws Exception{
        String source = "someMethod();";
        parser = new BlockTree(source);
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals(source, statement.getSource());
        
        source = "\nsomeMethod();";
        parser = new BlockTree(source);
        statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\nsomeMethod();\n";
        parser = new BlockTree(source);
        statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\t\nsomeMethod();\n";
        parser = new BlockTree(source);
        statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\t\nsomeMethod();\t\n\t";
        parser = new BlockTree(source);
        statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\t \nsomeMethod(); \t\n \t";
        parser = new BlockTree(source);
        statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
    }
    
    @Test
    public void testParseStatementWithBlock() throws Exception{
        String source = "someMethod(){\n"
                + "someStatement();"
                + "}";
        parser = new BlockTree(source);
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        BlockNode block = statement.getChildBlock();
        assertSame(statement, block.getParentStatement());
        assertEquals(1, block.getStatements().size());
        assertFalse(block.isSingleStatement());
        
        this.compareStrings("someMethod()", statement.getSource());
        statement = block.getStatements().getFirst();
        this.compareStrings("someStatement();", statement.getSource());
    }
    
    @Test
    public void testParseSingleStatementBlock() throws Exception{
        String source = "someMethod()\n"
                + "someStatement();";
        parser = new BlockTree(source);
        
        assertEquals(1, parser.getRootBlock().getStatements().size());
        assertFalse(parser.getRootBlock().isSingleStatement());
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("someMethod()", statement.getSource());
        
        BlockNode block = statement.getChildBlock();
        assertEquals(1, block.getStatements().size());
        
        statement = block.getStatements().getFirst();
        this.compareStrings("someStatement();", statement.getSource());
    }
    
    @Test
    public void testSingleStatementBlockWithMultipleStatements() throws Exception{
        String source = "someMethod()\n"
                + "someStatement();"
                + "someOtherStatement();"
                + "someObject.someField;";
        parser = new BlockTree(source);
        
        assertEquals(1, parser.getRootBlock().getStatements().size());
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("someMethod()", statement.getSource());
        
        BlockNode block = statement.getChildBlock();
        assertTrue(block.isSingleStatement());
        assertEquals(parser.getRootBlock(), block.getParentBlock());
        assertEquals(3, block.getStatements().size());
        
        String[] expected = new String[]{
                "someStatement();"
                ,"someOtherStatement();"
                ,"someObject.someField;"};
        for(int i = 0; i < 3; i++){
            StatementNode blockStatement = block.getStatements().get(i);
            assertEquals(null, this.getVariableFromClass(blockStatement, "childBlock"));
            this.compareStrings(expected[i], blockStatement.getSource());
        }
    }
    
    @Test
    public void testNestedStatements() throws Exception{
        String source = "if(something().somethingElse()){"
                + "doSomething();"
                + "}";
        parser = new BlockTree(source);
        assertEquals(1, parser.getRootBlock().getStatements().size());
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("if(something().somethingElse())", statement.getSource());
        BlockNode block = statement.getChildBlock();
        assertEquals(1, block.getStatements().size());
        this.compareStrings("doSomething();", block.getStatements().getFirst().getSource());
    }
    
    private void verifySimpleStatement(String a, String b){
        assertEquals(1, parser.getRootBlock().getStatements().size());
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings(a, statement.getSource());
        BlockNode block = this.verifyAndGetChildBlockFromStatement(statement);
        assertEquals(1, block.getStatements().size());
        statement = block.getStatements().getFirst();
        this.compareStrings(b, statement.getSource());
    }
    
    @Test
    public void testForLoops() throws Exception{
        String source = "for(some;set;ofStatements){"
                + "someMethod().someOtherMethod();"
                + "}";
        parser = new BlockTree(source);
        this.verifySimpleStatement("for(some;set;ofStatements)", 
                "someMethod().someOtherMethod();");
    }
    
    @Test
    public void testSingleStatementForLoop() throws Exception{
        String source = "for(some;set;ofStatements)"
                + "someMethod().someOtherMethod();";
        parser = new BlockTree(source);
        this.verifySimpleStatement("for(some;set;ofStatements)", 
                "someMethod().someOtherMethod();");
        BlockNode block = parser
                .getRootBlock()
                    .getStatements()
                        .getFirst()
                            .getChildBlock();
        assertTrue(block.isSingleStatement());
    }
    
    @Test
    public void testWhileLoop() throws Exception{
        String source = "while(someObject.someBoolean()){"
                + "someMethod().someOtherMethod();"
                + "}";
        parser = new BlockTree(source);
        this.verifySimpleStatement("while(someObject.someBoolean())", 
                "someMethod().someOtherMethod();");
    }
    
    @Test
    public void testSingleStatementWhileLoop() throws Exception{
        String source = "while(someObject.someBoolean())"
                + "someMethod().someOtherMethod();";
        parser = new BlockTree(source);
        this.verifySimpleStatement("while(someObject.someBoolean())", 
                "someMethod().someOtherMethod();");
        BlockNode block = parser
                .getRootBlock()
                    .getStatements()
                        .getFirst()
                            .getChildBlock();
        assertTrue(block.isSingleStatement());
    }
    
    
    @Test
    public void testIfStatement() throws Exception{
        String source = "if(someObject.someBoolean()){"
                + "someMethod().someOtherMethod();"
                + "}";
        parser = new BlockTree(source);
        this.verifySimpleStatement("if(someObject.someBoolean())", 
                "someMethod().someOtherMethod();");
    }
    
    @Test
    public void testSingleStatementIfStatement() throws Exception{
        String source = "if(someObject.someBoolean())"
                + "someMethod().someOtherMethod();";
        parser = new BlockTree(source);
        this.verifySimpleStatement("if(someObject.someBoolean())", 
                "someMethod().someOtherMethod();");
        BlockNode block = parser
                .getRootBlock()
                    .getStatements()
                        .getFirst()
                            .getChildBlock();
        assertTrue(block.isSingleStatement());
    }
    
    @Test
    public void testIfElse() throws Exception{
        String source = "if(someObject.someBoolean()){"
                + "someMethod().someOtherMethod();"
                + "} else {"
                + "someObject.someMethod();"
                + "}";
        parser = new BlockTree(source);
        
        assertEquals(2, parser.getRootBlock().getStatements().size());
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("if(someObject.someBoolean())", statement.getSource());
        BlockNode block = statement.getChildBlock();
        assertFalse(block.isSingleStatement());
        assertEquals(1, block.getStatements().size());
        this.compareStrings("someMethod().someOtherMethod();", 
                block.getStatements().getFirst().getSource());
        
        statement = parser.getRootBlock().getStatements().getLast();
        this.compareStrings("else", statement.getSource());
        block = statement.getChildBlock();
        assertFalse(block.isSingleStatement());
        assertEquals(1, block.getStatements().size());
        this.compareStrings("someObject.someMethod();", 
                block.getStatements().getFirst().getSource());
    }
    
}
