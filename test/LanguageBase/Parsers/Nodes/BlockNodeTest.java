/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Nodes;

import Internal.BaseTest;
import LanguageBase.Parsers.BlockParser;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class BlockNodeTest extends BaseTest{
    private BlockNode block;
    
    public BlockNodeTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    private void setUpParserAndNodeWithSource(String source) throws Exception{
        block = new BlockParser(source).getRootBlock();
    }

    @Test
    public void testGetReferences() throws Exception{
        String source = "someMethod();"
                + "someObject.someMethod();"
                + "someObject.someVar;";
        this.setUpParserAndNodeWithSource(source);
        assertEquals(5, block.getReferences().size());
        this.compareStrings("someMethod", block.getReferences().get(0));
        this.compareStrings("someObject", block.getReferences().get(1));
        this.compareStrings("someMethod", block.getReferences().get(2));
        this.compareStrings("someObject", block.getReferences().get(3));
        this.compareStrings("someVar", block.getReferences().get(4));
    }
    
}
