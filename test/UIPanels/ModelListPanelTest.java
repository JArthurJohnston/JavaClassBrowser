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
public class ModelListPanelTest  extends BaseTest{
    ModelListPanel panel;
    
    public ModelListPanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        panel = new ModelListPanel();
    }
    
    @After
    public void tearDown() {
        panel = null;
    }
    

    /**
     * Test of addClassToList method, of class ModelListPanel.
     */
    @Test
    public void testAddClassToList(){
        JTable aTable = (JTable)this.getVariableFromClass(panel, "modelTable");
        ClassModel aClass = new ClassModel("AClass");
        aClass.setScope(ScopeType.PRIVATE);
        panel.addClassToList(aClass);
        assertEquals("private", aTable.getModel().getValueAt(0, 0));
        assertEquals("ACLass", aTable.getModel().getValueAt(1, 0));
    }

    /**
     * Test of addMethodToList method, of class ModelListPanel.
     */
    @Test
    public void testAddMethodToList() {
        System.out.println("addMethodToList");
        MethodModel aMethod = null;
        ModelListPanel instance = new ModelListPanel();
        instance.addMethodToList(aMethod);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillTable method, of class ModelListPanel.
     */
    @Test
    public void testFillTable() {
        System.out.println("fillTable");
        ModelListPanel instance = new ModelListPanel();
        instance.fillTable();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
