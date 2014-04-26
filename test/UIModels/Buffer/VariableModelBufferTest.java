/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Exceptions.NameAlreadyExistsException;
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
    private VariableModel model;
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
    public void setUp() {
        parentClass = new ClassModel("Parent");
        this.setUpVar();
        buffer = new VariableModelBuffer(model);
    }
    
    @After
    public void tearDown() {
        buffer = null;
        model = null;
        parentClass = null;
    }
    
    private void setUpVar(){
        model = new VariableModel(ScopeType.PRIVATE, new ClassModel("int"), "x");
        model.setType(ClassType.INSTANCE);
        model.setParent(parentClass);
    }
    
    private void setUpNullVar(){
        model.setScope(null);
        model.setParent(null);
        model.setObjectType(null);
        model.setName(null);
        model.setType(null);
        model.setValue(null);
    }

    @Test
    public void testInit() {
        assertEquals(model, buffer.getEntity());
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
        } catch (NameAlreadyExistsException ex) {
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
    public void testParseDeclaration(){
        String source = "Integer x;";
        assertTrue(buffer.parseDeclaration(source));
        source = "private Integer x;";
        assertTrue(buffer.parseDeclaration(source));
        source = "private static Integer x;";
        assertTrue(buffer.parseDeclaration(source));
        source = "private static Integer x;";
        assertTrue(buffer.parseDeclaration(source));
        source = "static private Integer x;";
        assertTrue(buffer.parseDeclaration(source));
    }
    
    @Test
    public void testParseDeclarationWithFinalContainsValue(){
        String source = "final Integer x = 4;";
        assertTrue(buffer.parseSource(source));
        assertEquals(" 4", (String)this.getVariableFromClass(buffer, "value"));
        assertTrue((boolean)this.getVariableFromClass(buffer, "isFinal"));
        assertEquals("Integer", ((ClassModel)this.getVariableFromClass(buffer, "objectType")).name());
        assertTrue(buffer.isValid());
    }
    
    @Test
    public void testParseDeclarationFinalWithoutValue(){
        String source = "final Integer x;";
        assertTrue(buffer.parseDeclaration(source));
        assertTrue((boolean)this.getVariableFromClass(buffer, "isFinal"));
        assertEquals("Integer", ((ClassModel)this.getVariableFromClass(buffer, "objectType")).name());
        assertEquals("x", model.name());
        assertTrue(buffer.isValid());
    }
    
    @Test
    public void testInvalidDeclarationFinal(){
        assertFalse(buffer.parseDeclaration("final intever int x;"));
    }
    
    @Test
    public void testParseSource(){
        String source = "public static int x = 5;";
        assertTrue(buffer.parseSource(source));
        assertTrue(buffer.isValid());
    }
    
    @Test
    public void testSaveToModel(){
        assertEquals(model, buffer.getEntity());
        this.testParseSource();
        buffer.saveToModel();
        assertEquals(ScopeType.PUBLIC, model.getScope());
        assertEquals(ClassType.STATIC, model.getType());
        assertEquals("int", model.getObjectType().name());
        assertTrue(this.compareStrings(" 5", model.getValue()));
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
    
}
