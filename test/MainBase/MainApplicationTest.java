/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainBase;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Models.ProjectModel;
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
public class MainApplicationTest {
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
    }
    
    @After
    public void tearDown() {
        main = null;
    }

    /**
     * Test of getProjects method, of class MainApplication.
     */
    @Test
    public void testGetProjects() {
        System.out.print("Test getProjects");
        assertEquals(0, main.getProjects().size());
        System.out.println(" passed");
    }
    
    @Test
    public void testAddProject_projectModel(){
        System.out.print("test addProject from projectModel");
        ProjectModel newProject = new ProjectModel(main, "new project");
        assertEquals(newProject, main.addProject(newProject));
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
        System.out.println(" passed");
    }
    
    @Test
    public void testAddProject_stringProjectName(){
        System.out.print("test addProject from string");
        ProjectModel newProject = null;
        try {
            newProject = main.addProject("new project");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(MainApplicationTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("exception thrown when it shouldnt");
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
        try {
            main.addProject("new project");
            fail("exception not thrown");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(MainApplicationTest.class.getName()).log(Level.FINE, null, ex);
        }
        System.out.println(" passed");
    }
    
    @Test
    public void testRemoveProject(){
        System.out.print("test remove project");
        ProjectModel aProject = new ProjectModel(main, "a project");
        try {
            main.removeProject(aProject);
            fail("exception not thrown");
        } catch (DoesNotExistException ex) {
            Logger.getLogger(MainApplicationTest.class.getName()).log(Level.FINE, null, ex);
        }
        main.addProject(aProject);
        try {
            main.removeProject(aProject);
        } catch (DoesNotExistException ex) {
            Logger.getLogger(MainApplicationTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("exception thrown when it shouldnt");
        }
        System.out.println(" passes");
    }
    
    @Test
    public void testOKToDeleteProject(){
        System.out.print("test okToDelete");
        ProjectModel aProject = new ProjectModel(main, "a project");
        assertFalse(main.okToDelete(aProject));
        main.addProject(aProject);
        assertTrue(main.okToDelete(aProject));
        System.out.println(" passed");
    }
    
    @Test
    public void testOKToAddProject(){
        System.out.print("test okToAdd");
        assertTrue(main.okToAdd("new project"));
        try {
            main.addProject("new project");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(MainApplicationTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("exception thrwon when it shouldnt");
        }
        assertFalse(main.okToAdd("new project"));
        assertTrue(main.okToAdd("another project"));
        try {
            main.addProject("another project");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(MainApplicationTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("exception thrwon when it shouldnt");
        }
        assertFalse(main.okToAdd("another project"));
        System.out.println(" passed");
    }
    
    @Test
    public void testUserName(){
        System.out.print("test user name");
        assertEquals(System.getProperty("user.name"), main.getUserName());
        main.setUserName("Kyle Raynor");
        assertEquals("Kyle Raynor", main.getUserName());
        System.out.println(" passed");
    }
}
