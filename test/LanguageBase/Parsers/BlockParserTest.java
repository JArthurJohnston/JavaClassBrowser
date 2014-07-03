/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Internal.BaseTest;
import LanguageBase.Parsers.Nodes.BlockNode;
import LanguageBase.Parsers.Nodes.StatementNode;
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
    public void setUp() {
    }
    
    @After
    public void tearDown() {
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
        source = "if(someBoolean())"
                    + "someMethod();"
                + "else " //edit isCurrentSymbol to look for white spaces before and after the symbol
                    + "someOtherMethod();";
        this.initializeParserWithSource(source);
        this.verifyIfElse();
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
        
        source = "do"
                    + "someMethod();"
                + "while(someBoolean());";
        this.initializeParserWithSource(source);
        this.validateDoWhile();
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
        
        this.compareStrings("someMethod(new AnonymClass{\n"
                + "\tsomeMethod();\n"
                + "});", parser.formattedSource());
    }
}
