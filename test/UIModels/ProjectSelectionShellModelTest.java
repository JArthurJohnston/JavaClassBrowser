/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels;

import Exceptions.NameAlreadyExistsException;
import Models.ProjectModel;
import UIShells.ProjectSelectionShell;
import java.util.ArrayList;
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
        assertTrue(((DefaultListModel)this.getVariableFromClass(model, "projectList")).isEmpty());
    }
    
    @Test
    public void testAddProjectToMainUpdatesModel(){
        DefaultListModel projects = (DefaultListModel)this.getVariableFromClass(model, "projectList");
        assertTrue(projects.isEmpty());
        ProjectModel aProject = null;
        try {
            aProject = main.addProject(new ProjectModel(main, "a project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, projects.size());
        assertEquals(aProject, projects.get(0));
        try {
            aProject = main.addProject(new ProjectModel(main, "another project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, projects.size());
        assertEquals(aProject, projects.get(1));
    }
    
    @Test
    public void testAddDuplicateProjectDoesNOTUpdateModel(){
        DefaultListModel projects = (DefaultListModel)this.getVariableFromClass(model, "projectList");
        ProjectModel aProject = null;
        try {
            aProject = main.addProject(new ProjectModel(main, "a project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, projects.size());
        assertEquals(aProject, projects.get(0));
        try {
            main.addProject(new ProjectModel(main, "a project"));
            fail("Exception not thrown");
        } catch (NameAlreadyExistsException ex) {
        }
        assertEquals(1, projects.size());
        assertEquals(aProject, projects.get(0));
    }
    
}
