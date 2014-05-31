/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Exceptions.AlreadyExistsException;
import Models.ClassModel;
import Models.InterfaceModel;
import Types.ScopeType;
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
            parentPackage.addInterface(new InterfaceModel("AnInterface"));
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
        assertEquals(ScopeType.PRIVATE, buffer.getScope());
        assertEquals("BaseClass", buffer.getName());
        assertNull(buffer.getParentClass());
        
        buffer.parseSource("public class SomeClass extends SomeOtherClass");
        assertEquals(ScopeType.PUBLIC, buffer.getScope());
        assertEquals("SomeClass", buffer.getName());
        assertEquals(ClassModel.class, buffer.getParentClass().getClass());
        assertEquals(parentProject.findClass("SomeOtherClass"), buffer.getParentClass());
        
        buffer.revertChanges();
        assertEquals(ScopeType.PRIVATE, buffer.getScope());
        assertEquals("BaseClass", buffer.getName());
        assertNull(buffer.getParentClass());
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
        assertTrue(buffer.isValid());
    }
    
    @Test
    public void testParseScope(){
        assertEquals(ScopeType.PRIVATE, buffer.getScope());
        String sourceText = "public class BaseClass";
        buffer.parseSource(sourceText);
        assertEquals(ScopeType.PUBLIC, buffer.getScope());
        assertTrue(buffer.isValid());
    }
    
    @Test
    public void testParseAbstract(){
        assertFalse(buffer.isAbstract());
        String sourceText = "public abstract class BaseClass";
        buffer.parseSource(sourceText);
        assertTrue(buffer.isAbstract());
        assertTrue(buffer.isValid());
    }
    
    @Test
    public void testParseImplements(){
        assertTrue(buffer.getInterfaces().isEmpty());
        try {
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
        assertTrue(buffer.isValid());
    }
    
    @Test
    public void testSaveToModel(){
        String sourceText = "public class SomeClass extends SomeOtherClass implements AnInterface";
        buffer.parseSource(sourceText);
        buffer.saveToModel();
        assertEquals(ScopeType.PUBLIC, baseClass.getScope());
        assertEquals("SomeClass", baseClass.name());
        assertEquals(this.findClass("SomeOtherClass"), baseClass.getParent());
        assertEquals(this.findClass("AnInterface"),baseClass.getInterfaces().getFirst());
    }
    
    @Test
    public void testParseFinal(){
        buffer.parseSource("public final class SomeClass");
        assertTrue(baseClass.isFinal());
    }
    
    @Test
    public void testParseFinalAddsWarning(){
        fail();
        /*
        final classes cant have sub-classes, so if the user wants to turn a class 
        into a final class, he/she needs to be warned if the class in 
        question already has sub-classes.
        */
    }
    
    
}
