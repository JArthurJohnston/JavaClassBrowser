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
import UIModels.SystemBrowserShellModel;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JMenuItem;
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
        SystemBrowserShellModel aModel = aMain.openSystemBrowser();
        shell = aModel.getShell();
    }
    
    private ProjectModel project(){
        return this.main().getProjects().get(0);
    }
    private MainApplication main(){
        return ((MainApplication)this.getVariableFromClass(this.model(), "main"));
    }
    private SystemBrowserShellModel model(){
        return((SystemBrowserShellModel)this.getVariableFromClass(shell, "model"));
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
    public void testAddPackage(){
        try {
            this.model().addPackage(new PackageModel(this.main().getSelectedProject(), "a Package"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        JList projects = (JList)this.getVariableFromClass(shell, "packageList");
        assertEquals(3, projects.getModel().getSize());
    }
}
