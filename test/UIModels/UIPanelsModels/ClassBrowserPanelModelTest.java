/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels.UIPanelsModels;

import Exceptions.NameAlreadyExistsException;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIShells.UIPanels.ClassBrowserPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
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
public class ClassBrowserPanelModelTest {
    private ProjectModel testProject;
    private PackageModel testPackage;
    private ClassModel testClass;
    private ClassBrowserPanelModel instance;
    
    public ClassBrowserPanelModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            testProject = new ProjectModel("Test Project");
            testPackage = testProject.addPackage("Test Package");
            testClass = testPackage.addClass("TestClass");
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(ClassBrowserPanelModelTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Exception thrown when it shoudnt");
        }
        instance = new ClassBrowserPanelModel(testClass);
    }
    
    @After
    public void tearDown() {
        testProject = null;
        testPackage = null;
        testClass = null;
    }
    
    @Test
    public void testInitialize(){
        System.out.println("testInitialize");
        assertEquals(DefaultListModel.class, instance.getClassMethodList().getClass());
        assertEquals(DefaultListModel.class, instance.getClassVarsList().getClass());
        assertEquals(DefaultListModel.class, instance.getInstMethodList().getClass());
        assertEquals(DefaultListModel.class, instance.getInstVarList().getClass());
        assertEquals(ClassBrowserPanel.class, instance.getShell().getClass());
    }

    /**
     * Test of getInstVarList method, of class ClassBrowserPanelModel.
     */
    @Test
    public void testGetInstVarList() {
        System.out.println("getInstVarList");
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClassVarsList method, of class ClassBrowserPanelModel.
     */
    @Test
    public void testGetClassVarsList() {
        System.out.println("getClassVarsList");
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstMethodList method, of class ClassBrowserPanelModel.
     */
    @Test
    public void testGetInstMethodList() {
        System.out.println("getInstMethodList");
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClassMethodList method, of class ClassBrowserPanelModel.
     */
    @Test
    public void testGetClassMethodList() {
        System.out.println("getClassMethodList");
        fail("The test case is a prototype.");
    }
}
