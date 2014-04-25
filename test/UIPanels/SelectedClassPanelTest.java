/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.*;
import Types.ClassType;
import UIModels.BrowserUIModel;
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
public class SelectedClassPanelTest extends BaseTest {
    private BrowserUIModel model;
    private SelectedClassPanel panel;
    
    public SelectedClassPanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        MainApplication main = new MainApplication();
        try {
            ProjectModel aProject = main.addProject(new ProjectModel(main, "a project"));
            main.setSelectedProejct(aProject);
            ClassModel aClass = 
                    aProject.getDefaultPackage().addClass(new ClassModel(aProject.getDefaultPackage(), "AClass"));
            aClass.addMethod(new MethodModel(aClass, "anInstanceMethod", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel(aClass, "aStaticMethod", ClassType.STATIC));
            aClass.addVariable(new VariableModel(ClassType.STATIC, new ClassModel("Object"), "aClassVar"));
            aClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel("Object"), "anInstVar"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        model = new BrowserUIModel(main);
        panel = new SelectedClassPanel();
        panel.setModel(model);
    }
    
    @After
    public void tearDown() {
        model = null;
        panel = null;
    }

    /**
     * Test of setModel method, of class SelectedClassPanel.
     */
    @Test
    public void testSetModel() {
        JList classList = (JList)this.getVariableFromClass(panel, "classList");
        assertEquals(1, classList.getModel().getSize());
        ClassFieldsPanel cfPresenter = (ClassFieldsPanel)this.getVariableFromClass(panel, "classFieldsPresenter");
        MethodPanel mPresenter = (MethodPanel)this.getVariableFromClass(panel, "methodPresenter");
        assertEquals(model, this.getVariableFromClass(cfPresenter, "model"));
        assertEquals(model, this.getVariableFromClass(mPresenter, "model"));
    }
    
    @Test
    public void testSetSelectionUpdatesModel(){
        JList classList = (JList)this.getVariableFromClass(panel, "classList");
        classList.setSelectedIndex(0);
        assertEquals("AClass", model.getSelected().name());
    }
}
