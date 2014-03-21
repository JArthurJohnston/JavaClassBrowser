/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ProjectModel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        main.openProjectSelectionShell();
        shell = (ProjectSelectionShell)((ArrayList)this.getVariableFromClass(main, "openWindowShells")).get(0);
        projectList = (DefaultListModel)this.getVariableFromClass(shell, "projects");
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
    
    private void refreshShell(){
        shell.signalClosedAndDispose();
        main.openProjectSelectionShell();
        shell = (ProjectSelectionShell)((ArrayList)this.getVariableFromClass(main, "openWindowShells")).get(0);
        projectList = (DefaultListModel)this.getVariableFromClass(shell, "projects");
        assertEquals(projectList.size(), main.getProjects().size());
    }

    @Test
    public void testInitializedFields() {
        assertEquals(main, this.getVariableFromClass(shell, "main"));
        JButton removeButton = (JButton)this.getVariableFromClass(shell, "removeProjectButton");
        assertTrue(shell.isVisible());
        assertTrue(projectList.isEmpty());
        assertFalse(removeButton.isEnabled());
        this.setUpMainProjects();
        this.refreshShell();
        removeButton = (JButton)this.getVariableFromClass(shell, "removeProjectButton");
        assertTrue(removeButton.isEnabled());
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
        this.refreshShell();
        JList list = (JList)this.getVariableFromClass(shell, "projectSelectionList");
        assertTrue(list.isVisible());
        assertEquals(DefaultListModel.class, list.getModel().getClass());
        assertEquals(2, main.getProjects().size());
        assertEquals(2, list.getModel().getSize());
        assertEquals(main.getProjects().get(0), list.getModel().getElementAt(0));
        assertEquals(main.getProjects().get(1), list.getModel().getElementAt(1));
        assertEquals(list.getSelectedValue(), main.getProjects().get(0)); 
        list.setSelectedIndex(1);
        assertEquals(main.getProjects().get(1), main.getSelectedProject());
    }
    
    @Test
    public void testRemoveProjectButton(){
        JButton removeButton = (JButton)this.getVariableFromClass(shell, "removeProjectButton");
        assertFalse(removeButton.isEnabled());
        try {
            main.addProject(new ProjectModel(main, "a project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, projectList.getSize());
        assertTrue(main.getSelectedProject() != null);
        assertTrue(removeButton.isEnabled());
        removeButton.doClick();
        assertTrue(projectList.isEmpty());
        assertTrue(main.getProjects().isEmpty());
        assertEquals(null, main.getSelectedProject());
        assertEquals(0, projectList.getSize());
        assertFalse(removeButton.isEnabled());
        this.setUpMainProjects();
        this.refreshShell();
        assertEquals(2, main.getProjects().size());
        removeButton = (JButton)this.getVariableFromClass(shell, "removeProjectButton");
        assertTrue(removeButton.isEnabled());
        removeButton.doClick();
        assertEquals(1, main.getProjects().size());
        assertTrue(removeButton.isEnabled());
    }
    
    @Test
    public void testAddingProjectToMainFillsList(){
        JList list = (JList)this.getVariableFromClass(shell, "projectSelectionList");
        assertEquals(0, list.getModel().getSize());
        ProjectModel aProject = null;
        try {
            aProject = main.addProject(new ProjectModel(main, "a project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(aProject, list.getModel().getElementAt(0));
        assertEquals(aProject, list.getSelectedValue());
        try {
            aProject = main.addProject(new ProjectModel(main, "another project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, list.getModel().getSize());
        assertEquals(aProject, list.getModel().getElementAt(1));
        assertEquals(aProject, list.getSelectedValue());
    }
    
    @Test
    public void testRemoveProjectUpdatesList(){
        this.setUpMainProjects();
        this.refreshShell();
        JList list = (JList)this.getVariableFromClass(shell, "projectSelectionList");
        assertEquals(2, list.getModel().getSize());
        list.setSelectedIndex(0);
        ProjectModel projectToBeRemoved = (ProjectModel)list.getSelectedValue();
        try {
            main.removeProject(projectToBeRemoved);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, list.getModel().getSize());
        assertFalse(main.getProjects().contains(projectToBeRemoved));
        
    }
    
    @Override
    public void testCloseAndDispose(){
        assertTrue(((ArrayList)this.getVariableFromClass(main, "openWindowShells")).contains(shell));
        shell.signalClosedAndDispose();
        assertFalse(((ArrayList)this.getVariableFromClass(main, "openWindowShells")).contains(shell));
        //could probably just push this up
        //though, that would mean id have to write a base shell test class.
    }
}
