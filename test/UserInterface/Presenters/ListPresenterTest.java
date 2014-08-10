/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import UserInterface.Presenters.MockPresenters.MockPresenter;
import UserInterface.Views.ListView;
import javax.swing.table.DefaultTableModel;
import org.junit.After;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ListPresenterTest {

    private ListPresenter presenter;

    public ListPresenterTest() {
    }

    @Before
    public void setUp() {
        presenter = new ListPresenter(new MockPresenter());
    }

    @After
    public void tearDown() {
        presenter = null;
    }

    @Test
    public void testConstructorGetter() {
        assertTrue(BasePresenter.class.isAssignableFrom(presenter.getClass()));
        assertSame(ListView.class, presenter.getView().getClass());
        assertSame(DefaultTableModel.class, presenter.getTableModel().getClass());
        assertNull(presenter.getSelected());
        assertSame(MockPresenter.class, presenter.getParentPresenter().getClass());
    }

    @Test
    public void testGetTableModelAndModelIsSingleton() {
        DefaultTableModel model = presenter.getTableModel();
        assertSame(DefaultTableModel.class, model.getClass());
        assertSame(model, presenter.getTableModel());
    }

}