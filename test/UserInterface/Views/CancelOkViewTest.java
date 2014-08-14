/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.BaseUserInterfaceTest;
import UserInterface.Presenters.CancelOkInterface;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class CancelOkViewTest extends BaseUserInterfaceTest {

    private CancelOkView view;

    public CancelOkViewTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        view = new CancelOkView(new MockCancelOkPresenter());
    }

    @After
    public void tearDown() {
        view = null;
    }

    @Test
    public void testConstructor() throws Exception {
        assertTrue(BaseView.class.isAssignableFrom(CancelOkView.class));
        assertSame(BorderLayout.class, view.getLayout().getClass());
    }

    @Test
    public void testViewFields() throws Exception {
        JPanel panel = (JPanel) this.assertComponentExistsAndGet(view, "background");
        assertSame(GridBagLayout.class, panel.getLayout().getClass());

    }

}
