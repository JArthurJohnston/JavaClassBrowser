/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import Exceptions.AlreadyExistsException;
import Models.ClassModel;
import Models.MethodModel;
import Types.ClassType;
import UserInterface.BaseUserInterfaceTest;
import UserInterface.Presenters.MockPresenters.MockPresenter;
import UserInterface.Views.NetbeansViews.ListPanelView;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class MethodListPresenterTest extends BaseUserInterfaceTest {

    private MethodListPresenter presenter;
    private MockPresenter parentPresenter;

    public MethodListPresenterTest() {
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        parentPresenter = new MockPresenter();
        parentPresenter.setSelectedClass(this.setUpClassWithMethods());
        presenter = new MethodListPresenter(parentPresenter, ClassType.STATIC);
    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
        presenter = null;
        parentPresenter = null;
    }

    private ClassModel setUpClassWithMethods() throws AlreadyExistsException {
        ClassModel aClass = parentPackage.addClass(new ClassModel("AClass"));
        MethodModel aMethod = aClass.addMethod(new MethodModel("aMethod"));
        aMethod.setType(ClassType.STATIC);
        aMethod = aClass.addMethod(new MethodModel("anotherMethod"));
        aMethod.setType(ClassType.STATIC);
        aMethod = aClass.addMethod(new MethodModel("yetAnotherMethod"));
        aMethod.setType(ClassType.INSTANCE);
        return aClass;
    }

    @Test
    public void testGetViewIsSingleton() throws Exception {
        ListPanelView view = presenter.getView();
        assertSame(ListPanelView.class, view.getClass());
        assertSame(view, presenter.getView());
    }

    @Test
    public void testConstructorGetter() throws Exception {
        ClassModel aClass = parentPresenter.getSelectedClass();
        MethodModel expectedMethod = aClass.getMethods().getFirst();
        presenter = new MethodListPresenter(parentPresenter, ClassType.STATIC);

        assertEquals(parentPresenter, presenter.getParentPresenter());
        assertTrue(ListPresenter.class.isAssignableFrom(presenter.getClass()));
        assertSame(expectedMethod, presenter.getSelected());
        assertEquals("methodList", presenter.getTableName());

        DefaultTableModel model = presenter.getTableModel();

        assertEquals(3, model.getColumnCount());
        assertEquals("Scope", model.getColumnName(0));
        assertEquals("Name", model.getColumnName(1));
        assertEquals("Return Type", model.getColumnName(2));
        assertEquals(2, model.getRowCount());

        MethodModel aMethod = aClass.getMethods().getLast();
        presenter = new MethodListPresenter(parentPresenter, ClassType.INSTANCE);
        model = presenter.getTableModel();

        assertEquals(3, model.getColumnCount());
        assertEquals(1, model.getRowCount());
        assertSame(aMethod, presenter.getSelected());

        presenter = new MethodListPresenter(parentPresenter, ClassType.STATIC);
        aMethod = aClass.getMethods().getFirst();
        model = presenter.getTableModel();

        assertEquals(3, model.getColumnCount());
        assertEquals(2, model.getRowCount());
        assertSame(aMethod, presenter.getSelected());
    }

    @Test
    public void testClear() throws Exception {
        DefaultTableModel model = presenter.getTableModel();
        assertEquals(3, model.getColumnCount());
        assertEquals(2, model.getRowCount());
        assertSame(MethodModel.class, presenter.getSelected().getClass());
        presenter.clear();

        assertEquals(0, model.getRowCount());
        assertNull(presenter.getSelected());
    }

    @Test
    public void testSelectionChanged() throws Exception {
        ClassModel aClass = parentPresenter.getSelectedClass();
        MethodModel aMethod = aClass.getMethods().getFirst();
        presenter = new MethodListPresenter(parentPresenter, ClassType.STATIC);
        assertSame(aMethod, presenter.getSelected());

        aMethod = aClass.getMethods().get(1);
        assertSame(ClassType.STATIC, aMethod.getType());

        presenter.selectionChanged(aMethod);
        assertSame(aMethod, presenter.getSelected());

        aMethod = aClass.getMethods().get(2);
        presenter.selectionChanged(aMethod);
        assertNotSame(aMethod, presenter.getSelected());

        presenter.selectionChanged(aClass);
        assertNotSame(aClass, presenter.getSelected());
        assertSame(aClass.getMethods().getFirst(), presenter.getSelected());
    }

    @Test
    public void testMethodSelection() throws Exception {
        ClassModel aClass = parentPresenter.getSelectedClass();
        MethodModel aMethod = aClass.getMethods().getFirst();
        ListPanelView view = presenter.getView();

        JTable table = this.getTableFromView(view);

        table.setRowSelectionInterval(0, 0);
        assertSame(aMethod, presenter.getSelected());

        table.setRowSelectionInterval(1, 1);
        aMethod = aClass.getMethods().get(1);
        assertSame(aMethod, presenter.getSelected());
    }

    @Test
    public void testGetParentFrame() throws Exception {
        JFrame parentFrame = new JFrame();
        parentPresenter.setParentFrame(parentFrame);
        assertSame(parentFrame, presenter.getParentFrame());
    }

    @Test
    public void testRightClickActions() throws Exception {
        final Action[] actions = presenter.getRightClickMenuActions();
        assertEquals(1, actions.length);
        Action act = actions[0];
        assertTrue(act.isEnabled());
    }

    private JTable getTableFromView(JComponent view) {
        return (JTable) this.assertComponentExistsAndGet(view, "methodList");
    }
}
