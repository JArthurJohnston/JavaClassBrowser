/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import Internal.Mocks.MockBrowserController;
import Internal.Mocks.MockClassModel;
import MainBase.MainApplication;
import Models.*;
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
public class ClassFieldsPanelTest extends BaseTest{
    private BrowserUIController model;
    private ClassFieldsPanel panel;
    
    public ClassFieldsPanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        panel =  new ClassFieldsPanel();
        model = this.setUpModel();
        panel.setModel(model);
    }
    
    @After
    public void tearDown() {
        model = null;
        panel  = null;
    }
    
    private MockBrowserController setUpModel(){
        MockBrowserController controller = new MockBrowserController();
            MockClassModel aClass = new MockClassModel("AClass");
            aClass.addVariable(new VariableModel(ClassType.INSTANCE, 
                    ClassModel.getObjectClass(), "aVar"));
            aClass.addVariable(new VariableModel(ClassType.STATIC, 
                    ClassModel.getObjectClass(), "anotherVar"));
            aClass.addVariable(new VariableModel(ClassType.INSTANCE, 
                    ClassModel.getObjectClass(), "yetAnotherVar"));
        controller.setSelectedClass(aClass);
        return controller;
    }
    
    
    private MockClassModel getTestClass(){
        MockClassModel aClass = new MockClassModel("AnotherClass");
            aClass.addVariable(new VariableModel(
                    ClassType.INSTANCE, new ClassModel("Object"), "aVar"));
            aClass.addVariable(new VariableModel(
                    ClassType.STATIC, new ClassModel("Object"), "aClassVar"));
            aClass.addVariable(new VariableModel(
                    ClassType.INSTANCE, new ClassModel("Object"), "anInstVar"));
        return aClass;
    }
    
    @Test
    public void testSelectionSetsModel(){
        VariableListPanel instVarList = (VariableListPanel)panel.myPanels().getFirst();
        VariableListPanel statVarList = (VariableListPanel)panel.myPanels().getFirst();
        instVarList.setSelectedIndex(0);
        assertEquals(VariableModel.class, model.getSelected().getClass());
        assertEquals("aVar", model.getSelected().name());
        statVarList.setSelectedIndex(0);
        assertEquals(VariableModel.class, model.getSelected().getClass());
        assertEquals("anotherVar", model.getSelected().name());
    }
    

    @Test
    public void testInitialPanel() {
        JList instVarList = (JList)this.getVariableFromClass(panel, "instanceVarList");
        JList statVarList = (JList)this.getVariableFromClass(panel, "staticVarList");
        assertEquals(2, instVarList.getModel().getSize());
        assertEquals(1, statVarList.getModel().getSize());
        assertEquals(model, this.getVariableFromClass(panel, "model"));
    }
    
    @Test
    public void testImportsList(){
        fail();
    }
    
    @Test
    public void testSelectionChangedWithClass(){
        VariableListPanel instVarList = (VariableListPanel)panel.myPanels().getFirst();
        VariableListPanel statVarList = (VariableListPanel)panel.myPanels().getFirst();
        ClassModel aClass = this.getTestClass();
        panel.selectionChanged(aClass);
        assertEquals(2, instVarList.getTableSize());
        assertEquals(1, statVarList.getTableSize());
    }
    
    @Test
    public void testModelAdded(){
        VariableListPanel instVarList = (VariableListPanel)panel.myPanels().getFirst();
        VariableListPanel statVarList = (VariableListPanel)panel.myPanels().getFirst();
        assertEquals(2, instVarList.getTableSize());
        assertEquals(1, statVarList.getTableSize());
        
    }
}
