/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.Presenters.ListPresenter;
import UserInterface.Presenters.MockPresenters.MockPresenter;
import javax.swing.JComponent;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ListViewTest {

    private ListView view;

    public ListViewTest() {
    }

    @Before
    public void setUp() {
        view = new ListView(new ListPresenter(new MockPresenter()));
    }

    @After
    public void tearDown() {
        view = null;
    }

    @Test
    public void testConstructorGetter() {
        assertTrue(JComponent.class.isAssignableFrom(view.getClass()));
        assertTrue(BaseView.class.isAssignableFrom(view.getClass()));
    }

}
