/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ProjectModel;
import UIModels.ProjectManagerShellModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arthur
 */
public class ProjectManagerShellTest extends BaseTest{
    private ProjectManagerShell window;
    private ProjectManagerShellModel model;
    private MainApplication testMain;
    
    public ProjectManagerShellTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testMain = new MainApplication();
        model = new ProjectManagerShellModel(testMain);
        window = new ProjectManagerShell(model);
        window.setVisible(true);
    }
    
    @After
    public void tearDown() {
        window.dispose();
    }

    @Test
    public void testIsVisible(){
        System.out.print("test is visible");
        JList list = (JList)this.getVariableFromClass(window, "projectList");
        JTextField projectInfo = (JTextField)this.getVariableFromClass(window, "projectInfo");
        assertTrue(window.isShowing());
        assertTrue(list.isShowing());
        assertTrue(projectInfo.isVisible());
        System.out.println(" passed");
    }
    
    private void setUpProjects(){
        testMain.addProject(new ProjectModel(testMain,"first"));
        testMain.addProject(new ProjectModel(testMain,"second"));
        testMain.addProject(new ProjectModel(testMain,"third"));
        testMain.addProject(new ProjectModel(testMain,"fourth"));
        model = new ProjectManagerShellModel(testMain);
    }
    
    @Test
    public void testInitialize(){
        System.out.print("test initialize");
        JList projectList = (JList)this.getVariableFromClass(window, "projectList");
        ProjectManagerShellModel aModel = (ProjectManagerShellModel)this.getVariableFromClass(window, "model");
        JButton addButton = (JButton)getVariableFromClass(window, "addProject");
        
        assertEquals(ProjectManagerShellModel.class, aModel.getClass());
        assertEquals(model, aModel);
        assertEquals(0, projectList.getModel().getSize());
        assertTrue(projectList.isVisible());
        assertTrue(window.isVisible());
        assertTrue(addButton.isVisible());
        
        
        this.setUpProjects();
        window = new ProjectManagerShell(model);
        projectList = (JList)this.getVariableFromClass(window, "projectList");
        
        assertEquals(4, projectList.getModel().getSize());
        
        projectList.setSelectedIndex(0);
        
        assertEquals(ProjectModel.class, projectList.getSelectedValue().getClass());
        assertEquals("first", projectList.getSelectedValue().toString());
        
        ProjectModel aProject = testMain.getProjects().get(0);
        JTextField projectInfo = (JTextField)this.getVariableFromClass(window, "projectInfo");
        
        assertFalse(projectInfo.isEditable());
        //System.out.println(aProject.getDescription());
        //assertEquals(projectInfo.getText(), aProject.getDescription());
        System.out.println(" passed");
    }
    
}
