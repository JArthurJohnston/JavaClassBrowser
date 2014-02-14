/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainBase;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import Models.ProjectModel;
import UIModels.ProjectManagerShellModel;
import UIShells.AddNewProjectShell;
import UIShells.ProjectManagerShell;
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
public class MainApplicationTest extends BaseTest {
    private MainApplication main;
    
    public MainApplicationTest() {
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
        main.setUserName("Barry Allen");
    }
    
    @After
    public void tearDown() {
        main = null;
    }

    @Test
    public void testConstructor(){
        System.out.println("testConstructor");
        ArrayList mainProjects = (ArrayList)this.getVariableFromClass(main, "projects");
        assertEquals(ArrayList.class, mainProjects.getClass());
        assertEquals(0, mainProjects.size());
        String mainUserName = (String)this.getVariableFromClass(main, "userName");
        assertEquals(String.class, mainUserName.getClass());
        assertEquals("Barry Allen", mainUserName);
        ArrayList mainOpenWindowModels = (ArrayList)this.getVariableFromClass(main, "openWindowModels");
        assertEquals(ArrayList.class, mainOpenWindowModels.getClass());
        assertEquals(0, mainOpenWindowModels.size());
        //assertEquals(ProjectManagerShellModel.class,mainOpenWindowModels.get(0).getClass());
    }
    @Test
    public void testGetProjects() {
        ProjectModel newProject = null;
        System.out.print("Test getProjects");
        assertEquals(0, main.getProjects().size());
        try {
            newProject = main.addProject(new ProjectModel(main, "new project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
        System.out.println(" passed");
        try {
            main.removeProject(newProject);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(0, main.getProjects().size());
    }
    
    @Test
    public void testAddProject_projectModel(){
        System.out.print("test addProject from projectModel");
        ProjectModel newProject = new ProjectModel(main, "new project");
        try {
            assertEquals(newProject, main.addProject(newProject));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
        System.out.println(" passed");
    }
    
    @Test
    public void testAddProject_stringProjectName(){
        ProjectModel newProject = null;
        try {
            newProject = main.addProject(new ProjectModel(main,"new project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
        try {
            main.addProject(new ProjectModel(main, "new project"));
            fail("exception not thrown");
        } catch (NameAlreadyExistsException ex) {}
        System.out.println(" passed");
    }
    
    @Test
    public void testAddProjectUpdatesShell(){
        ProjectManagerShellModel shellModel = 
                (ProjectManagerShellModel)this.getVariableFromClass(main, "shellModel");
        ProjectManagerShell shell = 
                (ProjectManagerShell)this.getVariableFromClass(shellModel, "shell");
        //get the project list model from the shell
        //add a project to main
        //assert that the shells project list updates when projects are added/removed from main
    }
    
    @Test
    public void testRemoveProject(){
        System.out.print("test remove project");
        ProjectModel aProject = new ProjectModel(main, "a project");
        try {
            main.removeProject(aProject);
            fail("exception not thrown");
        } catch (DoesNotExistException ex) {}
        try {
            main.addProject(aProject);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        try {
            main.removeProject(aProject);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        System.out.println(" passes");
    }
    
    @Test
    public void testOKToDeleteProject(){
        System.out.print("test okToDelete");
        ProjectModel aProject = new ProjectModel(main, "a project");
        assertFalse(main.okToDelete(aProject));
        try {
            main.addProject(aProject);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(main.okToDelete(aProject));
        System.out.println(" passed");
    }
    
    @Test
    public void testOKToAddProject(){
        assertTrue(main.okToAdd("new project"));
        try {
            main.addProject(new ProjectModel(main,"new project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(main.okToAdd("new project"));
        assertTrue(main.okToAdd("another project"));
        try {
            main.addProject(new ProjectModel(main,"another project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(main.okToAdd("another project"));
    }
    
    @Test
    public void testUserName(){
        System.out.print("test user name");
        main.setUserName("Kyle Raynor");
        assertEquals("Kyle Raynor", main.getUserName());
        System.out.println(" passed");
        main = new MainApplication();
        assertEquals(System.getProperty("user.name"), main.getUserName());
    }
}
