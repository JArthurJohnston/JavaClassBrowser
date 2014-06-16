/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Internal.BaseTest;
import LanguageBase.Parsers.ScopeTree.Block;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ScopeTreeTest extends BaseTest{
    private ScopeTree tree;
    
    public ScopeTreeTest() {
    }
    
    @Before
    @Override
    public void setUp() {
    }
    
    @After
    @Override
    public void tearDown() {
        tree = null;
    }
    
    private String simpleTestClassSource(){
        return  "import.statement;"
                + "class SomeClass{"
                + "int var = 5;"
        + "}";
    }
    
    private String simpleMethodString(){
        return "void someMethod(){"
                + "int var = 6;"
                + "int varTwo = 7;"
                + "}";
    }
    
    private String simpleClassWithMethod(){
        return "import.statement;"
                +"class SomeClass {"
                + "int var = 6;"
                + this.simpleMethodString()
                +"}";
    }
    
    @Test
    public void testParseSource(){
        tree = new ScopeTree(this.simpleTestClassSource());
        
        assertEquals(2, tree.getChildren().size());
        this.compareStrings("import.statement;", tree.getChildren().getFirst().getSource());
        this.compareStrings(this.simpleTestClassSource(), tree.getSource());
        
        Block block = tree.getChildren().getLast();
        assertEquals("int var = 5;", block.getSource());
        assertEquals(1, block.getChildren().size());
        this.compareStrings("int var = 5;", block.getChildren().getFirst().getSource());
        this.compareStrings("class SomeClass", block.getDeclaration());
    }
    
    @Test
    public void testParseClassWithMethod(){
        tree = new ScopeTree(this.simpleClassWithMethod());
        assertEquals(2, tree.getChildren().size());
        this.compareStrings("import.statement;", tree.getChildren().getFirst().getSource());
        this.compareStrings(this.simpleClassWithMethod(), tree.getSource());
        
        Block block = tree.getChildren().getLast();
        assertEquals(2, block.getChildren().size());
        
        block = block.getChildren().getLast();
        this.compareStrings("void someMethod()", block.getDeclaration());
        assertEquals(2, block.getChildren().size());
        this.compareStrings("int var = 6;", block.getChildren().getFirst().getSource());
        this.compareStrings("int varTwo = 7;", block.getChildren().getLast().getSource());
    }
    
    @Test
    public void testParseWithStringLiteral(){
        fail();
    }
    
    @Test
    public void testParseWithComment(){
        fail();
    }
    
    @Test
    public void testParseIfStatement(){
        String source = "if(someBoolean()){"
                + "someStatement();"
                + "}";
        tree = new ScopeTree(source);
        assertEquals(1, tree.getChildren().size());
        this.compareStrings(source, tree.getSource());
        Block block = tree.getChildren().getFirst();
        assertEquals(1, block.getChildren().size());
        this.compareStrings("if(someBoolean())", block.getDeclaration());
        this.compareStrings("someStatement();", block.getSource());
        
        source = "if(someBoolean())"
                + "someStatement();";
        tree = new ScopeTree(source);
        assertEquals(1, tree.getChildren().size());
        this.compareStrings(source, tree.getSource());
        block = tree.getChildren().getFirst();
        assertEquals(1, block.getChildren().size());
        this.compareStrings("if(someBoolean())", block.getDeclaration());
        this.compareStrings("someStatement();", block.getSource());
    }
    
    @Test
    public void testIfElseStatement(){
        fail();
    }
    
    @Test
    public void testParseLoop(){
        fail();
    }
    
    @Test
    public void testParseSingleStatementBlock(){
        fail();
    }
    
}
