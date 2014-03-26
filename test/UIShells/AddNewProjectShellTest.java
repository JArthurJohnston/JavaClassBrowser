/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ProjectModel;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
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
        main.setUserName("Kyle Raynor");
        shell = new AddNewProjectShell(main);
    }
    
    @After
    public void tearDown() {
        shell.signalClosedAndDispose();
        main = null;
        shell = null;
    }

    /**
     * Test of main method, of class AddNewProjectShell.
     */
    @Test
    public void testConstructor() {
        System.out.println("testConstructor");
        assertTrue(shell.isVisible());
        JTextField testProjectName = (JTextField)this.getVariableFromClass(shell, "projectNameField");
        assertTrue(testProjectName.isVisible());
        assertTrue(testProjectName.isEditable());
        assertEquals("New Project", testProjectName.getText());
        JTextField testAuthorName = (JTextField)this.getVariableFromClass(shell, "authorNameField");
        assertTrue(testAuthorName.isVisible());
        assertTrue(testAuthorName.isEditable());
        assertEquals(main.getUserName(),testAuthorName.getText());
        JLabel testProjectInfo = (JLabel)this.getVariableFromClass(shell, "projectInfoField");
        String expectedString = "Project Name: New Name\nAuthor: Kyle Raynor\n";
        //assertEquals(expectedString, testProjectInfo.getText());
    }
    
    @Test
    public void testNewProjectModel(){
        ProjectModel baseNewProject = (ProjectModel)this.getVariableFromClass(shell, "newProject");
        assertEquals(ProjectModel.class, baseNewProject.getClass());
        assertEquals(main.getUserName(), baseNewProject.getUserName());
    }
    
    @Test
    public void testCreateProjectButton(){
        ProjectModel baseNewProject = (ProjectModel)this.getVariableFromClass(shell, "newProject");
        JButton createButton = (JButton)this.getVariableFromClass(shell, "createProjectButton");
        assertTrue(createButton.isVisible());
        createButton.doClick();
        assertTrue(main.getProjects().contains(baseNewProject));
        assertFalse(shell.isVisible());
    }
    @Test
    public void testIsProjectValid(){
        JTextField projectName = (JTextField)this.getVariableFromClass(shell, "projectNameField");
        JButton createButton = (JButton)this.getVariableFromClass(shell, "createProjectButton");
        projectName.setText("");
        createButton.doClick();
        assertTrue(shell.isVisible());
        assertTrue(main.getProjects().isEmpty());
        projectName.setText("Some project");
        createButton.doClick();
        assertFalse(shell.isVisible());
        assertEquals(1, main.getProjects().size());
    }
    
    @Test
    public void testCloseAndDispose(){
        ArrayList shells = (ArrayList)this.getVariableFromClass(main, "openWindowShells");
        JButton cancel = (JButton)this.getVariableFromClass(shell, "cancelProjectButton");
        shells.add(shell);
        cancel.doClick();
        assertFalse(shells.contains(shell));
        assertTrue(shells.isEmpty());
    }
    
    @Test
    public void testCancelButton(){
        JButton cancel = (JButton)this.getVariableFromClass(shell, "cancelProjectButton");
        ProjectModel aProject = (ProjectModel)this.getVariableFromClass(shell, "newProject");
        cancel.doClick();
        assertFalse(main.getProjects().contains(aProject));
        assertFalse(shell.isVisible());
        assertFalse(((ArrayList)this.getVariableFromClass(main, "openWindowShells")).contains(shell));
    }
    
    @Test
    public void testChangeProjectNameFieldUpdatesProject(){
        ProjectModel newProject = (ProjectModel)this.getVariableFromClass(shell, "newProject");
        JTextField projectName = (JTextField)this.getVariableFromClass(shell, "projectNameField");
        projectName.setText("project with new name");
        assertEquals("project with new name", newProject.name());
    }
    @Test
    public void testChangeAuthorFieldUpdatesProject(){
        ProjectModel newProject = (ProjectModel)this.getVariableFromClass(shell, "newProject");
        JTextField author = (JTextField)this.getVariableFromClass(shell, "authorNameField");
        author.setText("Hal Jordan");
        assertEquals("Hal Jordan", newProject.getUserName());
    }
}
