/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels;

import Exceptions.AlreadyExistsException;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIModels.BrowserUIController;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ClassTreePanelTest {
    private ClassTreePanel panel;
    
    public ClassTreePanelTest() {
    }
    
    @Before
    public void setUp() {
        panel = new ClassTreePanel();
        panel.setModel(this.controller());
    }
    
    @After
    public void tearDown() {
        panel = null;
    }
    
    private BrowserUIController controller(){
        BrowserUIController c = new BrowserUIController();
        ProjectModel aProject = new ProjectModel();
        c.setProject(aProject);
        ClassModel aClass = new ClassModel("AClass");
        try {
            PackageModel aPackage = aProject.addPackage(new PackageModel());
            aPackage.addClass(aClass);
            aClass.addClass(new ClassModel("ASubClass"));
            aClass.addClass(new ClassModel("AnotherSubClass"));
            aClass.addClass(new ClassModel("YetAnotherSubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        c.setSelected(aClass);
        assertEquals(aClass, c.getSelectedClass());
        return c;
    }
    
    @Test
    public void testInit(){
        assertEquals(4, panel.getTreeSize());
    }
    
    @Test
    public void testAddPackageClasses() {
        fail();
    }

    @Test
    public void testGetSelected() {
        fail();
    }

    @Test
    public void testModelAdded() {
        fail();
    }
    
}
