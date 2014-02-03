/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ProjectModel;
import UIShells.ProjectManagerShell;
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
        model = new ProjectManagerShellModel(main);
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
    }
    @Test
    public void testConstructor_mainWithpreExistingProjects(){
        System.out.println("test constructor(main) with pre-existing projects");
        ProjectModel expSelected = main.addProject(new ProjectModel(main, "first"));
        main.addProject(new ProjectModel(main, "second"));
        this.setUpModel();
        assertEquals(expSelected, model.getSelected());
        assertEquals(2, model.getListModel().size());
    }
    
    @Test
    public void testGetAndSetSelected(){
        System.out.println("test get selected");
        assertEquals(null, model.getSelected());
        try {
            main.addProject("new Project");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ProjectManagerShellModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testSetUserName(){
        model.setUserName("Kyle Raynor");
        assertEquals("Kyle Raynor", model.getSelected().getUserName());
    }
    
    @Test
    public void testGetApplication(){
        assertEquals(main, model.getApplication());
    }
}
