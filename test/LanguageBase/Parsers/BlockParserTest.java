/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class BlockParserTest extends BaseParserTest{
    private BlockParser parser;
    
    public BlockParserTest() {
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
        parser = new BlockParser(source);
        assertTrue(parser.stack.isEmpty());
        assertEquals(source, parser.source);
        assertTrue(parser.getErrors().isEmpty());
        
        assertEquals(parser, parser.getRootBlock().getTree());
        assertEquals(parser.getRootBlock(), 
                this.getVariableFromClass(parser, "currentBlock"));
        assertEquals(1, parser.getRootBlock().getStatements().size());
        assertFalse(parser.getRootBlock().isSingleStatement());
        assertEquals(1, parser.getBlocks().size());
        
        assertEquals(1, parser.getLineCount());
        assertEquals("",parser.getRootBlock().getStatements().getFirst().getSource());
        
        assertTrue(parser.getRootBlock().isRoot());
        assertEquals(parser.getRootBlock(), parser.getRootBlock().getParentBlock());
        
        assertTrue(parser.getReferences().isEmpty());
    }
    
    @Test
    public void testParserLineCount() throws Exception{
        String source = "someMethod(); \n someOtherMethod();";
        parser = new BlockParser(source);
        assertEquals(2, parser.getLineCount());
    }
    
    @Test
    public void testOpenStack() throws Exception{
        parser = new BlockParser("{");
        assertTrue(parser.stack.peek("{"));
        parser = new BlockParser("(");
        assertTrue(parser.stack.peek("("));
        parser = new BlockParser("[");
        assertTrue(parser.stack.peek("["));
    }
    
    @Test
    public void testStackCloses() throws Exception {
        parser = new BlockParser("{}");
        assertTrue(parser.stack.isEmpty());
        parser = new BlockParser("()");
        assertTrue(parser.stack.isEmpty());
        parser = new BlockParser("[]");
        assertTrue(parser.stack.isEmpty());
    }
    
    @Test
    public void testStackThrowsException(){
        for(String symbol : new String[]{"}", ")", "]",})
            try {
                parser = new BlockParser(symbol);
                fail("Exception not thrown");
            } catch (BaseParseTree.ParseException ex) {
                this.compareStrings("Parse error at line: 1", ex.getMessage());
            }
    }
    
    @Test
    public void testParseMethodStatement() throws Exception{
        String source = "someMethod();";
        parser = new BlockParser(source);
        this.verifyBlockStatements(parser.getRootBlock(), 1);
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        
        assertSame(parser.getRootBlock(), statement.getParentBlock());
        assertSame(parser, statement.getTree());
        assertNull(this.getVariableFromClass(statement, "childBlock"));
        this.compareStrings(source, statement.getSource());
    }
    
    @Test
    public void testStatementGetSource() throws Exception{
        String source = "someMethod();";
        parser = new BlockParser(source);
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals(source, statement.getSource());
        
        source = "\nsomeMethod();";
        parser = new BlockParser(source);
        statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\nsomeMethod();\n";
        parser = new BlockParser(source);
        statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\t\nsomeMethod();\n";
        parser = new BlockParser(source);
        statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\t\nsomeMethod();\t\n\t";
        parser = new BlockParser(source);
        statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
        
        source = "\t \nsomeMethod(); \t\n \t";
        parser = new BlockParser(source);
        statement = parser.getRootBlock().getStatements().getFirst();
        assertEquals("someMethod();", statement.getSource());
    }
    
    @Test
    public void testParseStatementWithBlock() throws Exception{
        String source = "someMethod(){\n"
                + "someStatement();"
                + "}";
        parser = new BlockParser(source);
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.verifyBlockStatements(parser.getRootBlock(), 1);
        BlockNode block = statement.getChildBlock();
        assertSame(statement, block.getParentStatement());
        assertEquals(2, block.getStatements().size());
        assertEquals("", block.getStatements().getLast().getSource());
        assertFalse(block.isSingleStatement());
        
        this.compareStrings("someMethod()", statement.getSource());
        statement = block.getStatements().getFirst();
        this.compareStrings("someStatement();", statement.getSource());
    }
    
    @Test
    public void testParseSingleStatementBlock() throws Exception{
        String source = "someMethod()\n"
                + "someStatement();";
        parser = new BlockParser(source);
        
        this.verifyBlockStatements(parser.getRootBlock(), 1);
        assertFalse(parser.getRootBlock().isSingleStatement());
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("someMethod()", statement.getSource());
        
        BlockNode block = statement.getChildBlock();
        this.verifyBlockStatements(block, 1);
        
        statement = block.getStatements().getFirst();
        this.compareStrings("someStatement();", statement.getSource());
    }
    
    @Test
    public void testSingleStatementBlockWithMultipleStatementBlock() throws Exception{
        String source = "if(someMethod())\n"
                        + "if(someStatement()){"
                            + "someOtherStatement();"
                            + "someObject.someField;"
                        + "}";
        parser = new BlockParser(source);
        
        this.verifyBlockStatements(parser.getRootBlock(), 1);
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("if(someMethod())", statement.getSource());
        
        BlockNode block = statement.getChildBlock();
        assertTrue(block.isSingleStatement());
        this.verifyBlockStatements(block, 1);
        statement = block.getStatements().getFirst();
        this.compareStrings("if(someStatement())", statement.getSource());
        
        block = statement.getChildBlock();
        this.verifyBlockStatements(block, 2);
        this.compareStrings("someOtherStatement();",block.getStatements().getFirst().getSource());
        this.compareStrings("someObject.someField;",block.getStatements().get(1).getSource());
    }
    
    @Test
    public void testNestedSingleStatementBlocks() throws Exception{
        String source = "if(someBoolean)"
                        + "if(someMethod().someBooleanMethod())"
                            + "doSomething();";
        parser = new BlockParser(source);
        this.verifyBlockStatements(parser.getRootBlock(), 1);
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("if(someBoolean)", statement.getSource());
        
        BlockNode block = statement.getChildBlock();
        this.verifyBlockStatements(block, 1);
        
        statement = block.getStatements().getFirst();
        this.compareStrings("if(someMethod().someBooleanMethod())", statement.getSource());
        
        block = statement.getChildBlock();
        this.verifyBlockStatements(block, 1);
        
        statement = block.getStatements().getFirst();
        this.compareStrings("doSomething();", statement.getSource());
    }
    
    @Test
    public void testNestedStatements() throws Exception{
        String source = "if(something().somethingElse()){"
                + "doSomething();"
                + "}";
        parser = new BlockParser(source);
        this.verifyBlockStatements(parser.getRootBlock(), 1);
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("if(something().somethingElse())", statement.getSource());
        BlockNode block = statement.getChildBlock();
        this.verifyBlockStatements(block, 1);
        this.compareStrings("doSomething();", block.getStatements().getFirst().getSource());
    }
    
    private void verifySimpleStatement(String a, String b){
        this.verifyBlockStatements(parser.getRootBlock(), 1);
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings(a, statement.getSource());
        BlockNode block = this.verifyAndGetChildBlockFromStatement(statement);
        this.verifyBlockStatements(block, 1);
        statement = block.getStatements().getFirst();
        this.compareStrings(b, statement.getSource());
    }
    
    @Test
    public void testForLoops() throws Exception{
        String source = "for(some;set;ofStatements){"
                + "someMethod().someOtherMethod();"
                + "}";
        parser = new BlockParser(source);
        this.verifySimpleStatement("for(some;set;ofStatements)", 
                "someMethod().someOtherMethod();");
    }
    
    @Test
    public void testSingleStatementForLoop() throws Exception{
        String source = "for(some;set;ofStatements)"
                + "someMethod().someOtherMethod();";
        parser = new BlockParser(source);
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
        parser = new BlockParser(source);
        this.verifySimpleStatement("while(someObject.someBoolean())", 
                "someMethod().someOtherMethod();");
    }
    
    @Test
    public void testSingleStatementWhileLoop() throws Exception{
        String source = "while(someObject.someBoolean())"
                + "someMethod().someOtherMethod();";
        parser = new BlockParser(source);
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
        parser = new BlockParser(source);
        this.verifySimpleStatement("if(someObject.someBoolean())", 
                "someMethod().someOtherMethod();");
    }
    
    @Test
    public void testSingleStatementIfStatement() throws Exception{
        String source = "if(someObject.someBoolean())"
                + "someMethod().someOtherMethod();";
        parser = new BlockParser(source);
        this.verifySimpleStatement("if(someObject.someBoolean())", 
                "someMethod().someOtherMethod();");
        BlockNode block = parser
                .getRootBlock()
                    .getStatements()
                        .getFirst()
                            .getChildBlock();
        assertTrue(block.isSingleStatement());
    }

    private void verifyIfElseStatement(boolean singleStatements) {
        this.verifyBlockStatements(parser.getRootBlock(), 2);
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("if(someObject.someBoolean())", statement.getSource());
        BlockNode block = statement.getChildBlock();
        assertTrue(block.isSingleStatement() == singleStatements);
        this.verifyBlockStatements(block, 1);
        this.compareStrings("someMethod().someOtherMethod();", 
                block.getStatements().getFirst().getSource());
        
        statement = parser.getRootBlock().getStatements().get(1);
        this.compareStrings("else", statement.getSource());
        block = statement.getChildBlock();
        assertTrue(block.isSingleStatement() == singleStatements);
        this.verifyBlockStatements(block, 1);
        this.compareStrings("someObject.someMethod();", 
                block.getStatements().getFirst().getSource());
    }
    
    @Test
    public void testIfElse() throws Exception{
        String source = "if(someObject.someBoolean()){"
                + "someMethod().someOtherMethod();"
                + "} else {"
                + "someObject.someMethod();"
                + "}";
        parser = new BlockParser(source);
        verifyIfElseStatement(false);
        
        source = "if(someObject.someBoolean())"
                    + "someMethod().someOtherMethod();"
                + "else "
                    + "someObject.someMethod();";
        parser = new BlockParser(source);
        verifyIfElseStatement(true);
    }
    
    @Test
    public void testParseIfElseIfStatement() throws Exception{
        String source = "if(something){"
                + "doSomething();"
                + "}else if(somethingElse){"
                + "doSomethingElse();"
                + "}";
        parser = new BlockParser(source);
        verifyIfElseIfStatement(false);
        
        source = "if(something)"
                + "doSomething();"
                + "else if(somethingElse)"
                + "doSomethingElse();";
        parser = new BlockParser(source);
        verifyIfElseIfStatement(true);
    }

    private void verifyIfElseIfStatement(boolean isSingleStatement) {
        this.verifyBlockStatements(parser.getRootBlock(), 2);
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("if(something)", statement.getSource());
        
        BlockNode block = statement.getChildBlock();
        this.verifyBlockStatements(block, 1);
        assertTrue(block.isSingleStatement() == isSingleStatement);
        
        statement = block.getStatements().getFirst();
        this.compareStrings("doSomething();", statement.getSource());
        
        statement = parser.getRootBlock().getStatements().get(1);
        this.compareStrings("else if(somethingElse)", statement.getSource());
        
        block = statement.getChildBlock();
        this.verifyBlockStatements(block, 1);
        assertTrue(block.isSingleStatement() == isSingleStatement);
        
        statement = block.getStatements().getFirst();
        this.compareStrings("doSomethingElse();", statement.getSource());
    }
    
    @Test
    public void testOpenStatement() throws Exception{
        String source = "if(someMethod()";
        parser = new BlockParser(source);
        
        assertEquals(1, parser.getRootBlock().getStatements().size());
        assertEquals(1, parser.getLineCount());
        
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        assertTrue(statement.isOpen());
        
        source = "if(someMethod())";
        parser = new BlockParser(source);
        
        assertEquals(1, parser.getRootBlock().getStatements().size());
        
        statement = parser.getRootBlock().getStatements().getFirst();
        assertFalse(statement.isOpen());
        BlockNode block = this.verifyAndGetChildBlockFromStatement(statement);
        assertEquals(1, block.getStatements().size());
        assertEquals("", block.getStatements().getFirst().getSource());
    }
    
    private void verifyDoWhileLoop(){
        this.verifyBlockStatements(parser.getRootBlock(), 2);
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("do", statement.getSource());
        
        BlockNode block = statement.getChildBlock();
        this.verifyBlockStatements(block, 1);
        
        statement = block.getStatements().getFirst();
        this.compareStrings("someStatement();", statement.getSource());
        
        statement = parser.getRootBlock().getStatements().get(1);
        this.compareStrings("while(someBoolean());", statement.getSource());
    }
    
    @Test
    public void testParseDoWhileLoop() throws Exception {
        String source = "do{"
                            + "someStatement();"
                        + "}while(someBoolean());";
        parser = new BlockParser(source);
        this.verifyDoWhileLoop();
        
        source = "do "
                    + "someStatement();"
                + "while(someBoolean());";
        parser = new BlockParser(source);
        this.verifyDoWhileLoop();
    }
    
    @Test
    public void testParseComments() throws Exception{
        String source = "do{ //this comment\n"
                            + "someStatement();"
                        + "}while(someBoolean());";
        parser = new BlockParser(source);
        
        this.verifyBlockStatements(parser.getRootBlock(), 2);
        StatementNode statement = parser.getRootBlock().getStatements().getFirst();
        this.compareStrings("do", statement.getSource());
        
        BlockNode block = statement.getChildBlock();
        this.verifyBlockStatements(block, 2);
        
        statement = block.getStatements().getFirst();
        this.compareStrings("//this comment", statement.getSource());
        statement = block.getStatements().get(1);
        this.compareStrings("someStatement();", statement.getSource());
        
        statement = parser.getRootBlock().getStatements().get(1);
        this.compareStrings("while(someBoolean());", statement.getSource());
    }
    
    @Test
    public void testCommentOnSameLineAsStatement() throws Exception{
        String source = "do{"
                            + "someStatement();//this comment\n"
                        + "}while(someBoolean());";
        parser = new BlockParser(source);
        this.verifyBlockStatements(parser.getRootBlock(), 2);
        
        BlockNode block = parser.getRootBlock().getStatements().getFirst().getChildBlock();
        this.verifyBlockStatements(block, 2);
        
        this.compareStrings("someStatement();", block.getStatements().get(0).getSource());
        this.compareStrings("//this comment", block.getStatements().get(1).getSource());
        assertEquals(2, parser.getLineCount());
    }
    
    @Test
    public void testMultiLineComment() throws Exception{
        String source = "do{"
                            + "someStatement(); /*someComment*/" 
                        + "}while(someBoolean());";
        parser = new BlockParser(source);
        this.verifyBlockStatements(parser.getRootBlock(), 2);
        
    }
    
    private void setUpTestProject() throws Exception{
        main = new MainApplication();
        super.setUpProjectAndPackage();
        ClassModel aClass = parentPackage.addClass(new ClassModel("ClassOne"));
        aClass.addMethod(new MethodModel("methodOne"));
        aClass.addMethod(new MethodModel("methodTwo"));
        aClass = parentPackage.addClass(new ClassModel("ClassTwo"));
        aClass.addMethod(new MethodModel("methodOne"));
        aClass.addMethod(new MethodModel("methodTwoOnClassTwo"));
        aClass.addMethod(new MethodModel("methodThree"));
    }
    
    @Test
    public void testParseFromModel() throws Exception{
        this.setUpTestProject();
        MethodModel aMethod = parentProject.findClass("ClassOne").methods.getFirst();
        this.compareStrings("void methodOne(){\n\n}", aMethod.toSourceString());
        
        parser = new BlockParser(aMethod);
        assertSame(aMethod, parser.getModel());
        assertTrue(parser.getReferences().isEmpty());
        String source = "do{"
                            + "this.methodTwo(); /*someComment*/" 
                        + "}while(ClassTwo.methodThree());";
        
        parser.parseSource(source);
        assertEquals(3, parser.getReferences().size());
    }
    
    /**
     * Tests I need to write
     * 
     * test skips string literals
     * test parse array declarations
     * test do-while
     * test references
     * test parses comments in single statement blocks
     * 
     * all of these need to be tested in single, and multi-line blocks
     */
    
}
