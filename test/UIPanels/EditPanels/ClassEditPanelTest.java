/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.EditPanels;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import Models.ClassModel;
import Types.ScopeType;
import javax.swing.JTextPane;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ClassEditPanelTest extends BaseTest{
    private ClassEditPanel panel;
    
    public ClassEditPanelTest() {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        panel = new ClassEditPanel();
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        panel = null;
    }
    
    private ClassModel getTestClass(){
        ClassModel aClass = null;
        ClassModel subClass = null;
        try {
            aClass = parentPackage.addClass(new ClassModel("TestClass"));
            subClass = aClass.addClass(new ClassModel("SubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        subClass.setScope(ScopeType.PRIVATE);
        return subClass;
    }

    /**
     * Test of setModel method, of class ClassEditPanel.
     */
    @Test
    public void testSetModel() {
        panel.setModel(this.getTestClass());
        JTextPane pane = (JTextPane)this.getVariableFromClass(panel, "classDeclarationField");
        assertEquals("private class SubClass extends TestClass", pane.getText());
        this.showPanel(panel);
    }
    
}
