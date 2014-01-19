/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import MainBase.MainApplication;
import Models.ProjectModel;
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
public class ProjectManagerShellTest extends BaseUIShellTest{
    private ProjectManagerShell window;
    private MainApplication testMain;
    
    public ProjectManagerShellTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testMain = new MainApplication();
        window = new ProjectManagerShell(testMain);
        window.setVisible(true);
    }
    
    @After
    public void tearDown() {
        window.dispose();
    }

    @Test
    public void testIsVisible(){
        /*
         * hmmmmm, should I assert that each component is doing what it should
         * be doing. Or should I only test components whose properties
         * could change during runtime...
         */
        assertTrue(window.isShowing());
        //assertTrue(this.getComponentWithName(window, "projectList").isShowing());
        assertTrue(window.projectList().isShowing());
    }
    
    private void setUpProjects(){
        testMain.addProject(new ProjectModel("first"));
        testMain.addProject(new ProjectModel("second"));
        testMain.addProject(new ProjectModel("third"));
        testMain.addProject(new ProjectModel("fourth"));
    }
    
    @Test
    public void testInitialize(){
        assertEquals(MainApplication.class, window.getApplication().getClass());
        assertEquals(testMain, window.getApplication());
        assertEquals(testMain.getProjects(), window.getProjects());
        assertEquals(0, window.getProjects().size());
        this.setUpProjects();
        assertEquals(4, window.getProjects().size());
        assertEquals(testMain.getProjects().size(), window.getProjects().size());
        assertEquals("first", window.getProjects().get(0).name());
        assertEquals("second", window.getProjects().get(1).name());
        assertEquals("third", window.getProjects().get(2).name());
        assertEquals("fourth", window.getProjects().get(3).name());
    }
}
