/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseShellTest;
import MainBase.MainApplication;
import Models.ProjectModel;
import UIModels.ProjectSelectionModel;
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
public class ProjectSelectionShellTest extends BaseShellTest{
    private ProjectSelectionShell shell;
    private ProjectSelectionModel model;
    
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
        model = main.openProjectSelection();
        shell = model.showShell();
    }
    
    @After
    public void tearDown() {
        model.close();
        shell = null;
        model = null;
        main = null;
    }
    
    private ProjectSelectionShell refreshShell(){
        model.close();
        model = null;
        model = main.openProjectSelection();
        return model.showShell();
    }
    
    private void setUpMainWithProjects(){
        try {
            main.setSelectedProejct(main.addProject(new ProjectModel(main, "a project")));
            main.addProject(new ProjectModel(main, "another project"));
            main.addProject(new ProjectModel(main, "yet another project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        shell = this.refreshShell();
    }

    @Test
    public void testInitialProjectList() {
        JList projectList = (JList)this.getVariableFromClass(shell, "projectList");
        assertEquals(0, projectList.getModel().getSize());
        try {
            model.addProject(new ProjectModel(main, "a project"));
            model.addProject(new ProjectModel(main, "another project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        shell = this.refreshShell();
        projectList = (JList)this.getVariableFromClass(shell, "projectList");
        assertEquals(2, projectList.getModel().getSize());
    }
    
    @Test
    public void testSelectionUpdatesModelAndMain(){
        this.setUpMainWithProjects();
        JList projectList = (JList)this.getVariableFromClass(shell, "projectList");
        assertEquals(main.getSelectedProject(), projectList.getSelectedValue());
        projectList.setSelectedIndex(1);
        assertEquals(projectList.getSelectedValue(), main.getSelectedProject());
        assertEquals(main.getProjects().get(1), main.getSelectedProject());
        projectList.setSelectedIndex(0);
        assertEquals(projectList.getSelectedValue(), main.getSelectedProject());
        assertEquals(main.getProjects().get(0), main.getSelectedProject());
        projectList.setSelectedIndex(2);
        assertEquals(projectList.getSelectedValue(), main.getSelectedProject());
        assertEquals(main.getProjects().get(2), main.getSelectedProject());
    }
    
    @Test
    @Override
    public void testCloseAndDispose(){
        JButton remove = (JButton)this.getVariableFromClass(shell, "closeShellButton");
        remove.doClick();
        assertFalse(shell.isVisible());
        assertFalse(this.mainContainsModel(model));
    }
    
    @Test
    public void testRemoveProjectButton(){
        this.setUpMainWithProjects();
        ProjectModel aProject = model.getMain().getProjects().get(0);
        JButton remove = (JButton)this.getVariableFromClass(shell, "removeProjectButton");
        remove.doClick();
        assertFalse(model.getMain().getProjects().contains(aProject));
    }
    
    @Test 
    public void testRemoveProjectButtonDissablesEnables(){
        JButton remove = (JButton)this.getVariableFromClass(shell, "removeProjectButton");
        assertFalse(remove.isEnabled());
        this.setUpMainWithProjects();
        remove = (JButton)this.getVariableFromClass(shell, "removeProjectButton");
        assertTrue(remove.isEnabled());
    }
    
    @Test
    public void testAddProjectButton(){
        AddNewProjectShell addProjectShell = model.addProject();
        assertEquals(AddNewProjectShell.class, addProjectShell.getClass());
        assertTrue(addProjectShell.isVisible());
        addProjectShell.dispose();
    }
    
    @Test 
    public void testProjectInfo(){
        fail();
    }
    
}
