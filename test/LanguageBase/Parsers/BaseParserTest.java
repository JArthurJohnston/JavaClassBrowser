/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import LanguageBase.Parsers.Nodes.BlockNode;
import LanguageBase.Parsers.Nodes.StatementNode;
import Internal.BaseTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 *
 * @author arthur
 */
public class BaseParserTest  extends BaseTest{
    
    protected void verifyBlockStatements(BlockNode aBlock, int numberOfStatements){
        if(numberOfStatements + 1 != aBlock.getStatements().size())
            fail("expected "+ numberOfStatements+" but was "+ 
                    (aBlock.getStatements().size()-1));
        //assertEquals(numberOfStatements+1, aBlock.getStatements().size());
        assertEquals("", aBlock.getStatements().getLast().getSource());
    }
    
    protected BlockNode verifyAndGetChildBlockFromStatement(StatementNode aStatement){
        //errors if the block is null and asserts its the right class
        //since BlockNodes are lazy-initialized
        assertSame(BlockNode.class, 
                this.getVariableFromClass(aStatement, "childBlock").getClass());
        return aStatement.getChildBlock();
    }
    
    protected void printStatementsFromBlock(BlockNode aBlock){
        for(StatementNode s : aBlock.getStatements())
            System.out.println('\'' + s.getSource() + '\'');
    }
    
}
