/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Exceptions.AlreadyExistsException;
import Models.ClassModel;
import Models.ProjectModel;
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
    public void setUp() {
        ProjectModel aProject = new ProjectModel("parent project");
        try {
            baseClass = aProject.getDefaultPackage().addClass(new ClassModel("BaseClass"));
            aProject.getDefaultPackage().addClass(new ClassModel("SomeOtherClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        buffer = baseClass.getBuffer();
    }
    
    @After
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
    public void testSaveToModel(){
        buffer.setName("SomeName");
        buffer.saveToModel();
        assertEquals("SomeName",baseClass.name());
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
        
    }
    
}
