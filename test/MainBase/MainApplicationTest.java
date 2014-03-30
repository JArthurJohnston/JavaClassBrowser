/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainBase;

import Exceptions.DoesNotExistException;
import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import Models.ProjectModel;
import UIModels.ClassBrowserShellModel;
import UIShells.AddNewProjectShell;
import UIShells.BaseUIShell;
import UIShells.ClassBrowserShell;
import UIShells.ProjectSelectionShell;
import java.util.ArrayList;
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
public class MainApplicationTest extends BaseTest {
    private MainApplication main;
    
    public MainApplicationTest(){}
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        main = new MainApplication();
        main.setUserName("Barry Allen");
    }
    
    @After
    public void tearDown() {
        main = null;
    }

    @Test
    public void testConstructor(){
        ArrayList mainProjects = (ArrayList)this.getVariableFromClass(main, "projects");
        assertEquals(ArrayList.class, mainProjects.getClass());
        assertEquals(0, mainProjects.size());
        String mainUserName = (String)this.getVariableFromClass(main, "userName");
        assertEquals(String.class, mainUserName.getClass());
        assertEquals("Barry Allen", mainUserName);
        ArrayList mainOpenWindowModels = (ArrayList)this.getVariableFromClass(main, "openWindowModels");
        assertEquals(ArrayList.class, mainOpenWindowModels.getClass());
        assertEquals(0, mainOpenWindowModels.size());
        //assertEquals(ProjectManagerShellModel.class,mainOpenWindowModels.get(0).getClass());
    }
    
    @Test
    public void testGetProjects() {
        ProjectModel newProject = null;
        assertEquals(0, main.getProjects().size());
        try {
            newProject = main.addProject(new ProjectModel(main, "new project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
        try {
            main.removeProject(newProject);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(0, main.getProjects().size());
    }
    
    @Test
    public void testAddProject_projectModel(){
        ProjectModel newProject = new ProjectModel(main, "new project");
        try {
            assertEquals(newProject, main.addProject(newProject));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
    }
    
    @Test
    public void testAddProject_stringProjectName(){
        ProjectModel newProject = null;
        try {
            newProject = main.addProject(new ProjectModel(main,"new project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(newProject, main.getProjects().get(0));
        try {
            main.addProject(new ProjectModel(main, "new project"));
            fail("exception not thrown");
        } catch (NameAlreadyExistsException ex) {}
    }
    
    @Test
    public void testProjectSelectionShellConnections(){
        ArrayList openShells = (ArrayList)this.getVariableFromClass(main, "openWindowShells");
        main.openProjectSelection();
        assertEquals(1, openShells.size());
        assertEquals(ProjectSelectionShell.class, openShells.get(0).getClass());
        main.openProjectSelection();
        assertEquals(1, openShells.size());
        main.openAddProjectShell();
        assertEquals(2, openShells.size());
    }
    
    @Test
    public void testRemoveProject(){
        ProjectModel aProject = new ProjectModel(main, "a project");
        assertTrue(main.getProjects().isEmpty());
        try {
            main.removeProject(aProject);
            fail("exception not thrown");
        } catch (DoesNotExistException ex) {}
        try {
            main.addProject(aProject);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, main.getProjects().size());
        assertEquals(aProject, main.getProjects().get(0));
        try {
            main.removeProject(aProject);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertTrue(main.getProjects().isEmpty());
    }
    
    @Test
    public void testOKToDeleteProject(){
        ProjectModel aProject = new ProjectModel(main, "a project");
        assertFalse(main.okToDelete(aProject));
        try {
            main.addProject(aProject);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(main.okToDelete(aProject));
    }
    
    @Test
    public void testOKToAddProject(){
        assertTrue(main.okToAdd("new project"));
        try {
            main.addProject(new ProjectModel(main,"new project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(main.okToAdd("new project"));
        assertTrue(main.okToAdd("another project"));
        try {
            main.addProject(new ProjectModel(main,"another project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(main.okToAdd("another project"));
    }
    
    @Test
    public void testUserName(){
        main.setUserName("Kyle Raynor");
        assertEquals("Kyle Raynor", main.getUserName());
        main = new MainApplication();
        assertEquals(System.getProperty("user.name"), main.getUserName());
    }
    
    @Test
    public void testOpenAddProject(){
        ArrayList openShells = (ArrayList)this.getVariableFromClass(main, "openWindowShells");
        assertTrue(openShells.isEmpty());
        main.openAddProjectShell();
        assertEquals(1, openShells.size());
        main.openAddProjectShell();
        assertEquals(1, openShells.size());
    }
    
    @Test
    public void testAddProjectUpdatesShells(){
        fail();
    }
    
    @Test
    public void testAddShell(){
        BaseUIShell aShell;
        main.addShell(aShell = new AddNewProjectShell(main));
        assertTrue(((ArrayList)this.getVariableFromClass(main, "openWindowShells")).contains(aShell));
    }
    
    @Test
    public void testRemoveShell(){
        BaseUIShell aShell;
        main.addShell(aShell = new AddNewProjectShell(main));
        main.removeShell(aShell);
        assertFalse(((ArrayList)this.getVariableFromClass(main, "openWindowShells")).contains(aShell));
    }
    
    @Test
    public void testOpenAddClassBrowser(){
        ClassBrowserShellModel classBrowserModel = main.openAddClassBrowser();
        fail();
    }
    
    @Test
    public void testSelectedPackage(){
        fail();
    }
}
