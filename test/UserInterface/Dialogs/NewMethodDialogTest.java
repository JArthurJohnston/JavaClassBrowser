/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Dialogs;

import Models.ClassModel;
import Models.MethodModel;
import UserInterface.BaseUserInterfaceTest;
import UserInterface.Presenters.MockPresenters.MockPresenter;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class NewMethodDialogTest extends BaseUserInterfaceTest {

    private NewMethodDialog dialog;
    private MockPresenter parentPresenter;

    public NewMethodDialogTest() {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        parentPresenter = new MockPresenter();
        dialog = new NewMethodDialog(parentPresenter);
    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
        parentPresenter = null;
        dialog = null;
    }

    @Test
    public void testConstructor() throws Exception {
        ClassModel aClass = parentPackage.addClass(new ClassModel("SomeClass"));
        parentPresenter.setSelectedClass(aClass);
        dialog = new NewMethodDialog(parentPresenter);

        assertSame(aClass, this.getVariableFromClass(dialog, "baseClass"));
        assertEquals("New Method", dialog.getTitle());
        assertTrue(dialog.isModal());
        assertSame(parentPresenter.getParentFrame(), dialog.getOwner());

        assertSame(MethodModel.class,
                this.getVariableFromClass(dialog, "newMethod").getClass());

    }

    @Test
    public void testComponents() throws Exception {
        ClassModel aClass = parentPackage.addClass(new ClassModel("SomeClass"));
        parentPresenter.setSelectedClass(aClass);
        dialog = new NewMethodDialog(parentPresenter);

        JTextField field = (JTextField) this.getVariableFromClass(dialog, "newMethodName");
        assertEquals("newMethod", field.getText());

    }
}
