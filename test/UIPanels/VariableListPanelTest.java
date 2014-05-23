/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels;

import UIPanels.ListPanels.VariableListPanel;
import Internal.Mocks.MockBrowserController;
import Internal.Mocks.MockClassModel;
import Models.VariableModel;
import Types.ClassType;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class VariableListPanelTest {
    private VariableListPanel panel;
    
    public VariableListPanelTest() {
    }
    
    @Before
    public void setUp() {
        panel = new VariableListPanel();
    }
    
    @After
    public void tearDown() {
        panel = null;
    }

    private MockBrowserController controller(){
        MockBrowserController controller = new MockBrowserController();
        MockClassModel aClass = new MockClassModel("SomeClass");
        aClass.addVariable(new VariableModel("aVar", ClassType.INSTANCE));
        aClass.addVariable(new VariableModel("anotherVar", ClassType.INSTANCE));
        aClass.addVariable(new VariableModel("yetAnotherVar", ClassType.STATIC));
        aClass.addVariable(new VariableModel("andYetAnotherVar", ClassType.STATIC));
        aClass.addVariable(new VariableModel("andAnotherVar", ClassType.STATIC));
        controller.setSelected(aClass);
        return controller;
    }

    /**
     * Test of setModel method, of class VariableListPanel.
     */
    @Test
    public void testSetModel() {
        panel.setSelectionType(ClassType.STATIC);
        panel.setModel(this.controller());
        assertEquals(3, panel.getTableSize());
        panel.setSelectionType(ClassType.INSTANCE);
        panel.setModel(this.controller());
        assertEquals(2, panel.getTableSize());
    }

    /**
     * Test of modelAdded method, of class VariableListPanel.
     */
    @Test
    public void testVariableAdded() {
        panel.setSelectionType(ClassType.INSTANCE);
        MockBrowserController controller = this.controller();
        MockClassModel selectedClass = (MockClassModel)controller.getSelectedClass();
        MockClassModel anotherClass = new MockClassModel("AnotherClass");
        panel.setModel(controller);
        
        VariableModel addedVar = selectedClass
                .addVariable(new VariableModel("newVar", ClassType.INSTANCE));
        VariableModel ignoredStaticVar = selectedClass
                .addVariable(new VariableModel("ignoredStatVar", ClassType.STATIC));
        VariableModel ignoredVar = anotherClass
                .addVariable(new VariableModel("ignoredVar", ClassType.INSTANCE));
        
        
        assertEquals(2, panel.getTableSize());
        panel.modelAdded(addedVar);
        assertEquals(3, panel.getTableSize());
        panel.modelAdded(ignoredVar);
        assertEquals(3, panel.getTableSize());
        panel.modelAdded(ignoredStaticVar);
        assertEquals(3, panel.getTableSize());
    }
    
    @Test
    public void testVariableRemoved(){
        VariableModel variableToBeRemoved = 
                new VariableModel("remvoedVar", ClassType.INSTANCE);
        assertTrue(panel.isEmpty());
        panel.modelRemoved(variableToBeRemoved);
        //testing that removing from an empty list doesnt thrown any exceptions
        
        panel.setSelectionType(ClassType.INSTANCE);
        MockBrowserController controller = this.controller();
        MockClassModel selectedClass = (MockClassModel)controller.getSelectedClass();
        MockClassModel anotherClass = new MockClassModel("AnotherClass");
        panel.setModel(controller);
        
        assertEquals(2, panel.getTableSize());
        
        variableToBeRemoved = selectedClass.getInstanceVars().getFirst();
        panel.modelRemoved(variableToBeRemoved);
        assertEquals(1, panel.getTableSize());
        assertFalse(panel.contains(variableToBeRemoved));
        
        VariableModel ignoredVar = selectedClass.getStaticVars().getFirst();
        panel.modelRemoved(ignoredVar);
        assertEquals(1, panel.getTableSize());
        
        ignoredVar = anotherClass
                .addVariable(new VariableModel("someVar", ClassType.INSTANCE));
        panel.modelRemoved(ignoredVar);
        assertEquals(1, panel.getTableSize());
    }
    
    @Test
    public void testVariableChanged(){
        fail();
    }
    
}
