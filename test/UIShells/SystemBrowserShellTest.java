/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIModels.BrowserUIModel;
import UIPanels.BasePanel;
import UIPanels.SelectedClassPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
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
            aMain.setSelectedProejct(aMain.addProject(new ProjectModel("a project")));
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

    private JList getClassList(){
        SelectedClassPanel panel = 
                (SelectedClassPanel)this.getVariableFromClass(shell, "classBrowserPanel");
        return (JList)this.getVariableFromClass(panel, "classList");
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
    public void testAddPackageToProjectUpdatesShell(){
        JList packages = (JList)this.getVariableFromClass(shell, "packageList");
        assertEquals(2, packages.getModel().getSize());
        try {
            this.model().getSelectedProject().addPackage(new PackageModel("a Package"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(3, packages.getModel().getSize());
    }
    
    @Test
    public void testAddClassUpdatesShell(){
        JList classes = this.getClassList();
        assertEquals(0, classes.getModel().getSize());
        this.model().setSelected(model().getSelectedPackage());
        try {
            this.model().getSelectedPackage().addClass(new ClassModel("SomeClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, classes.getModel().getSize());
    }
    
    @Test
    public void testPanelsHaveModel(){
        BasePanel aPanel = (BasePanel)this.getVariableFromClass(shell, "modelEditPanel");
        BasePanel bPanel = (BasePanel)this.getVariableFromClass(shell, "classBrowserPanel");
        assertEquals(this.model(), this.getVariableFromClass(shell, "model"));
        assertEquals(this.model(), this.getVariableFromClass(bPanel, "model"));
        assertEquals(this.model(), this.getVariableFromClass(aPanel, "model"));
    }
    
    @Test
    public void testAddPackageUpdatesShell(){
        
    }
}
