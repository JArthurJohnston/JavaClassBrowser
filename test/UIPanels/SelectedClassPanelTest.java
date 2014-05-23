/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Exceptions.AlreadyExistsException;
import Exceptions.BaseException;
import Internal.BaseTest;
import MainBase.MainApplication;
import MainBase.SortedList;
import Models.*;
import Types.ClassType;
import UIModels.BrowserUIController;
import java.util.LinkedList;
import javax.swing.JList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arthur
 */
public class SelectedClassPanelTest extends BaseTest {
    private BrowserUIController controller;
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
        main = new MainApplication();
        try {
            ProjectModel aProject = main.addProject(new ProjectModel("a project"));
            main.setSelectedProejct(aProject);
            ClassModel aClass = 
                    aProject.getDefaultPackage().addClass(new ClassModel("AClass"));
            aClass.addMethod(new MethodModel("anInstanceMethod", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("aStaticMethod", ClassType.STATIC));
            aClass.addVariable(new VariableModel(ClassType.STATIC, new ClassModel("Object"), "aClassVar"));
            aClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel("Object"), "anInstVar"));
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
        controller = new BrowserUIController(main);
        panel = new SelectedClassPanel();
        panel = (SelectedClassPanel)this.getVariableFromClass(controller.getShell(), "classBrowserPanel");
    }
    
    @After
    public void tearDown() {
        controller = null;
        panel = null;
    }
    
    
    private LinkedList<ClassModel> setUpOtherClasses(){
        LinkedList<ClassModel> aList = new LinkedList();
        try {
            ClassModel aClass = controller.getSelectedPackage()
                    .addClass(new ClassModel("SecondClass"));
            aList.add(aClass);
            aClass.addMethod(new MethodModel("secondClassInstMethodOne", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("secondClassInstMethodTwo", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("secondClassStatMethodOne", ClassType.STATIC));
            aClass.addMethod(new MethodModel("secondClassStatMethodTwo", ClassType.STATIC));
            aClass.addVariable(new VariableModel(
                    ClassType.INSTANCE, ClassModel.getPrimitive("void"), "secondClassInstVarOne"));
            aClass.addVariable(new VariableModel(
                    ClassType.INSTANCE, ClassModel.getPrimitive("void"), "secondClassInstVarTwo"));
            aClass.addVariable(new VariableModel(
                    ClassType.STATIC, ClassModel.getPrimitive("void"), "secondClassStatVarOne"));
            aClass.addVariable(new VariableModel(
                    ClassType.STATIC, ClassModel.getPrimitive("void"), "secondClassStatVarTwo"));
            
            aClass = controller.getSelectedPackage()
                    .addClass(new ClassModel("ThirdClass"));
            aList.add(aClass);
            aClass.addMethod(new MethodModel("secondClassInstMethodOne", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("secondClassInstMethodTwo", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("secondClassInstMethodThree", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("secondClassStatMethodOne", ClassType.STATIC));
            aClass.addMethod(new MethodModel("secondClassStatMethodTwo", ClassType.STATIC));
            aClass.addMethod(new MethodModel("secondClassStatMethodThree", ClassType.STATIC));
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
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
        assertEquals("SecondClass",aList.getFirst().name());
        assertEquals("ThirdClass",aList.getLast().name());
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
        assertEquals(controller, this.getVariableFromClass(cfPresenter, "model"));
        assertEquals(controller, this.getVariableFromClass(mPresenter, "model"));
    }
    
    @Test
    public void testSetSelectionUpdatesModel(){
        JList classList = (JList)this.getVariableFromClass(panel, "classList");
        classList.setSelectedIndex(0);
        assertEquals("AClass", controller.getSelected().name());
    }
    
    @Test
    public void testClassSelectedUpdatesVariablePanel(){
        ClassFieldsPanel classFields = (ClassFieldsPanel)panel.myPanels().getFirst();
        JList instVarList = classFields.myLists().get(1);
        JList statVarList = classFields.myLists().getLast();
        LinkedList<ClassModel> aList = this.setUpOtherClasses();
        JList classes = (JList)this.getVariableFromClass(panel, "classList");
        assertEquals(3, classes.getModel().getSize());
        
        classes.setSelectedIndex(1);
        assertEquals(2, instVarList.getModel().getSize());
        assertEquals(2, statVarList.getModel().getSize());
        
        classes.setSelectedIndex(2);
        assertEquals(3, instVarList.getModel().getSize());
        assertEquals(3, statVarList.getModel().getSize());
        
        classes.setSelectedIndex(1);
        assertEquals(2, instVarList.getModel().getSize());
        assertEquals(2, statVarList.getModel().getSize());
    }
    
    @Test
    public void testClassSelectedUpdatesMethodPanel(){
        MethodPanel methods = (MethodPanel)panel.myPanels().getLast();
        JList instMethList = methods.myLists().getFirst();
        JList statMethList = methods.myLists().getLast();
        LinkedList<ClassModel> aList = this.setUpOtherClasses();
        JList classes = (JList)this.getVariableFromClass(panel, "classList");
        assertEquals(3, classes.getModel().getSize());
        
        classes.setSelectedIndex(1);
        assertEquals(2, instMethList.getModel().getSize());
        assertEquals(2, statMethList.getModel().getSize());
        
        classes.setSelectedIndex(2);
        assertEquals(3, instMethList.getModel().getSize());
        assertEquals(3, statMethList.getModel().getSize());
        
        classes.setSelectedIndex(1);
        assertEquals(2, instMethList.getModel().getSize());
        assertEquals(2, statMethList.getModel().getSize());
    }
    
    @Test
    public void testClassAddedUpdatesPanel(){
        JList classList = (JList)this.getVariableFromClass(panel, "classList");
        assertEquals(1, classList.getModel().getSize());
        panel.modelAdded(new ClassModel("NewClass"));
        assertEquals(2, classList.getModel().getSize());
    }
}
