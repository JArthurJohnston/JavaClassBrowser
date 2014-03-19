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
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
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
        assertTrue(shell.isVisible());
        assertTrue(projectList.isEmpty());
        this.setUpMainProjects();
        this.setUpShell();
        assertTrue(shell.isVisible());
        assertEquals(2, projectList.size());
        assertEquals(ProjectModel.class, projectList.get(0).getClass());
        ProjectModel aProject = (ProjectModel)projectList.get(0);
        this.compareStrings("a project", aProject.name());
        aProject = (ProjectModel)projectList.get(1);
        this.compareStrings("another project", aProject.name());
    }
    
    @Test
    public void testProjectSelectionList(){
        this.setUpMainProjects();
        this.setUpShell();
        JList list = (JList)this.getVariableFromClass(shell, "projectSelectionList");
        assertTrue(list.isVisible());
        assertEquals(DefaultListModel.class, list.getModel().getClass());
        assertEquals(2, main.getProjects().size());
        assertEquals(2, list.getModel().getSize());
        assertEquals(list.getSelectedValue(), main.getProjects().get(0));
        list.setSelectedIndex(1);
        assertEquals(main.getProjects().get(1), main.getSelectedProject());
    }
    
    @Test 
    public void testAddProjectButton(){
        JButton addProjectButton = (JButton)this.getVariableFromClass(shell, "addProjectButton");
        ArrayList openWindows = (ArrayList)this.getVariableFromClass(main, "openWindowShells");
        /*
        normally opeWindowShells would also contain a reference to 
        ProjectSelectionShell. But for the purposes of this
        test, it can start off being empty.
        */
        assertTrue(openWindows.isEmpty());
        addProjectButton.doClick();
        assertEquals(1, openWindows.size());
        assertEquals(AddNewProjectShell.class, openWindows.get(0).getClass());
        addProjectButton.doClick();
        //assert that it can only open ONE newProjectShell
        assertEquals(1, openWindows.size());
    }
}
