/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels;

import Exceptions.AlreadyExistsException;
import Internal.Mocks.MockBrowserController;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIModels.BrowserUIController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class PackageTreePanelTest extends BaseTreePanelTest{
    private ProjectModel project;
    private PackageTreePanel panel;
    
    public PackageTreePanelTest() {
    }
    
    @Before
    public void setUp() {
        panel = new PackageTreePanel();
    }
    
    @After
    public void tearDown() {
        panel = null;
    }
    
    
    private ProjectModel setUpProject(){
        project = new ProjectModel("a project");
        try {
            project.addPackage(new PackageModel("a package"));
            PackageModel aPackage = project.addPackage(new PackageModel("another package"));
            project.addPackage(new PackageModel("and another package"));
            aPackage.addPackage(new PackageModel("some package"));
            aPackage.addPackage(new PackageModel("some other package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return project;
    }
    
    private BrowserUIController controller(){
        MockBrowserController c = new MockBrowserController();
        c.setProject(this.setUpProject());
        c.setSelected(project.getAllPackage());
        return c;
    }
    
    @Override
    protected PackageTreePanel getPanel(){
        return panel;
    }

    /**
     * Test of tree method, of class PackageTreePanel.
     */
    @Test
    public void testSetController() {
        panel.setModel(this.controller());
        this.verifyTreeSize(7);
    }
    
    @Test
    public void testPackageAddedToRoot(){
        panel.setModel(this.controller());
        PackageModel newPackage = null;
        try {
            newPackage = project.addPackage(new PackageModel("a Package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        panel.modelAdded(newPackage);
        this.verifyTreeSize(8);
        assertTrue(panel.contains(newPackage));
    }
    
    @Test
    public void testPackageAddedToPackage(){
        panel.setModel(this.controller());
        PackageModel newPackage = null;
        try {
            newPackage = project.getDefaultPackage()
                    .addPackage(new PackageModel("a Package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        panel.modelAdded(newPackage);
        this.verifyTreeSize(8);
        assertTrue(panel.contains(newPackage));
    }
    
    @Test
    public void testPackageRemoved(){
        panel.setModel(this.controller());
        PackageModel aPackage = project.findPackage("some package");
        assertTrue(panel.contains(aPackage));
        this.verifyTreeSize(7);
        
        panel.modelRemoved(aPackage);
        this.verifyTreeSize(6);
        assertFalse(panel.contains(aPackage));
    }
    
}
