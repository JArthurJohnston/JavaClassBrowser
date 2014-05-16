/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import Internal.Mocks.MockBrowserController;
import Internal.Mocks.MockClassModel;
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
public class ClassTreePanelTest extends BaseTest{
    private ClassTreePanel panel;
    
    public ClassTreePanelTest() {
    }
    
    @Before
    public void setUp() {
        panel = new ClassTreePanel();
    }
    
    @After
    public void tearDown() {
        panel = null;
    }
    
    private BrowserUIController controller(){
        MockBrowserController c = new MockBrowserController();
        ClassModel aClass = new ClassModel("AClass");
        c.setSelected(aClass);
        try {
            PackageModel aPackage = new ProjectModel("a project")
                    .addPackage(new PackageModel("SomePackage"));
            
            aPackage.addClass(aClass);
            aClass.addClass(new ClassModel("ASubClass"));
            aClass.addClass(new ClassModel("AnotherSubClass"));
            aClass.addClass(new ClassModel("YetAnotherSubClass"));
            aPackage.addClass(new ClassModel("AnotherClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        c.setSelected(aClass);
        assertEquals(aClass, c.getSelectedClass());
        return c;
    }
    
    private void verifyTreeSize(int expected){
        assertEquals(expected, panel.getTreeSize()); //nodes in the tree
        assertEquals(expected, panel.getHashSize()); //nodes in the hash
    }
    
    @Test
    public void testSetControllerWithSelectedClass(){
        panel = new ClassTreePanel();
        assertTrue(panel.isEmpty());
        panel.setModel(this.controller());
        this.verifyTreeSize(4);
    }
    
    @Test 
    public void testSetControllerWithSelectedPackage(){
        panel = new ClassTreePanel();
        BrowserUIController controller = this.controller();
        PackageModel aPackage = controller.getSelectedClass().getParentPackage();
        controller.setSelected(aPackage);
        
        panel.setModel(controller);
        //this.showPanel(panel);
        this.verifyTreeSize(5);
    }

    @Test
    public void testGetSelected() {
        BrowserUIController controller = this.controller();
        panel.setModel(controller);
        panel.setSelectedIndex(0);
        assertEquals("AnotherSubClass", controller.getSelected().name());
    }

    @Test
    public void testModelAdded() {
        fail();
    }
    
}
