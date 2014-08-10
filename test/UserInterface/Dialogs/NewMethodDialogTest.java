/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Dialogs;

import Models.MethodModel;
import UserInterface.BaseUserInterfaceTest;
import UserInterface.Presenters.MockPresenters.MockPresenter;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.junit.After;
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

    @Before
    @Override
    public void setUp() {
        parentPresenter = new MockPresenter();
        dialog = new NewMethodDialog(new MockPresenter());
    }

    @After
    @Override
    public void tearDown() {
        parentPresenter = null;
        dialog = null;
    }

    @Test
    public void testConstructor() {
        assertEquals("New Method", dialog.getTitle());
        JButton okButton
                = (JButton) this.getComponentByName(
                        dialog.getContentPane(), "okButton");
        assertEquals("OK", okButton.getText());
        assertSame(BorderLayout.class, dialog.getLayout().getClass());
    }

}
