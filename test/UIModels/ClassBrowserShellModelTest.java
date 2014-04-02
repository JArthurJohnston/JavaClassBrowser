/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import Exceptions.VeryVeryBadException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIShells.ClassBrowserShell;
import java.util.ArrayList;
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
public class ClassBrowserShellModelTest extends BaseTest{
    private MainApplication main;
    private ClassBrowserShellModel model;
    private ProjectModel project;
    
    public ClassBrowserShellModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        main = new MainApplication();
        try {
            project = main.setSelectedProejct(main.addProject(new ProjectModel(main,"A Project")));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        model = (ClassBrowserShellModel)main.addModel(new ClassBrowserShellModel(main));
    }
    
    @After
    public void tearDown() {
        main = null;
        project = null;
        model = null;
    }
    
    private void setUpModelWithClasses(){
        PackageModel aPackage = project.getPackageList().get(0);
        try {
            aPackage.addClass(new ClassModel(aPackage, "NewClass"));
            aPackage.addClass(new ClassModel(aPackage, "AnotherNewClass"));
            aPackage.addClass(new ClassModel(aPackage, "YetAnotherNewClass"));
            assertEquals(3, project.getClassList().size());
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        model = new ClassBrowserShellModel(main);
    }

    @Test
    public void testConstructor(){
        ClassBrowserShell shell = (ClassBrowserShell)this.getVariableFromClass(model, "shell");
        assertEquals(ClassBrowserShell.class, shell.getClass());
        assertEquals(ProjectModel.class, model.selectedProject().getClass());
        assertEquals(main.getSelectedProject(), model.selectedProject());
        assertEquals(project, model.selectedProject());
        this.setUpModelWithClasses();
    }
    
    @Test
    public void testInitialClassList(){
        assertTrue(model.getClasses().isEmpty());
        this.setUpModelWithClasses();
        assertEquals(3, model.getClasses().size());
    }
    
    @Test
    public void testAddClassUpdates(){
        ClassModel aClass = null;
        try {
            PackageModel aPackage = project.addPackage(new PackageModel(project, "a package"));
            aClass = model.addClass(new ClassModel(model.selectedPackage(), "AClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, model.selectedProject().getClassList().size());
        assertTrue(model.selectedProject().getClassList().contains(aClass));
    }
    
    @Test
    public void testRemoveClass(){
        ClassModel aClass = null;
        try {
            PackageModel aPackage = project.addPackage(new PackageModel(project, "a package"));
            aClass = model.addClass(new ClassModel(model.selectedPackage(), "AClass"));
            assertTrue(model.selectedProject().getClassList().contains(aClass));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        try {
            model.removeClass(aClass);
        } catch (VeryVeryBadException ex) {
            fail(ex.getMessage());
        }
        assertFalse(model.selectedProject().getClassList().contains(aClass));
    }
    
    @Test
    public void testSelectedPackage(){
        assertEquals(PackageModel.class, model.selectedPackage().getClass());
        assertEquals(main.selectedPackage(), model.selectedPackage());
        assertTrue(this.compareStrings("default package", model.selectedPackage().name()));
    }
    
    @Test
    public void testClosed(){
        assertTrue(((ArrayList)this.getVariableFromClass(main, "openWindowModels")).contains(model));
        model.close();
        assertFalse(((ArrayList)this.getVariableFromClass(main, "openWindowModels")).contains(model));
    }
}
