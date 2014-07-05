/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Exceptions.AlreadyExistsException;
import Exceptions.BaseException;
import Internal.BaseTest;
import LanguageBase.Parsers.Nodes.BlockNode;
import LanguageBase.Parsers.Nodes.StatementNode;
import Models.ClassModel;
import Models.MethodModel;
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
public class BlockParserTest extends BaseTest{
    private BlockParser parser;
    private BlockNode root;
    
    public BlockParserTest() {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        parser = null;
        root = null;
    }
    
    public void initializeParserWithSource(String source){
        parser = new BlockParser(source);
        root = parser.getTree();
        if(parser.getError() != null){
            fail(parser.getError());
        }
    }
    
    private void verifySimpleStatementWith(String a, String b){
        BlockNode block = root;
        assertEquals(1, block.getStatements().size());
        StatementNode statement = block.getStatements().getFirst();
        this.compareStrings(a, statement.getSource());
        block = statement.getChildBlock();
        assertEquals(1, block.getStatements().size());
        statement = block.getStatements().getFirst();
        this.compareStrings(b, statement.getSource());
    }

    @Test
    public void testParseSimpleStatements(){
        String source = "if(someBoolean()){"
                + "someMethod();"
                + "}";
        this.initializeParserWithSource(source);
        this.verifySimpleStatementWith("if(someBoolean())", "someMethod();");
        
        source = "for(some;stuff;here){"
                + "someMethod();"
                + "}";
        this.initializeParserWithSource(source);
        this.verifySimpleStatementWith("for(some;stuff;here)", "someMethod();");
        
        source = "while(someBoolean().someVar){"
                + "someMethod().someVar;"
                + "}";
        this.initializeParserWithSource(source);
        this.verifySimpleStatementWith("while(someBoolean().someVar)", 
                "someMethod().someVar;");
    }
    
    @Test
    public void testParseSimpleStatementsWithoutBrackets(){
        String source = "if(someBoolean())"
                + "someMethod();";
        this.initializeParserWithSource(source);
        this.verifySimpleStatementWith("if(someBoolean())", "someMethod();");
        
        source = "for(some;stuff;here)"
                + "someMethod();";
        this.initializeParserWithSource(source);
        this.verifySimpleStatementWith("for(some;stuff;here)", "someMethod();");
        
        source = "while(someBoolean().someVar)"
                + "someMethod().someVar;";
        this.initializeParserWithSource(source);
        this.verifySimpleStatementWith("while(someBoolean().someVar)", 
                "someMethod().someVar;");
    }
    
    private void verifyIfElse(){
        assertEquals(2, root.getStatements().size());
        StatementNode statement = root.getStatements().getFirst();
        this.compareStrings("if(someBoolean())", statement.getSource());
        BlockNode block = statement.getChildBlock();
        assertEquals(1, block.getStatements().size());
        statement = block.getStatements().getFirst();
        this.compareStrings("someMethod();", statement.getSource());
        assertNull(statement.getChildBlock());
        
        statement = root.getStatements().getLast();
        this.compareStrings("else", statement.getSource());
        block = statement.getChildBlock();
        assertEquals(1, block.getStatements().size());
        statement = block.getStatements().getFirst();
        this.compareStrings("someOtherMethod();", statement.getSource());
        assertNull(statement.getChildBlock());
    }
    
    @Test
    public void testParseIfElse(){
        String source = "if(someBoolean()){"
                            + "someMethod();"
                        + "} else {"
                            + "someOtherMethod();"
                        + "}";
        this.initializeParserWithSource(source);
        //once again without brackets
        this.verifyIfElse();
        this.compareStrings("if(someBoolean()) {\n"
                            + "\tsomeMethod();\n"
                        + "}else {\n"
                            + "\tsomeOtherMethod();\n"
                        + "}", parser.formattedSource());
        
        
        
        source = "if(someBoolean())"
                    + "someMethod();"
                + "else " //edit isCurrentSymbol to look for white spaces before and after the symbol
                    + "someOtherMethod();";
        this.initializeParserWithSource(source);
        this.verifyIfElse();
        this.compareStrings("if(someBoolean())\n"
                            + "\tsomeMethod();\n"
                        + "else\n"
                            + "\tsomeOtherMethod();\n", parser.formattedSource());
    }
    
