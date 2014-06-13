/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import LanguageBase.Parsers.ScopeTree.Block;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ScopeTreeTest {
    private ScopeTree tree;
    
    public ScopeTreeTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        tree = null;
    }
    
    private String simpleTestClassSource(){
        return "class SomeClass{"
                + "int var = 5;"
        + "}";
    }
    
    @Test
    public void testParseSource(){
        tree = new ScopeTree(this.simpleTestClassSource());
        
        assertEquals(1, tree.children.size());
        assertEquals(1, tree.statements.size());
        assertEquals("class SomeClass", tree.statements.getFirst());
        
        Block block = tree.children.getFirst();
        assertEquals("int var = 5;", block.getSource());
    }
    
}
