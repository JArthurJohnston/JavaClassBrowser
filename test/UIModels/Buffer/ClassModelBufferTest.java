/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Exceptions.AlreadyExistsException;
import Models.ClassModel;
import Models.InterfaceModel;
import Models.ProjectModel;
import Types.ScopeType;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ClassModelBufferTest extends BaseBufferTest{
    private ClassModel baseClass;
    private ClassModelBuffer buffer;
    
    public ClassModelBufferTest() {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        try {
            parentPackage.addClass(new ClassModel("SomeOtherClass"));
            parentPackage.addClass(new ClassModel("BaseClass")).setScope(ScopeType.PRIVATE);
            baseClass = parentProject.findClass("BaseClass");
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        buffer = baseClass.getBuffer();
    }
    
    @After
    @Override
    public void tearDown() {
        baseClass= null;
        buffer = null;
    }

    @Test
    public void testInit() {
        assertEquals(baseClass, buffer.getEntity());
        assertEquals(buffer.name, baseClass.name());
        assertTrue(buffer.isValid());
        assertTrue(buffer.warnings.isEmpty());
        assertFalse(buffer.isAbstract());
        assertNull(buffer.getParentClass());
        assertEquals(parentPackage, buffer.getParentPackage());
    }
    
    @Test
    public void testDuplicateClassNameIsInvalid(){
        buffer.setName("SomeOtherClass");
        assertFalse(buffer.isValid());
    }
    
    @Test
    public void testNewClassName(){
        try {
            assertEquals("NewClass", buffer.newClassName());
            baseClass.getParentPackage().addClass(new ClassModel("NewClass"));
            assertEquals("NewClass1", buffer.newClassName());
            baseClass.getParentPackage().addClass(new ClassModel("NewClass1"));
            assertEquals("NewClass2", buffer.newClassName());
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void applyChanges(){
        buffer.setName("SomeName");
        buffer.saveToModel();
        assertEquals("SomeName",baseClass.name());
    }
    
    @Test
    public void testRevertChanges(){
        fail();
    }
    
    @Test
    public void setAbstractAddsWarning(){
        fail();
        /*
        if a class is abstract, it cant be instantiated. thus it cant be referenced
        in another class or method's variables.
        brows for those references and warn the user.
        */
    }
    
    @Test
    public void testSetNameAddsWarning(){
        fail();
    }
    
    @Test
    public void testEditableString(){
        String expected = "private class BaseClass";
        assertEquals(expected, buffer.editableString());
    }
    
    /**
     * this tests the "happy case"
     * Ill need to write other tests for errors and 
     * edge cases
     */
    @Test
    public void testParseText(){
        String sourceText = "public abstract class SomeClass";
        buffer.parseSource(sourceText);
        assertEquals(ScopeType.PUBLIC, buffer.getScope());
        assertEquals("SomeClass", buffer.name);
        assertTrue(buffer.isAbstract());
        assertNull(buffer.getParentClass());
        assertTrue(buffer.getInterfaces().isEmpty());
        
        //test subclass declaration
        sourceText = "public class SomeClass extends SomeOtherClass";
        buffer.parseSource(sourceText);
        assertEquals(ScopeType.PUBLIC, buffer.getScope());
        assertEquals("SomeClass", buffer.name);
        assertFalse(buffer.isAbstract());
        assertEquals(parentProject.findClass("SomeOtherClass"), buffer.getParentClass());
    }
    
    @Test
    public void testParseImplements(){
        assertTrue(buffer.getInterfaces().isEmpty());
        try {
            parentPackage.addInterface(new InterfaceModel("AnInterface"));
            parentPackage.addInterface(new InterfaceModel("AnotherInterface"));
            parentPackage.addInterface(new InterfaceModel("YetAnotherInterface"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        InterfaceModel anInterface  = parentProject.findInterface("AnInterface");
        
        String sourceText = "class SomeClass implements AnInterface";
        buffer.parseSource(sourceText);
        assertEquals(1, buffer.getInterfaces().size());
        assertTrue(buffer.getInterfaces().contains(anInterface));
        
        sourceText = "class SomeClass implements AnInterface implements AnotherInterface";
        buffer.parseSource(sourceText);
        assertEquals(2, buffer.getInterfaces().size());
        assertTrue(buffer.getInterfaces().contains(anInterface));
        anInterface  = parentProject.findInterface("AnotherInterface");
        assertTrue(buffer.getInterfaces().contains(anInterface));
    }
    
    
}
