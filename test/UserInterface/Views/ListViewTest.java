/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.BaseUserInterfaceTest;
import UserInterface.Presenters.ListPresenter;
import UserInterface.Presenters.MockPresenters.MockPresenter;
import UserInterface.Views.BaseView.MyPopupMenu;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ListViewTest extends BaseUserInterfaceTest {

    private ListView view;

    public ListViewTest() {
    }

    @Before
    @Override
    public void setUp() {
        view = new ListView(new ListPresenter(new MockPresenter()));
    }

    @After
    @Override
    public void tearDown() {
        view = null;
    }

    @Test
    public void testConstructorGetter() {
        assertTrue(JComponent.class.isAssignableFrom(view.getClass()));
        assertTrue(BaseView.class.isAssignableFrom(view.getClass()));
        assertSame(MyPopupMenu.class, this.assertComponentExistsAndGet(view, "rightClickMenu"));
    }

    @Test
    public void testRightClickMenu() throws Exception {
        MockPresenter parentPresenter = new MockPresenter();
        JPopupMenu menu = (JPopupMenu) this.assertComponentExistsAndGet(view, "rightClickMenu");
        assertEquals(1, menu.getSubElements().length);
    }

}
