/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Types.ClassType;
import Types.ScopeType;
import UIModels.SystemBrowserShellModel;
import javax.swing.JTextArea;
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
public class ModelEditPanelTest extends BaseTest{
    private ModelEditPanel panel;
    
    public ModelEditPanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        panel = new ModelEditPanel();
    }
    
    @After
    public void tearDown() {
        panel = null;
    }
    
    private MethodModel setUpMethod(){
        MethodModel aMethod = new MethodModel();
        aMethod.setName("aMethodForTesting");
        aMethod.setReturnType(new ClassModel("ReturnedObject"));
        aMethod.setType(ClassType.INSTANCE);
        aMethod.setScope(ScopeType.PRIVATE);
        aMethod.setSource("return null;");
        return aMethod;
    }

    @Test
    public void testModelEditArea() {
        fail();
        /*
         * 
         */
    }
    @Test
    public void testEditModelUpdatesOtherShells(){
        fail();
    }
    
    @Test
    public void testSetMethod(){
        MethodModel aMethod = this.setUpMethod();
        JTextArea source = (JTextArea)this.getVariableFromClass(panel, "modelEditTextArea");
        JTextArea comment = (JTextArea)this.getVariableFromClass(panel, "commentTextArea");
        assertTrue(this.compareStrings("", source.getText()));
        assertTrue(this.compareStrings("", comment.getText()));
        panel.setSelected(aMethod);
        assertTrue(this.compareStrings(aMethod.toSourceString() , source.getText()));
        assertTrue(this.compareStrings(aMethod.getComment() , comment.getText()));
        
    }
}
