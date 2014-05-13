/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIPanels.TreePanels.Nodes.*;
import UIShells.SystemBrowserShell;
import java.util.LinkedList;
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
 * @author Arthur
 */
public class BrowserUIControllerTest extends BaseTest{
    private BrowserUIController controller;
    
    public BrowserUIControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.setUpMain();
        main.setSelectedProejct(this.getProject());
        controller = new BrowserUIController(main);
    }
    
    @After
    public void tearDown() {
        main = null;
        controller = null;
    }
    
    private ProjectModel getProject(){
        ProjectModel aProject = new ProjectModel("a project");
        aProject.setMain(main);
        try {
            ClassModel aClass = aProject.addClass(new ClassModel("AClass1"));
            aClass.addClass(new ClassModel("ASubClass1"));
            aClass.addClass(new ClassModel("ASubClass2"));
            aClass.addClass(new ClassModel("ASubClass3"));
            aClass.addClass(new ClassModel("ASubClass4"));
            aProject.addClass(new ClassModel("AClass2"));
            aProject.addClass(new ClassModel("AClass3"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return aProject;
    }
    
    @Test
    public void testOpenFromMain(){
        controller = new BrowserUIController(new MainApplication());
        assertEquals(BrowserUIController.class, controller.getClass());
    }
    
    @Test
    public void testInit(){
        assertEquals(main, controller.main);
        assertEquals(main.getSelectedProject(), 
                (ProjectModel)this.getVariableFromClass(controller, "selectedProject"));
    }

    @Test
    public void testClose() {
        SystemBrowserShell aShell = controller.getShell();
        controller.close();
        assertFalse(aShell.isVisible());
        assertFalse(((LinkedList)this.getVariableFromClass(main, "openWindowModels")).contains(controller));
    }
    
    @Test
    public void testGetShell(){
        SystemBrowserShell aShell = controller.getShell();
        assertEquals(SystemBrowserShell.class, controller.getShell().getClass());
        assertEquals(aShell, controller.getShell());
        assertTrue(controller.getShell().isVisible());
    }
    
    @Test
    public void testSelectedProject(){
        assertEquals("a project", ((ProjectModel)this.getVariableFromClass(controller, "selectedProject")).name());
    }
    
    @Test
    public void testPackageList(){
        assertEquals(2, controller.getPackages().size());
    }
    
    @Test
    public void testAddPackage(){
        try {
            controller.addPackage(new PackageModel("a package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(3, controller.getPackages().size());
    }
    
    @Test
    public void testAddClass(){
        try {
            controller.addClass(new ClassModel("AClass"));
            fail("exception not thrown");
        } catch (Exception ex) {
            assertTrue(this.compareStrings("", ex.getMessage()));
        }
    }
    
    @Test
    public void testSelectedClass(){
        ClassModel aClass = null;
        assertEquals(null, controller.getSelectedClass());
        try {
            PackageModel aPackage = 
                    main.getSelectedProject().addPackage(
                    new PackageModel("a package"));
            aClass = aPackage.addClass(new ClassModel("AClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(aClass, controller.getSelectedClass());
    }
    
    @Test
    public void testOpenAddVariable(){
        controller.getShell();
        //AddVariableDialogue dialogue = controller.openAddVariable();
        //assertEquals(AddVariableDialogue.class, dialogue.getClass());
        //assertTrue(dialogue.isVisible());
        //dialogue.dispose();
        controller.close();
        fail();
    }
    
    @Test
    public void testGetSelectedPackage(){
        assertEquals(controller.getSelectedPackage(), main.getSelectedProject().getDefaultPackage());
                
    }
    
    
    @Test
    public void testGetAllClasses(){
        ClassNode allClasses = controller.getAllClasses();
        
    }
}
