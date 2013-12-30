/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.NameAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arthur
 */
public class PackageModelTest {
    PackageModel instance;
    ProjectModel parentProject;
    
    public PackageModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            parentProject = new ProjectModel();
            instance = parentProject.addPackage("New Package");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(PackageModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }
    
    @After
    public void tearDown() {
        parentProject = null;
        instance = null;
    }

    @Test
    public void testProjectKnowsPackage(){
        assertEquals(parentProject, instance.getParent());
        assertEquals("New Package", instance.name());
        assertEquals(1, parentProject.packageList().size());
        assertEquals(1, parentProject.packages().size());
        assertEquals(parentProject.packages().get("New Package"), instance);
        assertEquals(parentProject.packageList().get(0), instance);
        this.testPackageFields();
    }
    
    @Test
    public void testInitialize(){
        instance = new PackageModel();
        assertEquals("default package", instance.name());
        this.testPackageFields();
    }
    
    
    private void testPackageFields(){
        assertEquals(HashMap.class, instance.classes().getClass());
        assertEquals(HashMap.class, instance.packages().getClass());
        assertEquals(ArrayList.class, instance.packageList().getClass());
        assertEquals(ArrayList.class, instance.classList().getClass());
        assertEquals(0, instance.classList().size());
        assertEquals(0, instance.packageList().size());
        assertEquals(0, instance.classes().size());
        assertEquals(0, instance.packages().size());
    }
    
    

    /**
     * Test of addClass method, of class PackageModel.
     */
    @Test
    public void testAddClass_String() {
        ClassModel newClass = new ClassModel("WrongClass");
        try {
            newClass =  instance.addClass("NewClass");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(PackageModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
        assertEquals(instance, newClass.getParent());
        assertEquals(1, parentProject.classes().size());
        assertEquals(1, instance.classes().size());
        assertEquals(1, instance.classList().size());
        assertEquals(newClass, instance.classList().get(0));
    }
    
    @Test
    public void testAddSubClass(){
            ClassModel newSubClass = new ClassModel("Wrong Class");
        try {
            ClassModel parentClass = instance.addClass("ParentClass");
            newSubClass = parentClass.addClass("NewSubClass");
         } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(PackageModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
        assertEquals(2, parentProject.classes().size());
        assertEquals(newSubClass, parentProject.classes().containsKey("NewSubClass"));
        assertEquals(0, instance.classes().size());
        assertEquals(1, instance.classList().size());
        assertEquals(newSubClass, instance.classList().get(0));
    }

}
