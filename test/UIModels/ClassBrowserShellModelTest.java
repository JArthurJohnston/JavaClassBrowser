/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIShells.ClassBrowserShell;
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
        model = new ClassBrowserShellModel(main);
    }
    
    @After
    public void tearDown() {
        main = null;
        project = null;
        model = null;
    }

    @Test
    public void testConstructor(){
        ClassBrowserShell shell = (ClassBrowserShell)this.getVariableFromClass(model, "shell");
        assertEquals(ClassBrowserShell.class, shell.getClass());
        assertEquals(ProjectModel.class, model.getProject().getClass());
        assertEquals(main.getSelectedProject(), model.getProject());
        assertEquals(project, model.getProject());
    }
    
    @Test
    public void testClassSelected(){
        assertEquals(null, model.getSelectedClass());
        fail("dependant on add class");
    }
    
    @Test
    public void testAddClassUpdates(){
        try {
            PackageModel aPackage = project.addPackage(new PackageModel(project, "a package"));
            //model.addClass(new ClassModel(aPackage, "AClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, model.getProject().getClassList().size());
        assertEquals("AClass", model.getSelectedClass().name());
    }
    
    @Test
    public void testSelectedPackage(){
        
    }
}