    private void validateDoWhile(){
        assertEquals(2, root.getStatements().size());
        this.compareStrings("do", root.getStatements().getFirst().getSource());
        
        BlockNode block = root.getStatements().getFirst().getChildBlock();
        assertEquals(1, block.getStatements().size());
        this.compareStrings("someMethod();", block.getStatements().getFirst().getSource());
        
        this.compareStrings("while(someBoolean());", root.getStatements().getLast().getSource());
        assertNull(root.getStatements().getLast().getChildBlock());
    }
    
    @Test
    public void testDoWhile(){
        String source = "do{"
                        + "someMethod();"
                      + "}while(someBoolean());";
        this.initializeParserWithSource(source);
        this.validateDoWhile();
        this.compareStrings("do {\n"
                        + "\tsomeMethod();\n"
                      + "}while(someBoolean());\n", parser.formattedSource());
        
        source = "do"
                    + "someMethod();"
                + "while(someBoolean());";
        this.initializeParserWithSource(source);
        this.validateDoWhile();
        this.compareStrings("do\n"
                        + "\tsomeMethod();\n"
                      + "while(someBoolean());\n", parser.formattedSource());
    }
    
    @Test
    public void testTryCatch(){
        String source = "try{"
                            + "someMethod();"
                        + "} catch(someException(e)) {"
                            + "someOtherObject.someMethod();"
                        + "}";
        this.initializeParserWithSource(source);
        
        assertEquals(2, root.getStatements().size());
        this.compareStrings("try", root.getStatements().getFirst().getSource());
        
        BlockNode block = root.getStatements().getFirst().getChildBlock();
        assertEquals(1, block.getStatements().size());
        this.compareStrings("someMethod();", 
                block.getStatements().getFirst().getSource());
        
        this.compareStrings("catch(someException(e))", 
                root.getStatements().getLast().getSource());
        
        block = root.getStatements().getLast().getChildBlock();
        assertEquals(1, block.getStatements().size());
        this.compareStrings("someOtherObject.someMethod();", 
                block.getStatements().getFirst().getSource());
        this.compareStrings("try {\n"
                            + "\tsomeMethod();\n"
                        + "}catch(someException(e)) {\n"
                            + "\tsomeOtherObject.someMethod();\n"
                        + "}", parser.formattedSource());
    }
    
    @Test
    public void testMultiLineSingleStatement(){
        String source = "if(someBoolean())"
                        + "while(someBoolean()){"
                            + "someMethod();"
                            + "someOtherMethod();"
                        + "}";
        this.initializeParserWithSource(source);
        
        assertEquals(1, root.getStatements().size());
        this.compareStrings("if(someBoolean())", 
                root.getStatements().getFirst().getSource());
        
        BlockNode block = root.getStatements().getFirst().getChildBlock();
        assertEquals(1, block.getStatements().size());
        this.compareStrings("while(someBoolean())", 
                block.getStatements().getFirst().getSource());
        
        block = block.getStatements().getFirst().getChildBlock();
        assertEquals(2, block.getStatements().size());
        this.compareStrings("someMethod();", 
                block.getStatements().getFirst().getSource());
        this.compareStrings("someOtherMethod();", 
                block.getStatements().getLast().getSource());
        this.compareStrings("if(someBoolean())\n"
                        + "\twhile(someBoolean()) {\n"
                            + "\t\tsomeMethod();\n"
                            + "\t\tsomeOtherMethod();\n"
                        + "\t}", parser.formattedSource());
    }
    
