/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ProjectModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arthur
 */
public class ProjectSelectionShellTest extends BaseTest{
    private MainApplication main;
    private ProjectSelectionShell shell;
    private DefaultListModel projectList;
    
    public ProjectSelectionShellTest() {
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
        setUpShell();
    }
    
    @After
    public void tearDown() {
        main = null;
        shell = null;
    }
    
    private void setUpMainProjects(){
        try {
            main.addProject(new ProjectModel(main, "a project"));
            main.addProject(new ProjectModel(main, "another project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    private void setUpShell(){
        shell = new ProjectSelectionShell(main);
        projectList = (DefaultListModel)this.getVariableFromClass(shell, "projects");
    }

    @Test
    public void testInitializedFields() {
        assertTrue(projectList.isEmpty());
        this.setUpMainProjects();
        this.setUpShell();
        assertEquals(2, projectList.size());
        assertEquals(ProjectModel.class, projectList.get(0).getClass());
        ProjectModel aProject = (ProjectModel)projectList.get(0);
        this.compareStrings("a project", aProject.name());
        aProject = (ProjectModel)projectList.get(1);
        this.compareStrings("another project", aProject.name());
    }
}
