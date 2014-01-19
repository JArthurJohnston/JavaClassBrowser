/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainBase;

import Models.ProjectModel;
import java.util.ArrayList;
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
        ProjectModel newProject = new ProjectModel("new project");
        assertEquals(newProject, main.addProject(newProject));
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
    }
    
    @Test
    public void testAddProject_stringProjectName(){
        ProjectModel newProject = main.addProject("new project");
    }
    
    @Test
    public void testOKToAddProject(){
        assertTrue(main.okToAdd("new project"));
        main.addProject("new project");
        assertFalse(main.okToAdd("new project"));
        assertTrue(main.okToAdd("another project"));
        main.addProject("another project");
        assertFalse(main.okToAdd("another project"));
    }
}
