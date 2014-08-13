/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import Internal.BaseTest;
import Models.ClassModel;
import Models.VariableModel;
import Types.ClassType;
import UserInterface.Presenters.MockPresenters.MockPresenter;
import UserInterface.Views.NetbeansViews.ListPanelView;
import javax.swing.table.DefaultTableModel;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class VariableListPresenterTest extends BaseTest {

    private VariableListPresenter presenter;
    private MockPresenter parentPresenter;

    public VariableListPresenterTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        parentPresenter = new MockPresenter();
        presenter = new VariableListPresenter(parentPresenter, ClassType.INSTANCE);
    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
        parentPresenter = null;
    }

    @Test
    public void testConstructorGetter() throws Exception {
        assertTrue(ListPresenter.class.isAssignableFrom(presenter.getClass()));
        assertSame(parentPresenter, presenter.getParentPresenter());
    }

    @Test
    public void testTableModel() throws Exception {
        DefaultTableModel model = presenter.getTableModel();
        assertEquals(0, model.getRowCount());
        assertEquals(2, model.getColumnCount());
        assertEquals("Name", model.getColumnName(0));
        assertEquals("Type", model.getColumnName(1));
    }

    @Test
    public void testGetViewIsSingleton() throws Exception {
        ListPanelView view = presenter.getView();
        assertSame(view, presenter.getView());
    }

    private ClassModel setUpClassWithVars() throws Exception {
        ClassModel aClass = parentPackage.addClass(new ClassModel("SomeClass"));
        VariableModel aVar = aClass.addVariable(
                new VariableModel(ClassModel.getObjectClass(), "object"));
        aVar.setType(ClassType.INSTANCE);
        aVar = aClass.addVariable(
                new VariableModel(ClassModel.getPrimitive("char"), "letter"));
        aVar.setType(ClassType.INSTANCE);
        aVar = aClass.addVariable(
                new VariableModel(ClassModel.getPrimitive("int"), "x"));
        aVar.setType(ClassType.STATIC);
        return aClass;
    }

    @Test
    public void testTestConstructedWithClass() throws Exception {
        ClassModel aClass = this.setUpClassWithVars();
        parentPresenter.setSelectedClass(aClass);
        presenter = new VariableListPresenter(parentPresenter, ClassType.STATIC);
        DefaultTableModel model = presenter.getTableModel();
        VariableModel aVar = aClass.findVariable("x");

        assertEquals(1, model.getRowCount());
        assertSame(aVar, presenter.getSelected());

        presenter = new VariableListPresenter(parentPresenter, ClassType.INSTANCE);
        model = presenter.getTableModel();

        assertEquals(2, model.getRowCount());
        assertSame(VariableModel.class, presenter.getSelected().getClass());
    }

}
