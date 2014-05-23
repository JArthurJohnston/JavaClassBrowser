/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import Internal.Mocks.MockBrowserController;
import Internal.Mocks.MockPackageModel;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIModels.BrowserUIController;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ClassTreePanelTest extends BaseTreePanelTest{
    private ClassTreePanel panel;
    private ProjectModel project;
    
    public ClassTreePanelTest() {
    }
    
    @Before
    public void setUp() {
        project = new ProjectModel("a project");
        panel = new ClassTreePanel();
    }
    
    @After
    public void tearDown() {
        project = null;
        panel = null;
    }
    
    private BrowserUIController controller(){
        MockBrowserController c = new MockBrowserController();
        c.setProject(project);
        ClassModel aClass = new ClassModel("AClass");
        c.setSelected(aClass);
        try {
            PackageModel aPackage = project
                    .addPackage(new PackageModel("SomePackage"));
            
            aPackage.addClass(aClass);
            aClass.addClass(new ClassModel("ASubClass"));
            aClass.addClass(new ClassModel("AnotherSubClass"));
            aClass.addClass(new ClassModel("YetAnotherSubClass"));
            aPackage.addClass(new ClassModel("AnotherClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        c.setSelected(aClass);
        assertEquals(aClass, c.getSelectedClass());
        return c;
    }
    
    @Test
    public void testSetControllerWithSelectedClass(){
        panel = new ClassTreePanel();
        assertTrue(panel.isEmpty());
        panel.setModel(this.controller());
        this.verifyTreeSize(4);
    }
    
    @Test 
    public void testSetControllerWithSelectedPackage(){
        panel = new ClassTreePanel();
        BrowserUIController controller = this.controller();
        PackageModel aPackage = controller.getSelectedClass().getParentPackage();
        controller.setSelected(aPackage);
        
        panel.setModel(controller);
        this.showPanel(panel);
        this.verifyTreeSize(5);
    }

    @Test
    public void testSelectionChangedUpdatesModel() {
        BrowserUIController controller = this.controller();
        panel.setModel(controller);
        panel.setSelectedIndex(3);
        assertEquals("AnotherSubClass", controller.getSelected().name());
        panel.setSelectedIndex(2);
        assertEquals("ASubClass", controller.getSelected().name());
    }
    
    private PackageModel packageWithClasses(){
        PackageModel aPackage = null;
        try {
            aPackage = new ProjectModel("a project")
                    .addPackage(new PackageModel("a package"));
            ClassModel aClass = aPackage.addClass(new ClassModel("AClass"));
            aClass.addClass(new ClassModel("ASubClass"));
            aClass.addClass(new ClassModel("AnotherSubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return aPackage;
    }
    
    @Test
    public void testClassRemovedFromSelectedPackage(){
        MockBrowserController controller = new MockBrowserController();
        PackageModel aPackage = this.packageWithClasses();
        controller.setSelected(aPackage);
        ClassModel classRemoved = aPackage.getClassList().get(1);
        
        panel.setModel(controller);
        assertTrue(panel.contains(classRemoved));
        this.verifyTreeSize(3);
        
        panel.modelRemoved(classRemoved);
        this.verifyTreeSize(2);
        assertFalse(panel.contains(classRemoved));
    }
    
    @Test
    public void testClassRemovedFromAnotherPackage(){
        MockBrowserController controller = new MockBrowserController();
        PackageModel aPackage = this.packageWithClasses();
        controller.setSelected(aPackage);
        MockPackageModel anotherPackage = new MockPackageModel("another package");
        ClassModel classRemoved = 
                anotherPackage.addClass(new ClassModel("SomeRemovedClass"));
        anotherPackage.setParent(project);
        classRemoved.setParent(anotherPackage);
        
        panel.setModel(controller);
        this.verifyTreeSize(3);
        
        panel.modelRemoved(classRemoved);
        this.verifyTreeSize(3);
    }
    
    @Test
    public void testClassRemovedFromALLPackage(){
        fail();
    }

    @Test
    public void testClassAddedFromSelectedPackage() {
        MockBrowserController controller = new MockBrowserController();
        PackageModel aPackage = this.packageWithClasses();
        controller.setSelected(aPackage);
        
        panel.setModel(controller);
        this.showPanel(panel);
        this.verifyTreeSize(3);
        ClassModel classAdded = null;
        
        try {
            classAdded = aPackage.addClass(new ClassModel("ClassAdded"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        
        panel.modelAdded(classAdded);
        this.verifyTreeSize(4);
        assertTrue(panel.contains(classAdded));
    }
    
    @Test
    public void testClassAddedFromAnotherPackage(){
        MockBrowserController controller = new MockBrowserController();
        PackageModel aPackage = this.packageWithClasses();
        controller.setSelected(aPackage);
        
        panel.setModel(controller);
        this.verifyTreeSize(3);
        ClassModel classAdded = null;
        
        try {
            PackageModel anotherPackage = project.addPackage(new PackageModel("another package"));
            classAdded = anotherPackage.addClass(new ClassModel("ClassAdded"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        
        panel.modelAdded(classAdded);
        this.verifyTreeSize(3);
        assertFalse(panel.contains(classAdded));
    }
    
    @Test
    public void testClassAddedFromALLPackage(){
        fail();
    }
    
    @Test 
    public void testSelectionChanged(){
        PackageModel packageOne = null;
        PackageModel packageTwo = null;
        try {
            ProjectModel aProject = new ProjectModel("aProject");
            packageOne = aProject.addPackage(new PackageModel("package one"));
                packageOne.addClass(new ClassModel("P1Class1"));
                packageOne.addClass(new ClassModel("P1Class2"));
                packageOne.addClass(new ClassModel("P1Class3"));
            packageTwo = aProject.addPackage(new PackageModel("package two"));
                packageTwo.addClass(new ClassModel("P2Class1"));
                packageTwo.addClass(new ClassModel("P2Class2"));
                packageTwo.addClass(new ClassModel("P2Class3"));
                packageTwo.addClass(new ClassModel("P2Class4"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        panel.selectionChanged(packageOne);
        this.verifyTreeSize(3);
        panel.selectionChanged(packageTwo);
        this.verifyTreeSize(4);
    }
    
    /*
    Logical problem
    -my current framework only adds stuff to the gui via a ModelAdded
    trigger.
    -I only want to expand a tree if the class being added is the one im currently 
    working on. IE classes added from one shell sould be more or less ignored 
    by the others.
    
    solution: set the selection of that controller to the class being added, then
    have the controller/shells ignore a class added if its == to its own
    selection
    */
    
    
}
