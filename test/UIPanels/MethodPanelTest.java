/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Models.PackageModel;
import Models.ProjectModel;
import Types.ClassType;
import UIModels.ClassBrowserShellModel;
import UIModels.SystemBrowserShellModel;
import UIShells.ClassBrowserShell;
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
public class MethodPanelTest extends BaseTest{
    private MethodPanel panel;
    private MainApplication main;
    private SystemBrowserShellModel model;
    
    public MethodPanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        main = new MainApplication();
        ClassModel aClass = null;
        try {
            ProjectModel aProject = main.addProject(new ProjectModel(main, "AProject"));
            PackageModel aPackage = aProject.getPackageList().get(1);
            aClass = aPackage.addClass(new ClassModel(aPackage, "AClass"));
            aClass.addMethod(new MethodModel(aClass, "aMethod"));
            aClass.addMethod(new MethodModel(aClass, "anotherMethod"));
            aClass.addMethod(new MethodModel(aClass, "yetAnotherMethod", ClassType.CLASS));
            main.setSelectedProejct(aProject);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        model = main.openSystemBrowser();
        panel  = new MethodPanel();
        panel.initializeWithModel(model);
    }
    
    @After
    public void tearDown() {
        panel = null;
        model = null;
        main = null;
    }

    @Test
    public void testInitialValues() {
        assertEquals(MethodPanel.class, panel.getClass());
        JList instList = (JList)this.getVariableFromClass(panel, "instanceMethodList");
        assertEquals(model, this.getVariableFromClass(panel, "model"));
        assertEquals(3, instList.getModel().getSize());
    }
    
    @Test
    public void testListSelectionSetsModel(){
        /*
        assertEquals(MethodPanel.class, panel.getClass());
        MethodModel aMethod = model.selectedClass().getMethods().get(0);
        MethodModel anotherMethod = model.selectedClass().getMethods().get(1);
        JList instList = (JList)this.getVariableFromClass(panel, "instanceMethodList");
        instList.setSelectedIndex(1);
        assertEquals(anotherMethod, model.getSelected());
        instList.setSelectedIndex(0);
        assertEquals(aMethod, model.getSelected());
        */
        fail("reWrite");
    }
    
    @Test
    public void testDefaultDimensions(){
        assertEquals(150, panel.getWidth());
        assertEquals(250, panel.getHeight());
    }
    
}
