/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Exceptions.NothingSelectedException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.ProjectModel;
import UIShells.AddNewProjectShell;
import UIShells.ProjectManagerShell;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
public class ProjectManagerShellModelTest extends BaseTest{
    private MainApplication main;
    private ProjectManagerShellModel model;
    
    public ProjectManagerShellModelTest() {
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
        this.setUpModel();
    }
    
    @After
    public void tearDown() {
        main = null;
        model = null;
    }
    
    private void setUpModel(){
        model = (ProjectManagerShellModel)this.getVariableFromClass(main, "shellModel");
    }
    
    private ProjectModel setUpProject(){
        ProjectModel aProject = null;
        try {
            aProject = model.addProject("New Project");
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        ClassModel aClass;
        this.addPackageToProject("New Package", aProject);
        return aProject;
    }

    @Test
    public void testConstructor_main() {
        // used to be called 'testInitialize'
        System.out.println("test constructor(main)");
        assertEquals(main, this.getVariableFromClass(model, "main"));
        assertEquals(null, this.getVariableFromClass(model, "selected"));
        DefaultListModel listModel = (DefaultListModel)this.getVariableFromClass(model, "projectList");
        assertEquals(DefaultListModel.class, listModel.getClass());
        assertEquals(0,listModel.size());
        ProjectManagerShell shell = (ProjectManagerShell)this.getVariableFromClass(model, "shell");
        assertEquals(ProjectManagerShell.class, shell.getClass());
        assertTrue(shell.isVisible());
        assertEquals(main.getUserName(), model.getUserName());
        ArrayList openShells = (ArrayList)this.getVariableFromClass(model, "openShells");
        assertEquals(0, openShells.size());
        ArrayList openShellModels = (ArrayList)this.getVariableFromClass(model, "openShellModels");
        assertEquals(ArrayList.class, openShellModels.getClass());
    }
    @Test
    public void testConstructor_mainWithpreExistingProjects(){
        ProjectModel expSelected = null;
        try {
            expSelected = main.addProject(new ProjectModel(main, "first"));
            main.addProject(new ProjectModel(main, "second"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        this.setUpModel();
        assertEquals(expSelected, model.getSelected());
        assertEquals(2, model.getListModel().size());
    }
    
    @Test
    public void testGetAndSetSelected(){
        System.out.println("test get selected");
        assertEquals(null, model.getSelected());
        try {
            main.addProject(new ProjectModel(main, "New Project"));
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ProjectManagerShellModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testSetUserName(){
        try {
            main.addProject(new ProjectModel(main,"A Project"));
        } catch (NameAlreadyExistsException ex) {
            fail("exception thrown when it shouldnt");
            Logger.getLogger(ProjectManagerShellModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.setUserName("Kyle Raynor");
        assertEquals("Kyle Raynor", model.getSelected().getUserName());
    }
    
    @Test
    public void testGetApplication(){
        assertEquals(main, model.getApplication());
    }
    
    @Test
    public void testOkToOpen(){
        assertTrue(model.okToOpen(AddNewProjectShell.class));
        model.openAddProject();
        AddNewProjectShell newShell = (AddNewProjectShell)((ArrayList)this.getVariableFromClass(model, "openShells")).get(0);
        JButton cancelButton = (JButton)this.getVariableFromClass(newShell, "cancelProjectButton");
        assertFalse(model.okToOpen(AddNewProjectShell.class));
        cancelButton.doClick();
        assertTrue(model.okToOpen(AddNewProjectShell.class));
    }
    @Test
    public void testRemoveProject(){
        model.removeProject(null);
        fail("not yet written");
    }
    
    @Test
    public void testOpenClassBrowser(){
        try {
            model.openClassBrowser();
            fail("exception not thrown");
        } catch (NothingSelectedException ex) {
        }
        ProjectModel aProject = this.setUpProject();
        this.setUpModel();
        ArrayList shellModels = (ArrayList)this.getVariableFromClass(model, "openShellModels");
        try {
            model.setSelection(aProject);
        } catch (DoesNotExistException ex) {
            fail("exception thrown when it shouldnt "+ ex.getMessage());
        }
        try {
            model.openClassBrowser();
        } catch (NothingSelectedException ex) {
            fail("exception thrown when it shouldnt "+ ex.getMessage());
        }
        assertEquals(1, shellModels.size());
    }
}
