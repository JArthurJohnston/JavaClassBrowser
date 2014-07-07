/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import UIPanels.ListPanels.MethodlListPanel;
import Internal.BaseTest;
import Internal.Mocks.MockBrowserController;
import Internal.Mocks.MockClassModel;
import Models.ClassModel;
import Models.MethodModel;
import Types.ClassType;
import Types.ScopeType;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
public class MethodlListPanelTest extends BaseTest{
    MethodlListPanel panel;
    
    public MethodlListPanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        super.setUp();
        panel = new MethodlListPanel();
    }
    
    @After
    public void tearDown() {
        main = null;
        panel = null;
    }
    
    private JTable table(){
        return (JTable)this.getVariableFromClass(panel, "modelTable");
    }
    
    private DefaultTableModel tableModel(){
        return (DefaultTableModel)this.table().getModel();
    }
    
    private void setListSelection(int selectionIndex){
        this.table().setRowSelectionInterval(selectionIndex, selectionIndex);
        this.table().setColumnSelectionInterval(1, 1);
        assertEquals(selectionIndex, this.table().getSelectedRow());
        assertEquals(1, this.table().getSelectedColumn());
    }
    
    private void setUpTableMethods(){
        MethodModel aMethod = new MethodModel("aMethod");
        aMethod.setScope(ScopeType.PRIVATE);
        aMethod.setReturnType(ClassModel.getPrimitive("void"));
        panel.addMethodToList(aMethod);
        aMethod.setName("anotherMethod");
        aMethod.setScope(ScopeType.NONE);
        aMethod.setReturnType(ClassModel.getPrimitive("int"));
        panel.addMethodToList(aMethod);
        aMethod.setName("yetAnotherMethod");
        aMethod.setScope(ScopeType.NONE);
        aMethod.setReturnType(ClassModel.getPrimitive("char"));
        panel.addMethodToList(aMethod);
    }
    
    private MockClassModel classWithMethods(){
        MockClassModel aClass = new MockClassModel("AClass");
            aClass.addMethod(new MethodModel("oneMethod", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("twoMethod", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("threeMethod", ClassType.STATIC));
            aClass.addMethod(new MethodModel("fourMethod", ClassType.STATIC));
            aClass.addMethod(new MethodModel("fiveMethod", ClassType.STATIC));
        return aClass;
    }
    
    @Test
    public void testInit(){
        assertEquals(DefaultTableModel.class, 
                ((JTable)this.getVariableFromClass(panel, "modelTable"))
                        .getModel().getClass());
        assertEquals(3, this.tableModel().getColumnCount());
        assertEquals(0, this.tableModel().getRowCount());
        assertTrue(panel.isEmpty());
        //assertEquals(-1, panel.getSelected());
            //throws an error if you call getSelected when there are no elements in the table.
        assertFalse(panel.contains(new MethodModel("someRandomMethod")));
    }
    
    @Test
    public void testAddMethodToTable(){
        this.setUpTableMethods();
        assertEquals(3, this.tableModel().getRowCount());
    }
    
    @Test
    public void testRowSelection(){
        this.setUpTableMethods();
        panel.setModel(new MockBrowserController());
        this.setListSelection(1);
        assertTrue(panel.getSelected().isMethod());
    }
    
    @Test 
    public void testClassSelectionChanged() {
        panel.setSelectionType(ClassType.STATIC);
        panel.selectionChanged(this.classWithMethods());
        assertEquals(3, panel.getTableSize());
        //test selection changed to another class
            //clear the list, then re-populate it
        MockClassModel aClass = new MockClassModel("AnotherClass");
        this.addMethodToClass(aClass, new MethodModel("aMethod", ClassType.STATIC));
        this.addMethodToClass(aClass, new MethodModel("anotherMethod", ClassType.STATIC));
        this.addMethodToClass(aClass, new MethodModel("yetAnotherMethod", ClassType.INSTANCE));
        panel.selectionChanged(aClass);
        assertEquals(2, panel.getTableSize());
    }
    
    @Test
    public void testMethodAdded(){
        MockBrowserController controller = new MockBrowserController();
        MockClassModel aClass = this.classWithMethods();
        controller.setSelected(aClass);
        
        panel.setSelectionType(ClassType.INSTANCE);
        panel.setModel(controller);
        assertEquals(2, panel.getTableSize());
        
        MethodModel aMethod = new MethodModel("aMethod");
        aMethod.setType(ClassType.INSTANCE);
        aMethod.setParent(aClass);
        
        panel.modelAdded(aMethod);
        assertEquals(3, panel.getTableSize());
        assertEquals(aMethod, panel.getValueAt(2));
        assertEquals(2, panel.indexOf(aMethod));
        assertTrue(panel.contains(aMethod));
        
    }
    
    @Test
    public void testSetModelWithNullClass(){
        MockBrowserController controller = new MockBrowserController();
        panel.setSelectionType(ClassType.STATIC);
        panel.setModel(controller);
        assertTrue(panel.isEmpty());
        controller.setSelected(this.classWithMethods());
        
        panel.setModel(controller);
        assertEquals(3, panel.getTableSize());
        
        panel.setSelectionType(ClassType.INSTANCE);
        panel.setModel(controller);
        assertEquals(2, panel.getTableSize());
    }
    
    @Test
    public void testIndexOfEmpty(){
        assertEquals(-1, panel.indexOf(new MethodModel()));
    }
    
    
    @Test
    public void testMethodRemoved(){
        assertTrue(panel.isEmpty());
        ClassModel aClass = this.classWithMethods();
        MethodModel methodToBeRemoved = aClass.getStaticMethods().getFirst();
        panel.setSelectionType(ClassType.STATIC);
        panel.selectionChanged(aClass);
        assertEquals(3, panel.getTableSize());
        assertTrue(panel.contains(methodToBeRemoved));
        panel.modelRemoved(methodToBeRemoved);
        assertEquals(2, panel.getTableSize());
        assertFalse(panel.contains(methodToBeRemoved));
    }
    
    /*
    these procedures will probably need buffers...
    */
    @Test
    public void testMethodNameChange(){
        fail();
    }
    
    @Test
    public void testMethodScopeChange(){
        fail();
    }
    
    @Test
    public void testMethodReturnTypeChange(){
        fail();
    }
}
