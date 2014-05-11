/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Internal.BaseTest;
import Internal.Mocks.MockBrowserController;
import Internal.Mocks.MockClassModel;
import Models.ClassModel;
import Models.MethodModel;
import Types.ClassType;
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
public class MethodPanelTest extends BaseTest{
    private MethodPanel panel;
    private MockBrowserController controller;
    
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
        this.setUpMain();
        MockClassModel aClass = new MockClassModel("AClass");
            aClass.addMethod(new MethodModel("aMethod"));
            aClass.addMethod(new MethodModel("anotherMethod"));
            aClass.addMethod(new MethodModel("yetAnotherMethod", ClassType.STATIC));
        controller = new MockBrowserController();
        controller.setSelectedClass(aClass);
        panel  = new MethodPanel();
        panel.setModel(controller);
    }
    
    @After
    public void tearDown() {
        panel = null;
        controller = null;
        main = null;
    }
    
    private MockClassModel getTestClass(){
        MockClassModel aClass = new MockClassModel("AnotherClass");
            aClass.addMethod(new MethodModel("aMethod", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("anotherMethod", ClassType.STATIC));
            aClass.addMethod(new MethodModel("yetAnotherMethod", ClassType.STATIC));
        return aClass;
    }

    @Test
    public void testInitialValues() {
        assertEquals(MethodPanel.class, panel.getClass());
        MethodlListPanel instList = (MethodlListPanel)panel.myPanels().getFirst();
        MethodlListPanel statList = (MethodlListPanel)panel.myPanels().getLast();
        assertEquals(controller, this.getVariableFromClass(panel, "controller"));
        assertEquals(controller, this.getVariableFromClass(instList, "controller"));
        assertEquals(controller, this.getVariableFromClass(statList, "controller"));
        assertEquals(2, instList.getTableSize());
        assertEquals(1, statList.getTableSize());
    }
    
    @Test
    public void testListSelectionSetsModel(){
        MethodlListPanel instList = (MethodlListPanel)panel.myPanels().getFirst();
        MethodlListPanel statList = (MethodlListPanel)panel.myPanels().getLast();
        instList.setSelectedIndex(1);
        assertEquals(MethodModel.class, controller.getSelected().getClass());
        assertEquals("anotherMethod", controller.getSelected().name());
        statList.setSelectedIndex(0);
        assertEquals(MethodModel.class, controller.getSelected().getClass());
        assertEquals("yetAnotherMethod", controller.getSelected().name());
    }
    
    @Test
    public void testSelectionChangedWithClass(){
        MethodlListPanel instList = (MethodlListPanel)panel.myPanels().getFirst();
        MethodlListPanel statList = (MethodlListPanel)panel.myPanels().getLast();
        ClassModel aClass = this.getTestClass();
        panel.selectionChanged(aClass);
        assertEquals(1, instList.getTableSize());
        assertEquals(2, statList.getTableSize());
    }
    
    @Test
    public void testMethodRemoved(){
        MethodlListPanel instList = (MethodlListPanel)panel.myPanels().getFirst();
        MethodlListPanel statList = (MethodlListPanel)panel.myPanels().getLast();
        ClassModel aClass = this.getTestClass();
        panel.selectionChanged(aClass);
        assertEquals(1, instList.getTableSize());
        assertEquals(2, statList.getTableSize());
        
        MethodModel instMethod = aClass.getInstanceMethods().getFirst();
        MethodModel statMethod = aClass.getStaticMethods().getFirst();
        
        panel.modelRemoved(instMethod);
        panel.modelRemoved(statMethod);
        
        assertTrue(instList.isEmpty());
        assertEquals(1, statList.getTableSize());
    }
    
    @Test
    public void testOtherClassMethodRemoved(){
        MethodlListPanel instList = (MethodlListPanel)panel.myPanels().getFirst();
        MethodlListPanel statList = (MethodlListPanel)panel.myPanels().getLast();
        ClassModel aClass = this.getTestClass();
        panel.selectionChanged(aClass);
        assertEquals(1, instList.getTableSize());
        assertEquals(2, statList.getTableSize());
        
        MockClassModel anotherClass = new MockClassModel("SomeOtherClass");
        
        MethodModel instMethod = 
                anotherClass.addMethod(new MethodModel("someMethod"));
        instMethod.setType(ClassType.INSTANCE);
        MethodModel statMethod = 
                anotherClass.addMethod(new MethodModel("someOtherMethod"));
        instMethod.setType(ClassType.STATIC);
        
        panel.modelRemoved(instMethod);
        panel.modelRemoved(statMethod);
        
        assertEquals(1, instList.getTableSize());
        assertEquals(2, statList.getTableSize());
    }
    
    @Test
    public void testMethodAdded(){
        MethodlListPanel instList = (MethodlListPanel)panel.myPanels().getFirst();
        MethodlListPanel statList = (MethodlListPanel)panel.myPanels().getLast();
        MockClassModel aClass = (MockClassModel)this.getTestClass();
        controller.setSelectedClass(aClass);
        panel.selectionChanged(aClass);
        assertEquals(1, instList.getTableSize());
        assertEquals(2, statList.getTableSize());
        
        MethodModel instMethod = 
                aClass.addMethod(new MethodModel("someMethod"));
        instMethod.setType(ClassType.INSTANCE);
        MethodModel statMethod = 
                aClass.addMethod(new MethodModel("someOtherMethod"));
        instMethod.setType(ClassType.STATIC);
        
        panel.modelAdded(instMethod);
        panel.modelAdded(statMethod);
        
        assertEquals(2, instList.getTableSize());
        assertEquals(3, statList.getTableSize());
    }
    
    @Test
    public void testOtherClassMethodAdded(){
        MethodlListPanel instList = (MethodlListPanel)panel.myPanels().getFirst();
        MethodlListPanel statList = (MethodlListPanel)panel.myPanels().getLast();
        MockClassModel aClass = (MockClassModel)this.getTestClass();
        controller.setSelectedClass(aClass);
        panel.selectionChanged(aClass);
        assertEquals(1, instList.getTableSize());
        assertEquals(2, statList.getTableSize());
        
        MockClassModel anotherClass = new MockClassModel("SomeOtherClass");
        
        MethodModel instMethod = 
                anotherClass.addMethod(new MethodModel("someMethod"));
        instMethod.setType(ClassType.INSTANCE);
        MethodModel statMethod = 
                anotherClass.addMethod(new MethodModel("someOtherMethod"));
        instMethod.setType(ClassType.STATIC);
        
        panel.modelAdded(instMethod);
        panel.modelAdded(statMethod);
        
        assertEquals(1, instList.getTableSize());
        assertEquals(2, statList.getTableSize());
    }
}
