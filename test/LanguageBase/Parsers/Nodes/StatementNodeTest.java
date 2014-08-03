/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Nodes;

import Internal.BaseTest;
import LanguageBase.Parsers.MockParsers.MockBlockNode;
import Models.ClassModel;
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
    
    @Test
    public void testConstructorAndGetters(){
        statement = new StatementNode(parent, 5);
        assertSame(parent, statement.getParentBlock());
        statement = new StatementNode(parent, 5, 6);
        assertSame(parent, statement.getParentBlock());
    }
    
    @Test
    public void testParseSegmentAddsReferences() throws Exception{
        statement = parent.getStatement("private void aMethod()");
        
        statement.parseSegment(7);
        assertTrue(statement.getReferences().isEmpty());
        
        statement.parseSegment(12);
        assertTrue(statement.getReferences().isEmpty());
        
        statement.parseSegment(20);
        assertEquals(1, statement.getReferences().size());
        assertEquals("aMethod", statement.getReferences().get(0));
    }
    
    @Test
    public void testParseSegmentSetsScopeModifier(){
        statement = parent.getStatement("");
        statement.parseSegment(0);
        assertSame(ScopeType.NONE, statement.getScope());
        
        statement = parent.getStatement("private void aMethod()");
        statement.parseSegment(7);
        assertSame(ScopeType.PRIVATE, statement.getScope());
        
        statement = parent.getStatement("public void aMethod()");
        statement.parseSegment(6);
        assertSame(ScopeType.PUBLIC, statement.getScope());
        
        statement = parent.getStatement("protected void aMethod()");
        statement.parseSegment(9);
        assertSame(ScopeType.PROTECTED, statement.getScope());
    }
    
    @Test
    public void testParseSegmentSetsStaticOrInstanceModifier(){
        statement = parent.getStatement("");
        statement.parseSegment(0);
        assertSame(ClassType.INSTANCE, statement.getClassType());
        
        statement = parent.getStatement("private void aMethod()");
        statement.parseSegment(7);
        assertSame(ClassType.INSTANCE, statement.getClassType());
        
        statement = parent.getStatement("private static void aMethod()");
        statement.parseSegment(7);
        statement.parseSegment(14);
        assertSame(ClassType.STATIC, statement.getClassType());
    }
    
    @Test
    public void testParseSegmentSetsObjectType(){
        String source = "";
        statement = parent.getStatement(source);
        statement.parseSegment(0);
        assertNull(statement.getObjectType());
        
        source = "void";
        statement = parent.getStatement(source);
        statement.parseSegment(source.length()-1);
        assertSame(ClassModel.getPrimitive(source), statement.getObjectType());
    }
    
    @Test
    public void testIsOpenParen(){
        statement = parent.getStatement("");
        assertFalse(statement.isOpenParen());
        statement = parent.getStatement("(");
        statement.parseSegment(0);
        assertFalse(statement.isOpenParen());
        statement = parent.getStatement(")");
        statement.parseSegment(0);
        assertFalse(statement.isOpenParen());
    }
    
    @Test
    public void testParseEmptyArgurments(){
        statement = parent.getStatement("");
        statement.parseSegment(0);
        assertTrue(statement.getArguments().isEmpty());
        
        statement = parent.getStatement("something()");
        statement.parseSegment(9);
        assertTrue(statement.getArguments().isEmpty());
        
        statement.parseSegment(9);
        statement.parseSegment(10);
        assertTrue(statement.getArguments().isEmpty());
        
        statement.parseSegment(10);
        statement.parseSegment(10);
        assertTrue(statement.getArguments().isEmpty());
    }
    
    @Test
    public void testParseActualArguments(){
        statement = parent.getStatement("(some)");
        statement.parseSegment(5);
        assertEquals(1, statement.getArguments().size());
        
        statement = parent.getStatement("(some, thing)");
        statement.parseSegment(0);
        statement.parseSegment(12);
        assertEquals(2, statement.getArguments().size());
        this.compareStrings("some", statement.getArguments().get(0));
        this.compareStrings("thing", statement.getArguments().get(1));
    }
    
    @Test
    public void testParseAddsReference(){
        statement = parent.getStatement("something()");
        statement.parseSegment(9);
        assertEquals(1, statement.getReferences().size());
        
    }
    
    @Test
    public void testReferencesAndArgumentsDontOverlap(){
        statement = parent.getStatement("someObject.someMethod(some, args)");
        statement.parseSegment(10);
        statement.parseSegment(21);
        statement.parseSegment(32);
        assertEquals(2, statement.getReferences().size());
        this.compareStrings("someObject", statement.getReferences().get(0));
        this.compareStrings("someMethod", statement.getReferences().get(1));
        assertEquals(2, statement.getArguments().size());
        this.compareStrings("some", statement.getArguments().get(0));
        this.compareStrings("args", statement.getArguments().get(1));
    }
    
    @Test
    public void testNestedStatements(){
        statement = parent.getStatement("someObject.someMethod(this.someOtherMethod(), args)");
        fail();
        /*
        my current logic wont cover this case. Id have to parse the method being
        called inside the outer method... damn. 
        */
    }
}