    @Test
    public void testParseAnonymousClass(){
        String source = "someMethod(new AnonymClass{"
                + "someMethod();"
                + "});";
        this.initializeParserWithSource(source);
        
        assertEquals(2, root.getStatements().size());
        this.compareStrings("someMethod(new AnonymClass", 
                root.getStatements().getFirst().getSource());
        
        BlockNode block = root.getStatements().getFirst().getChildBlock();
        assertEquals(1, block.getStatements().size());
        this.compareStrings("someMethod();", 
                block.getStatements().getFirst().getSource());
        
        this.compareStrings(");", 
                root.getStatements().getLast().getSource());
        
        this.compareStrings("someMethod(new AnonymClass {\n"
                + "\tsomeMethod();\n"
                + "});\n", parser.formattedSource());
    }
    
    @Test
    public void testParseInnerClassWithMultipleStatments(){
        String source = "someMethod(new AnonymClass{"
                + "someMethod();"
                + "someOtherMethod();"
                + "});";
        this.initializeParserWithSource(source);
        this.compareStrings("someMethod(new AnonymClass {\n"
                + "\tsomeMethod();\n"
                + "\tsomeOtherMethod();\n"
                + "});\n", parser.formattedSource());
    }
    
    @Test
    public void testSingleStatementWithMultiLineBlock(){
        String source = "if(something)"
                + "while(somethingElse){"
                    + "doSomething();"
                    + "doAnotherThing();"
                + "}";
        this.initializeParserWithSource(source);
        this.compareStrings("if(something)\n"
                + "\twhile(somethingElse) {\n"
                + "\t\tdoSomething();\n"
                + "\t\tdoAnotherThing();\n"
                + "\t}", parser.formattedSource());
    }
    
    @Test
    public void testSingleStatementWithinSingleStatement(){
        String source = "if(something)"
                + "while(somethingElse){"
                    + "if(something);"
                        + "doAnotherThing();"
                + "}";
        this.initializeParserWithSource(source);
        this.compareStrings("if(something)\n"
                + "\twhile(somethingElse) {\n"
                    + "\t\tif(something);\n"
                        + "\t\t\tdoAnotherThing();\n"
                + "\t}", parser.formattedSource());
    }
    
    @Test
    public void testIfElseInSingleStatement(){
        String source = "if(something)"
                        + "if(someBoolean()){"
                            + "someMethod();"
                            + "someOtherMethod();"
                        + "} else { "
                            + "yetAnotherMethod();"
                        + "}";
        this.initializeParserWithSource(source);
        
        this.compareStrings("if(something)\n"
                        + "\tif(someBoolean()) {\n"
                            + "\t\tsomeMethod();\n"
                            + "\t\tsomeOtherMethod();\n"
                        + "\t} else { \n"
                            + "\t\tyetAnotherMethod();\n"
                        + "\t}", parser.formattedSource());
    }
    
    private String classSource(){
        return "private class Example {"
                    + "void someMethod(new TestClass {"
                        + "someInnerMethod();"
                    + "});"
                    + "void someOtherMethod(something aThing){"
                        + "if(someSingleBoolean)"
                            + "while(something){"
                                + "doSomething();"
                                + "if(aThing)"
                                    + "doSomethingElse();"
                            + "}"
                    + "}"
                + "}";
    }
    
    @Test
    public void testAllInAClass(){
        this.initializeParserWithSource(this.classSource());
        this.compareStrings("private class Example {\n"
                    + "\tvoid someMethod(new TestClass {\n"
                        + "\t\tsomeInnerMethod();\n"
                    + "\t});"
                    + "\tvoid someOtherMethod(something aThing){\n"
                        + "\t\tif(someSingleBoolean)\n"
                            + "\t\t\twhile(something){\n"
                                + "\t\t\t\tdoSomething();\n"
                                + "\t\t\t\t\tif(aThing)\n"
                                    + "\t\t\t\t\tdoSomethingElse();\n"
                            + "\t\t\t}\n"
                    + "\t}\n"
                + "}", parser.formattedSource());
    }
    
    private void setUpTestProject(){
        try {
            parentPackage.addClass(new ClassModel("TestClass"));
            ClassModel aClass = parentPackage.addClass(new ClassModel("SomeClass"));
            parentPackage.addClass(new ClassModel("RandomClass"));
            aClass.addClass(new ClassModel("SubClass"));
            parentPackage.addClass(new ClassModel("HelloWorldClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testGetSourceReferences(){
        
    }
}
