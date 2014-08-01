/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Nodes;

import Internal.BaseTest;
import LanguageBase.Parsers.MockParsers.MockBlockNode;
import LanguageBase.Parsers.Nodes.StatementNode;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Types.ClassType;
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
public class StatementNodeTest extends BaseTest{
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

    protected void setUpTestProject()throws Exception{
        main = new MainApplication();
        super.setUpProjectAndPackage();
        ClassModel aClass = parentPackage.addClass(new ClassModel("OneClass"));
        aClass.addMethod(new MethodModel("someMethod"));
        MethodModel aMethod = aClass.addMethod(new MethodModel("testMethod"));
        parentProject.addClass(new ClassModel("TwoClass"));
    }
    
    @Test
    public void testIsClassDeclaration(){
        statement = parent.getStatement("");
        statement.parseStatement();
        assertFalse(statement.isClassDeclaration());
        
        statement = parent.getStatement("class SomeClass");
        statement.parseStatement();
        assertTrue(statement.isClassDeclaration());
    }
    
    @Test
    public void testStatementScope(){
        statement = parent.getStatement("");
        statement.parseStatement();
        assertSame(ScopeType.NONE, statement.getScope());
        
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
    
    @Test
    public void testStatementClassSide(){
        statement = parent.getStatement("");
        assertEquals(ClassType.INSTANCE, statement.getClassType());
        
        statement = parent.getStatement("");
        statement.parseStatement();
        assertEquals(ClassType.INSTANCE, statement.getClassType());
        
        statement = parent.getStatement("private void someMethod()");
        statement.parseStatement();
        assertSame(ClassType.INSTANCE, statement.getClassType());
        
        statement = parent.getStatement("private static void someMethod()");
        statement.parseStatement();
        assertSame(ClassType.STATIC, statement.getClassType());
        
        statement = parent.getStatement("private  static  void  someMethod()");
        statement.parseStatement();
        assertSame(ClassType.STATIC, statement.getClassType());
        assertSame(ScopeType.PRIVATE, statement.getScope());
    }
    
    @Test
    public void testIsMethodDeclaration(){
        statement = parent.getStatement("");
        statement.parseStatement();
        assertFalse(statement.isMethodDeclaration());
        
        statement = parent.getStatement("private void aMethod()");
        statement.parseStatement();
        assertTrue(statement.isMethodDeclaration());
    }
    
    @Test
    public void testParse() throws Exception{
        this.setUpTestProject();
        fail();
    }
    
    @Test
    public void testFindReference(){
        fail();
    }
    
    @Test
    public void testParseArgurmentsFromText(){
        statement = parent.getStatement("someObject.aMethod(some, argument)");
        statement.setOpenParen(18);
        statement.setCloseParen(33);
        
        assertEquals(2, statement.getArguments().length);
        assertEquals("some", statement.getArgumentAt(0));
        assertEquals("argument", statement.getArgumentAt(1));
    }
}
