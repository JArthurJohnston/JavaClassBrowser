/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainBase;

import Exceptions.DoesNotExistException;
import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import Models.ProjectModel;
import java.util.ArrayList;
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
public class MainApplicationTest extends BaseTest {
    private MainApplication main;
    
    public MainApplicationTest(){}
    
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
        assertEquals(0, main.getProjects().size());
        try {
            newProject = main.addProject(new ProjectModel("new project"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
        try {
            main.removeProject(newProject);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(0, main.getProjects().size());
    }
    
    @Test
    public void testAddProject_projectModel(){
        ProjectModel newProject = null;
        try {
            newProject = main.addProject(new ProjectModel("new project"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
    }
    
    @Test
    public void testAddProject_stringProjectName(){
        ProjectModel newProject = null;
        try {
            newProject = main.addProject(new ProjectModel("new project"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
        try {
            main.addProject(new ProjectModel("new project"));
            fail("exception not thrown");
        } catch (AlreadyExistsException ex) {}
    }
    
    
    @Test
    public void testRemoveProject(){
        ProjectModel aProject = null;
        assertTrue(main.getProjects().isEmpty());
        try {
            main.removeProject(aProject);
            fail("exception not thrown");
        } catch (DoesNotExistException ex) {
            //asserting the exception is thrown
        }
        try {
            main.addProject(new ProjectModel("a project"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(aProject, main.getProjects().get(0));
        try {
            main.removeProject(aProject);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertTrue(main.getProjects().isEmpty());
    }
    
    @Test
    public void testOKToDeleteProject(){
        ProjectModel aProject = null;
        assertFalse(main.okToDelete(aProject));
        try {
            aProject = main.addProject(new ProjectModel("a project"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(main.okToDelete(aProject));
    }
    
    @Test
    public void testOKToAddProject(){
        assertTrue(main.okToAdd("new project"));
        try {
            main.addProject(new ProjectModel("new project"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(main.okToAdd("new project"));
        assertTrue(main.okToAdd("another project"));
        try {
            main.addProject(new ProjectModel("another project"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(main.okToAdd("another project"));
    }
    
    @Test
    public void testUserName(){
        main.setUserName("Kyle Raynor");
        assertEquals("Kyle Raynor", main.getUserName());
        main = new MainApplication();
        assertEquals(System.getProperty("user.name"), main.getUserName());
    }
}
