/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import Internal.Mocks.MockBrowserController;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Models.ProjectModel;
import Types.ClassType;
import Types.ScopeType;
import UIModels.BrowserUIController;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private BrowserUIController BrowserUIModel;
    
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
        this.setUpMain();
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
    
    private ClassModel classWithMethods(){
        ClassModel aClass = null;
        try {
            aClass = this.project()
                    .getDefaultPackage()
                        .addClass(new ClassModel("AClass"));
            MethodModel aMethod = null;
            aClass.addMethod(new MethodModel("oneMethod", ScopeType.PRIVATE));
            aClass.addMethod(new MethodModel("twoMethod", ScopeType.PRIVATE));
            aMethod = aClass.addMethod(new MethodModel("threeMethod", ScopeType.PROTECTED));
            aMethod.setType(ClassType.STATIC);
            aMethod = aClass.addMethod(new MethodModel("fourMethod", ScopeType.PUBLIC));
            aMethod.setType(ClassType.STATIC);
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
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
        panel.setSelectionType(ClassType.STATIC);
        panel.selectionChanged(this.classWithMethods());
        assertEquals(2, this.table().getRowCount());
    }
    
    @Test
    public void testMethodAdded(){
        MockBrowserController controller = new MockBrowserController();
        ClassModel aClass = this.classWithMethods();
        controller.setSelectedClass(aClass);
        MethodModel aMethod = new MethodModel("aMethod");
        this.addMethodToClass(aClass, aMethod);
        
        panel.setSelectionType(ClassType.INSTANCE);
        panel.setModel(controller);
        assertEquals(3, panel.getTableSize());
        
        panel.modelAdded(aMethod);
        assertEquals(4, panel.getTableSize());
        assertEquals(aMethod, panel.getValueAt(3));
    }
    
    
    @Test
    public void testMethodRemoved(){
        fail();
        panel.modelRemoved(null);
        //assert model is removed
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
