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
import UIModels.SystemBrowserShellModel;
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
        try {
            ProjectModel aProject = main.addProject(new ProjectModel(main, "AProject"));
            PackageModel aPackage = aProject.getPackageList().get(1);
            ClassModel aClass = aPackage.addClass(new ClassModel(aPackage, "AClass"));
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
        JList statList = (JList)this.getVariableFromClass(panel, "staticMethodList");
        assertEquals(model, this.getVariableFromClass(panel, "model"));
        assertEquals(2, instList.getModel().getSize());
        assertEquals(1, statList.getModel().getSize());
    }
    
    @Test
    public void testListSelectionSetsModel(){
        JList instList = (JList)this.getVariableFromClass(panel, "instanceMethodList");
        JList statList = (JList)this.getVariableFromClass(panel, "staticMethodList");
        instList.setSelectedIndex(1);
        assertEquals(MethodModel.class, model.getSelected().getClass());
        assertEquals("anotherMethod", model.getSelected().name());
        statList.setSelectedIndex(0);
        assertEquals(MethodModel.class, model.getSelected().getClass());
        assertEquals("yetAnotherMethod", model.getSelected().name());
    }
    
    @Test
    public void testSelectionClearsPreviousSelection(){
        JList instList = (JList)this.getVariableFromClass(panel, "instanceMethodList");
        JList statList = (JList)this.getVariableFromClass(panel, "staticMethodList");
        instList.setSelectedIndex(0);
        assertEquals("aMethod", model.getSelected().name());
        statList.setSelectedIndex(0);
        assertEquals("yetAnotherMethod", model.getSelected().name());
        assertEquals(-1, instList.getSelectedIndex());
    }
    
    @Test
    public void testDefaultDimensions(){
        fail("this may not be necessary");
        assertEquals(150, panel.getWidth());
        assertEquals(250, panel.getHeight());
    }
    
}
