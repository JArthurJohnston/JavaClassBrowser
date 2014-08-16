/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.BaseUserInterfaceTest;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Action;
import javax.swing.JButton;
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
        presenter = new MockCancelOkPresenter();
        view = new CancelOkView(presenter);
    }
    private MockCancelOkPresenter presenter;

    @After
    public void tearDown() {
        view = null;
    }

    @Test
    public void testConstructor() throws Exception {
        assertTrue(BaseView.class.isAssignableFrom(CancelOkView.class));
        assertSame(BorderLayout.class, view.getLayout().getClass());
        assertSame(presenter, view.getPresenter());
    }

    @Test
    public void testViewFields() throws Exception {
        JPanel panel = (JPanel) this.assertComponentExistsAndGet(view, "background");
        assertSame(GridBagLayout.class, panel.getLayout().getClass());

        GridBagLayout layout = (GridBagLayout) panel.getLayout();

        JButton okButton = (JButton) this.assertComponentExistsAndGet(panel, "okButton");

        GridBagConstraints constraints = layout.getConstraints(okButton);
        assertEquals(0, constraints.gridx);
        assertEquals(0, constraints.gridy);
        assertEquals(0, constraints.gridheight);
        assertEquals(0, constraints.gridwidth);

        Insets insets = constraints.insets;
        assertEquals(0, insets.bottom);
        assertEquals(0, insets.left);
        assertEquals(0, insets.right);
        assertEquals(0, insets.top);

        assertEquals(new Dimension(50, 50), okButton.getSize());
    }

    @Test
    public void testButtonActions() throws Exception {
        JButton okButton = (JButton) this.assertComponentExistsAndGet(view, "okButton");
        assertSame(Action.class, okButton.getAction().getClass());
    }
}
