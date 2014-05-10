/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Models.PackageModel;
import Models.ProjectModel;
import Types.ClassType;
import UIModels.BrowserUIController;
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
    private BrowserUIController model;
    
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
            ProjectModel aProject = main.addProject(new ProjectModel("AProject"));
            PackageModel aPackage = aProject.getPackageList().get(1);
            ClassModel aClass = aPackage.addClass(new ClassModel("AClass"));
            aClass.addMethod(new MethodModel("aMethod"));
            aClass.addMethod(new MethodModel("anotherMethod"));
            aClass.addMethod(new MethodModel("yetAnotherMethod", ClassType.STATIC));
            main.setSelectedProejct(aProject);
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        model = main.openSystemBrowser();
        panel  = new MethodPanel();
        panel.setModel(model);
    }
    
    @After
    public void tearDown() {
        panel = null;
        model = null;
        main = null;
    }
    
    private ClassModel getTestClass(){
        ClassModel aClass = null;
        try {
            aClass = model.getSelectedProject()
                    .getPackageList()
                        .get(1)
                            .addClass(new ClassModel("AnotherClass"));
            aClass.addMethod(new MethodModel("aMethod", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("anotherMethod", ClassType.STATIC));
            aClass.addMethod(new MethodModel("yetAnotherMethod", ClassType.STATIC));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return aClass;
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
    public void testSelectionChangedWithClass(){
        JList instList = (JList)this.getVariableFromClass(panel, "instanceMethodList");
        JList statList = (JList)this.getVariableFromClass(panel, "staticMethodList");
        ClassModel aClass = this.getTestClass();
        panel.selectionChanged(aClass);
        assertEquals(1, instList.getModel().getSize());
        assertEquals(2, statList.getModel().getSize());
    }
    
}
