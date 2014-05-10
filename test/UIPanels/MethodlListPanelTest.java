/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Internal.BaseTest;
import Models.ClassModel;
import Models.MethodModel;
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
public class MethodlListPanelTest  extends BaseTest{
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
        panel = new MethodlListPanel();
    }
    
    @After
    public void tearDown() {
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
        this.table().setColumnSelectionInterval(0, 0);
        assertEquals(selectionIndex, this.table().getSelectedRow());
        assertEquals(0, this.table().getSelectedColumn());
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
    
    @Test
    public void testInit(){
        assertEquals(DefaultTableModel.class, 
                ((JTable)this.getVariableFromClass(panel, "modelTable"))
                        .getModel().getClass());
        assertEquals(3, this.tableModel().getColumnCount());
        assertEquals(0, this.tableModel().getRowCount());
        assertEquals(null, panel.getSelected());
    }
    
    @Test
    public void testAddMethodToTable(){
        this.setUpTableMethods();
        assertEquals(3, this.tableModel().getRowCount());
    }
    

    @Test
    public void testRowSelection(){
        this.setUpTableMethods();
        this.setListSelection(1);
        assertTrue(panel.getSelected().isMethod());
    }
    
    @Test 
    public void testClassSelectionChanged(){
        fail();
        panel.selectionChanged(new ClassModel("aClassWithMethods"));
        //assert that the classes methods were added to the list
    }
    
    @Test
    public void testMethodAdded(){
        fail();
        panel.modelAdded(new MethodModel());
        //assert new model is in the list so long as its the same parent class as the list
    }
    
    @Test
    public void testMethodRemoved(){
        fail();
        panel.modelRemoved(null);
        //assert model is removed
    }
    
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
