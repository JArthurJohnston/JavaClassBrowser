/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.PackageModel;
import Models.ProjectModel;
import UIModels.BrowserUIModel;
import java.util.LinkedList;
import javax.swing.JList;
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
public class SystemBrowserShellTest extends BaseTest{
    private SystemBrowserShell shell;
    
    public SystemBrowserShellTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        MainApplication aMain = new MainApplication();
        try {
            aMain.setSelectedProejct(aMain.addProject(new ProjectModel(aMain, "a project")));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        BrowserUIModel aModel = aMain.openSystemBrowser();
        shell = aModel.getShell();
    }
    
    private ProjectModel project(){
        return this.main().getProjects().get(0);
    }
    private MainApplication main(){
        return ((MainApplication)this.getVariableFromClass(this.model(), "main"));
    }
    private BrowserUIModel model(){
        return((BrowserUIModel)this.getVariableFromClass(shell, "model"));
    }
    
    @After
    public void tearDown() {
        shell = null;
    }

    /**
     * Test of main method, of class SystemBrowserShell.
     */
    @Test
    public void testInitialPackages() {
        assertTrue(shell.isVisible());
        JList projects = (JList)this.getVariableFromClass(shell, "packageList");
        assertEquals(2, projects.getModel().getSize());
    }
    
    @Test
    public void testChildren(){
        LinkedList children = (LinkedList)this.getVariableFromClass(shell, "childPanels");
        assertEquals(LinkedList.class, children.getClass());
        assertEquals(3, children.size());
        assertTrue(children.contains(this.getVariableFromClass(shell, "classBrowserPanel")));
        assertTrue(children.contains(this.getVariableFromClass(shell, "packageList")));
        assertTrue(children.contains(this.getVariableFromClass(shell, "modelEditPanel")));
    }
    
    @Test
    public void testAddPackage(){
        try {
            this.model().addPackage(new PackageModel(this.main().getSelectedProject(), "a Package"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        JList projects = (JList)this.getVariableFromClass(shell, "packageList");
        assertEquals(3, projects.getModel().getSize());
    }
    
    @Test
    public void testPanelsHaveModel(){
        fail();
        //make sure each panel has it this model
    }
}
