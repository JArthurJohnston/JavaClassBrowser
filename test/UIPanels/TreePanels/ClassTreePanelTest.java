/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels;

import Internal.Mocks.MockBrowserController;
import Internal.Mocks.MockClassModel;
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
    }
    
    @After
    public void tearDown() {
        panel = null;
    }

    private MockBrowserController getController(){
        MockBrowserController controller = new MockBrowserController();
        MockClassModel aClass = new MockClassModel("AClass");
        aClass.addClass(new MockClassModel("aSubClass1"));
        aClass.addClass(new MockClassModel("aSubClass2"));
        MockClassModel aSubClass = (MockClassModel)aClass
                .addClass(new MockClassModel("aSubClass3"));
        aSubClass.addClass(new MockClassModel("aSubSubClass1"));
        aSubClass.addClass(new MockClassModel("aSubSubClass2"));
        controller.setSelectedClass(aClass);
        return controller;
    }
    
    @Test
    public void testInit(){
    }
    
    @Test
    public void testAddPackageClasses() {
    }

    @Test
    public void testGetSelected() {
    }

    @Test
    public void testAddClassToSelected() {
    }
    
}
