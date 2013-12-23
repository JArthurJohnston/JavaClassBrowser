/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

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
public class WorkspaceModelTest {
    private WorkspaceModel instance;
    
    public WorkspaceModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new WorkspaceModel();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testAddProject() {
        ProjectModel newProject = new ProjectModel("New Project", instance);
        instance.addProject(newProject);
        assertTrue( instance.projects().contains(newProject));
        assertEquals(instance, newProject.workspace());
    }
}
