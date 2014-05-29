/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import Models.ClassModel;
import Models.VariableModel;
import Types.ClassType;
import Types.ScopeType;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class VariableModelBufferTest extends BaseTest{
    private VariableModel baseVar;
    private VariableModelBuffer buffer;
    private ClassModel parentClass;
    
    public VariableModelBufferTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        try {
            parentClass = parentPackage.addClass(new ClassModel("Parent"));
        baseVar = parentClass.addVariable(
                new VariableModel(ScopeType.PRIVATE, ClassModel.getPrimitive("int"), "x"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        baseVar.setType(ClassType.INSTANCE);
        buffer = new VariableModelBuffer(baseVar);
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        buffer = null;
        baseVar = null;
        parentClass = null;
    }

    @Test
    public void testInit() {
        assertEquals(baseVar, buffer.getEntity());
        assertTrue(buffer.getEntity().isVariable());
    }
    
    @Test
    public void testIsValid(){
        //test name is valid
        buffer.setName(null);
        assertFalse(buffer.isValid());
        buffer.setName("");
        assertFalse(buffer.isValid());
        
        //cant have duplicate variables in a class
        try {
            parentClass.addVariable(new VariableModel(ScopeType.PRIVATE, new ClassModel("int"), "aVar"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        buffer.setName("aVar");
        assertFalse(buffer.isValid());
        buffer.setName("anotherVar");
        assertTrue(buffer.isValid());
        
        //test null type is not valid
        buffer.setType(null);
        assertFalse(buffer.isValid());
    }
    
    @Test
    public void testParseName(){
        buffer.parseSource("int aName");
        assertEquals("aName", buffer.getName());
        buffer.parseSource("int x");
        assertEquals("x", buffer.getName());
    }
    
    @Test
    public void testParseFinal(){
        buffer.parseSource("final int x");
        assertTrue(buffer.isFinal());
        buffer.parseSource("int x");
        assertFalse(buffer.isFinal());
    }
    
    @Test
    public void testParseScope(){
        buffer.parseSource("public int x");
        assertEquals(ScopeType.PUBLIC, buffer.getScope());
        buffer.parseSource("private int x");
        assertEquals(ScopeType.PRIVATE, buffer.getScope());
        buffer.parseSource("int x");
        assertEquals(ScopeType.NONE, buffer.getScope());
    }
    
    @Test
    public void testParseObjectType(){
        buffer.parseSource("char x");
        assertEquals(ClassModel.getPrimitive("char"), buffer.getObjectType());
        buffer.parseSource("int x");
        assertEquals(ClassModel.getPrimitive("int"), buffer.getObjectType());
        buffer.parseSource("Object x");
        assertEquals(ClassModel.getPrimitive("Object"), buffer.getObjectType());
    }
    
    @Test
    public void testParseStatic(){
        buffer.parseSource("static int x");
        assertEquals(ClassType.STATIC, buffer.getClasType());
        buffer.parseSource("int x");
        assertEquals(ClassType.INSTANCE, buffer.getClasType());
    }
    
    @Test
    public void testParseFinalWithoutValueAddsWarning(){
        fail();
    }
    
    @Test
    public void testParseSource(){
        String source = "public static int x = 5;";
        buffer.parseSource(source);
        assertEquals(ScopeType.PUBLIC, buffer.getScope());
        assertEquals(ClassModel.getPrimitive("int"), buffer.getObjectType());
        assertEquals(ClassType.STATIC, buffer.getClasType());
        assertEquals("5", buffer.getValue());
    }
    
    @Test
    public void testSaveToModel(){
        this.testParseSource();
        buffer.saveToModel();
        assertEquals(ScopeType.PUBLIC, baseVar.getScope());
        assertEquals(ClassModel.getPrimitive("int"), baseVar.getObjectType());
        assertEquals(ClassType.STATIC, baseVar.getType());
        assertEquals("5", baseVar.getValue());
    }
    
    @Test
    public void testSaveToModileWhileInvalid(){
        fail();
    }
    
    @Test
    public void testRevertChanges(){
        VariableModel testVar = null;
        try {
            testVar = parentClass.addVariable(new VariableModel("someVar"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        testVar.setScope(ScopeType.NONE);
        testVar.setType(ClassType.STATIC);
        testVar.setObjectType(ClassModel.getPrimitive("char"));
        
        buffer = new VariableModelBuffer(testVar);
        assertEquals("someVar", buffer.getName());
        assertEquals(ClassType.STATIC, buffer.getType());
        assertEquals(ClassModel.getPrimitive("char"), buffer.getObjectType());
        
        buffer.parseSource("private float aFloatVar");
        assertEquals(ScopeType.PRIVATE, buffer.getScope());
        assertEquals(ClassModel.getPrimitive("float"), buffer.getObjectType());
        assertEquals("aFloatVar", buffer.getName());
        assertEquals(ClassType.INSTANCE, buffer.getType());
        
        buffer.revertChanges();
        assertEquals(ScopeType.NONE, buffer.getScope());
        assertEquals(ClassModel.getPrimitive("char"), buffer.getObjectType());
        assertEquals("someVar", buffer.getName());
        assertEquals(ClassType.STATIC, buffer.getType());
    }
    
    @Test
    public void testChangeFromStaticToInstanceAddsWarning(){
        fail();
    }
    
    @Test
    public void testWarnings(){
        fail();
        /*
        whenever the parser runs into an error, it'll add that error to
        the warnings list, it will let the user save his/her changes. but it will
        warn them first, givint them a list of error messages.
        
        of course certian errors, like adding two variables with the
        same name to a class, should not be allowed, period.
        */
    }
    
    @Test
    public void testEditableString(){
        assertTrue(this.compareStrings("private int x;", buffer.editableString()));
    }
    
}
