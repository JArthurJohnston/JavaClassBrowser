/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Models.ProjectModel;
import UIShells.ProjectSelectionShell;
import java.util.ArrayList;
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
public class ProjectSelectionShellModelTest extends BaseUIModelTest{
    private ProjectSelectionShell shell;
    private ProjectSelectionShellModel model;
    
    public ProjectSelectionShellModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        model = main.openProjectSelectionShell();
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }
    
    private void refreshModel(){
        model.close();
        model = main.openProjectSelectionShell();
    }

    @Test
    public void testInitializedFields() {
        assertEquals(null, this.getVariableFromClass(model, "shell"));
        assertEquals(main, this.getVariableFromClass(model, "main"));
        assertTrue(((ArrayList)
                (this.getVariableFromClass(main, "openWindowModels"))).contains(model));
    }
    
    @Test
    public void testShell(){
        shell = model.showShell();
        assertEquals(ProjectSelectionShell.class, shell.getClass());
        assertTrue(shell.isVisible());
        fail("shell needs to be re-factored");
    }
    
    @Override
    public void testFillListModel(){
        assertTrue(model.getListModel().isEmpty());
    }
    
    @Test
    public void testAddProject(){
        DefaultListModel projects = model.getListModel();
        assertTrue(projects.isEmpty());
        ProjectModel aProject = null;
        try {
            model.addProject(aProject = new ProjectModel(main, "a project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, projects.size());
        assertEquals(aProject, projects.get(0));
        try {
            model.addProject(aProject = new ProjectModel(main, "another project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, projects.size());
        assertEquals(aProject, projects.get(1));
    }
    
    @Test
    public void testAddDuplicateProject(){
        DefaultListModel projects = model.getListModel();
        ProjectModel aProject = null;
        try {
            model.addProject(aProject = new ProjectModel(main, "a project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, projects.size());
        assertEquals(aProject, projects.get(0));
        try {
            model.addProject(new ProjectModel(main, "a project"));
            fail("Exception not thrown");
        } catch (NameAlreadyExistsException ex) {
        }
        assertEquals(1, projects.size());
        assertEquals(aProject, projects.get(0));
    }
    
    
    @Test
    public void testAddProjectsSetsSelected(){
        DefaultListModel projects = model.getListModel();
        ProjectModel aProject = null;
        try {
            model.addProject(aProject = new ProjectModel(main, "a project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(aProject, main.getSelectedProject());
        try {
            model.addProject(new ProjectModel(main, "a project"));
            fail("Exception not thrown");
        } catch (NameAlreadyExistsException ex) {
        }
        assertEquals(aProject, main.getSelectedProject());
    }
    
    @Test
    public void testRemoveProject(){
        DefaultListModel projects = model.getListModel();
        ProjectModel aProject = null;
        try {
            aProject = model.addProject(new ProjectModel(main, "aProject"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(projects.contains(aProject));
        try {
            model.removeProject(aProject);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertFalse(projects.contains(aProject));
    }
    
    @Test
    public void testRemoveProjectFromMainUpdatesModel(){
        DefaultListModel projects = model.getListModel();
        ProjectModel aProject = null;
        try {
            aProject = main.addProject(new ProjectModel(main, "a project"));
            main.removeProject(aProject);
        } catch (NameAlreadyExistsException | DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertTrue(projects.isEmpty());
        assertFalse(projects.contains(aProject));
    }
    
    @Test
    public void testRemoveProjectPropogatesException(){
        try {
            model.removeProject(new ProjectModel(main, "a project"));
            fail("exception not thrown");
        } catch (DoesNotExistException ex) {
            assertEquals(DoesNotExistException.class, ex.getClass());
        }
    }
    
    @Test 
    public void testRemoveProjectChangesSelected(){
        ProjectModel aProject = null;
        assertNull(main.getSelectedProject());
        try {
            aProject = model.addProject(new ProjectModel(main, "a project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(aProject, main.getSelectedProject());
        try {
            model.removeProject(aProject);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertNull(main.getSelectedProject());
        ProjectModel anotherProject = null;
        try {
            model.addProject(aProject);
            anotherProject = model.addProject(new ProjectModel(main, "another project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(aProject, main.getSelectedProject());
        try {
            model.removeProject(aProject);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(anotherProject, main.getSelectedProject());
    }
    
    @Test
    public void testSetSelected(){
        ProjectModel aProject =  model.setSelectedProject(new ProjectModel(main, "aProject"));
        assertEquals(aProject, main.getSelectedProject());
    }
    
    @Test
    public void testCloseAndDispose(){
        shell = model.showShell();
        assertTrue(shell.isVisible());
        model.close();
        assertFalse(((ArrayList)this.getVariableFromClass(main, "openWindowModels")).contains(model));
        assertFalse(shell.isVisible());
    }
    
    @Test
    public void testGetListModel(){
        assertEquals(DefaultListModel.class, model.getListModel().getClass());
        assertTrue(model.getListModel().isEmpty());
        try {
            main.addProject(new ProjectModel(main, "aProject"));
            main.addProject(new ProjectModel(main, "another Project"));
            main.addProject(new ProjectModel(main, "yet another Project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        this.refreshModel();
        assertEquals(DefaultListModel.class, model.getListModel().getClass());
        assertEquals(3, model.getListModel().size());
    }
}
