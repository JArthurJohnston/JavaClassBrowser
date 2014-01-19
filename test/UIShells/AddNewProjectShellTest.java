/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import Internal.BaseTest;
import MainBase.MainApplication;
import UIModels.ProjectManagerShellModel;
import javax.swing.JTextField;
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
public class AddNewProjectShellTest extends BaseTest{
    private MainApplication main;
    private ProjectManagerShellModel model;
    private AddNewProjectShell shell;
    
    public AddNewProjectShellTest() {
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
        model = new ProjectManagerShellModel(main);
        shell = new AddNewProjectShell(model);
    }
    
    @After
    public void tearDown() {
        main = null;
        model = null;
        shell = null;
    }

    /**
     * Test of main method, of class AddNewProjectShell.
     */
    @Test
    public void testConstructor() {
        System.out.println("testConstructor");
        assertTrue(shell.isVisible());
        ProjectManagerShellModel shellModel = 
                (ProjectManagerShellModel)this.getVariableFromClass(shell, "model");
        assertEquals(model, shellModel);
        JTextField testProjectName = (JTextField)this.getVariableFromClass(shell, "projectNameField");
        assertTrue(testProjectName.isVisible());
        assertTrue(testProjectName.isEditable());
        assertEquals("New Project", testProjectName.getText());
        JTextField testAuthorName = (JTextField)this.getVariableFromClass(shell, "authorNameField");
        assertTrue(testAuthorName.isVisible());
        assertTrue(testAuthorName.isEditable());
        assertEquals(main.getUserName(),testAuthorName.getText());
    }
}
