/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Internal.BaseTest;
import LanguageBase.Parsers.ParseTree.TreeNode;
import java.util.LinkedList;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ParseTreeTest extends BaseTest{
    private ParseTree tree;
    
    public ParseTreeTest() {
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
    
    private void validateNodeIndeces(ParseTree node, int start, int end){
        assertEquals(start, node.startIndex);
        assertEquals(end, node.endIndex);
    }
    
    private String getBasicTestClass(){
        return "class SomeClass{"
                + "int x = 5;"
                + "int y;"
                + "void someMethod(){"
                + "y = 7;"
                + "}"
                + "}";
    }

    @Test
    public void testParseTree() {
        String source = this.getBasicTestClass();
        tree = new ParseTree(source);
        
        assertEquals(3, tree.getNodeCount());
        assertEquals(0, tree.startIndex);
        assertEquals(source.length(), tree.endIndex);
        assertEquals(1, tree.getChildren().size());
        assertEquals(1, tree.getChildren().getFirst().getChildren().size());
        assertTrue(tree.getChildren()
                .getFirst()
                .getChildren()
                .getFirst()
                .getChildren().isEmpty());
        
        assertEquals(3, tree.getNodes().size());
    }
    
    @Test
    public void testParseEmptyString(){
        tree = new ParseTree("");
        assertTrue(tree.getChildren().isEmpty());
        assertTrue(tree.getDeclaration().isEmpty());
        assertTrue(tree.getStatements().isEmpty());
    }
    
    @Test
    public void testTreeIndeces(){
        String source = this.getBasicTestClass();
        tree = new ParseTree(source);
        
        this.validateNodeIndeces(tree, 0, source.length());
        
        TreeNode node = tree.getChildren().getFirst();
        this.validateNodeIndeces(node, 15, 58);
        
        node = node.getChildren().getFirst();
        this.validateNodeIndeces(node, 49, 57);
    }
    
    @Test
    public void testNodeParents(){
        String source = this.getBasicTestClass();
        tree = new ParseTree(source);
        
        TreeNode node1 = tree.getChildren().getFirst();
        assertEquals(tree, node1.parent);
        
        TreeNode node2 = node1.getChildren().getFirst();
        assertEquals(node1, node2.parent);
    }
    
    @Test
    public void testNodeBodies(){
        String source = this.getBasicTestClass();
        tree = new ParseTree(source);
        
        this.compareStrings(source, tree.getBody());
        TreeNode node = tree.getChildren().getFirst();
        source = "{int x = 5;int y;void someMethod(){y = 7;}}";
        this.compareStrings(source, node.getBody());
        node = tree.getChildren().getFirst().getChildren().getFirst();
        source = "{y = 7;}";
        this.compareStrings(source, node.getBody());
    }
    
    @Test
    public void testNodeStatements(){
        tree = new ParseTree(this.getBasicTestClass());
        
        ParseTree node = tree;
        assertEquals(1, node.getStatements().size());
        this.compareStrings("class SomeClass", tree.getStatements().getFirst());
        
        node = node.getChildren().getFirst();
        assertEquals(1, node.getStatements().size());
        this.compareStrings("class SomeClass", node.getStatements().getFirst());
        
        node = node.getChildren().getFirst();
        LinkedList test = node.getStatements();
        assertEquals(3, node.getStatements().size());
        this.compareStrings("int x = 5", node.getStatements().get(0));
        this.compareStrings("int y", node.getStatements().get(1));
        this.compareStrings("void someMethod()", node.getStatements().get(2));
    }
    
    @Test
    public void testGetNodeDeclaration(){
        tree = new ParseTree(this.getBasicTestClass());
        
        assertEquals(1, tree.getStatements().size());
        this.compareStrings("class SomeClass", tree.getDeclaration());
        
        TreeNode node = tree.getChildren().getFirst();
        this.compareStrings("class SomeClass", node.getDeclaration());
        
        node = node.getChildren().getFirst();
        this.compareStrings("void someMethod()", node.getDeclaration());
    }
    
    
    @Test
    public void testParseMultipleTrees(){
        String source = "class SomeClass{"
                + "some statement;"
                + "}"
                + "class SomeOtherClass{"
                + "some statement;"
                + "}";
        tree = new ParseTree(source);
        //assert tree has 2 roots
        assertEquals(2, tree.children.size());
        this.validateNodeIndeces(tree, 0, source.length());
        this.validateNodeIndeces(tree.getChildren().getFirst(), 52, 69);
        this.validateNodeIndeces(tree.getChildren().getLast(), 15, 32);
        
        this.compareStrings(source, tree.getSource().toString());
        this.compareStrings("class SomeOtherClass", tree.children.getFirst().getDeclaration());
        this.compareStrings("class SomeClass", tree.children.getLast().getDeclaration());
    }
    
    @Test
    public void testParseIfStatement(){
        String source = "if(some.boolean()){"
                + "some statement();"
                + "}";
        tree = new ParseTree(source);
        assertEquals(1, tree.getChildren().size());
        assertEquals(2, tree.getNodes().size());
        
        TreeNode node = tree.getChildren().getFirst();
        
        assertEquals(18, node.startIndex);
        assertEquals(36, node.endIndex);
        this.compareStrings("if(some.boolean())", node.getDeclaration());
        assertEquals(1, node.getStatements().size());
        this.compareStrings("some statement();", node.getStatements().getFirst());
    }
    
    @Test
    public void testSkipWhiteSpaces(){
        String source = "some    thing   else";
        tree = new ParseTree(source);
        
        assertEquals(1, tree.skipWhiteSpaces(0));
        assertEquals(8, tree.skipWhiteSpaces(3));
    }
    
    @Test 
    public void testLineCount(){
        tree = new ParseTree(this.getBasicTestClass());
        assertEquals(1, tree.getLineCount());
        //should count lines AFTER reformat
    }
    
    @Test
    public void testSkipUntilSymbol(){
        String source = "flibertyGibbet;";
        tree = new ParseTree(source);
        
        int index = tree.skipUntilSymbol(0, "G");
        assertEquals(8, index);
        
        index = tree.skipUntilSymbol(0, "Gi");
        assertEquals(8, index);
        
        index = tree.skipUntilSymbol(0, "f");
        assertEquals(0, index);
        
        index = tree.skipUntilSymbol(0, ";");
        assertEquals(14, index);
        
        index = tree.skipUntilSymbol(0, "b");
        assertEquals(3, index);
        
        index = tree.skipUntilSymbol(4, "b");
        assertEquals(10, index);
        
        
        tree = new ParseTree("");
        index = tree.skipUntilSymbol(4, "b");
        assertEquals(-1, index);
        index = tree.skipUntilSymbol(0, "b");
        assertEquals(-1, index);
    }
    
    @Test
    public void testArraysDontHaveTheirOwnScope(){
        String source = "String[] something = new String[]{\"foo\",\"bar\"};";
        tree = new ParseTree(source);
        assertEquals(1, tree.getNodes().size());
    }
    
    @Test 
    public void testParseSingleStatement(){
        String source = "String something = \"foobar\";";
        tree = new ParseTree(source);
        assertEquals(1, tree.getNodes().size());
        assertEquals(null, tree.getDeclaration());
        assertEquals(source, tree.getStatements().getFirst());
    }
    
}
