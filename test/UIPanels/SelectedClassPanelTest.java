/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import MainBase.UsefulList;
import Models.*;
import Types.ClassType;
import Types.ScopeType;
import UIModels.BrowserUIModel;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
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
            ProjectModel aProject = main.addProject(new ProjectModel("a project"));
            main.setSelectedProejct(aProject);
            ClassModel aClass = 
                    aProject.getDefaultPackage().addClass(new ClassModel("AClass"));
            aClass.addMethod(new MethodModel("anInstanceMethod", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("aStaticMethod", ClassType.STATIC));
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
    
    private LinkedList<ClassModel> setUpOtherClasses(){
        LinkedList<ClassModel> aList = new LinkedList();
        try {
            ClassModel aClass = model.getSelectedPackage()
                    .addClass(new ClassModel("SecondClass"));
            aList.add(aClass);
            aClass.addMethod(new MethodModel("secondClassMethodOne"));
            aClass.addMethod(new MethodModel("secondClassMethodTwo"));
            aClass.addVariable(new VariableModel(
                    ClassType.INSTANCE, ClassModel.getPrimitive("void"), "secondClassInstVar"));
            aClass.addVariable(new VariableModel(
                    ClassType.STATIC, ClassModel.getPrimitive("void"), "secondClassStatVar"));
            
            aClass = model.getSelectedPackage()
                    .addClass(new ClassModel("ThirdClass"));
            aList.add(aClass);
            aClass.addMethod(new MethodModel("thirdClassMethodOne"));
            aClass.addMethod(new MethodModel("thirdClassMethodTwo"));
            aClass.addMethod(new MethodModel("thirdClassMethodThree"));
            aClass.addVariable(new VariableModel(
                    ClassType.INSTANCE, ClassModel.getPrimitive("void"), "thirdClassInstVarOne"));
            aClass.addVariable(new VariableModel(
                    ClassType.INSTANCE, ClassModel.getPrimitive("void"), "thirdClassInstVarTwo"));
            aClass.addVariable(new VariableModel(
                    ClassType.INSTANCE, ClassModel.getPrimitive("void"), "thirdClassInstVarThree"));
            aClass.addVariable(new VariableModel(
                    ClassType.STATIC, ClassModel.getPrimitive("void"), "thirdClassStatVarOne"));
            aClass.addVariable(new VariableModel(
                    ClassType.STATIC, ClassModel.getPrimitive("void"), "thirdClassStatVarTwo"));
            aClass.addVariable(new VariableModel(
                    ClassType.STATIC, ClassModel.getPrimitive("void"), "thirdClassStatVarThree"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals("SecondClass",aList.getFirst().name());
        assertEquals("ThirdClass",aList.getFirst().name());
        return aList;
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
    
    @Test
    public void testClassSelectedUpdatesPanels(){
        LinkedList<ClassModel> aList = this.setUpOtherClasses();
        JList classes = (JList)this.getVariableFromClass(panel, "classList");
        classes.setSelectedIndex(1);
        ClassFieldsPanel classFields = (ClassFieldsPanel)panel.myPanels().getFirst();
        MethodPanel methods = (MethodPanel)panel.myPanels().getLast();
        assertEquals(aList.getFirst(), classes.getSelectedValue());
        assertEquals(2, methods.myLists().getFirst().size());
    }
    
    @Test
    public void testClassAddedUpdatesPanel(){
        JList classList = (JList)this.getVariableFromClass(panel, "classList");
        assertEquals(1, classList.getModel().getSize());
        panel.modelAdded(new ClassModel("NewClass"));
        assertEquals(2, classList.getModel().getSize());
    }
}
