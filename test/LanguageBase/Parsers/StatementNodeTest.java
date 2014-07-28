/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import LanguageBase.Parsers.MockParsers.MockBlockNode;
import Types.ScopeType;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class StatementNodeTest {
    private static MockBlockNode parent;
    private StatementNode statement;
    
    public StatementNodeTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {
        parent = new MockBlockNode();
    }
    
    @AfterClass
    public static void tearDownClass() {
        parent = null;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        statement = null;
    }

    @Test
    public void testIsClassDeclaration(){
        statement = parent.getStatement("");
        assertFalse(statement.isClassDeclaration());
        statement = parent.getStatement("class SomeClass");
        assertTrue(statement.isClassDeclaration());
    }
    
    @Test
    public void testStatementScope(){
        statement = parent.getStatement("private void someMethod()");
        statement.parseStatement();
        assertSame(ScopeType.PRIVATE, statement.getScope());
        
        statement = parent.getStatement("public void someMethod()");
        statement.parseStatement();
        assertSame(ScopeType.PUBLIC, statement.getScope());
        
        statement = parent.getStatement("protected void someMethod()");
        statement.parseStatement();
        assertSame(ScopeType.PROTECTED, statement.getScope());
        
        statement = parent.getStatement("void someMethod()");
        statement.parseStatement();
        assertSame(ScopeType.NONE, statement.getScope());
    }
    
}
