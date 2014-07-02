/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Internal.BaseTest;
import LanguageBase.Parsers.Nodes.BlockNode;
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

    @Test
    public void testParseSimpleStatements(){
        String source = "if(someBoolean()){"
                + "someMethod();"
                + "}";
        this.initializeParserWithSource(source);
    }
    
}
