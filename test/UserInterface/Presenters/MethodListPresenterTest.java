/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import Models.ClassModel;
import Models.MethodModel;
import Types.ClassType;
import UserInterface.Dialogs.OpenDialog;
import UserInterface.Presenters.MockPresenters.MockPresenter;
import UserInterface.Views.ListView;
import java.awt.Component;
import java.awt.Container;
import javax.swing.table.DefaultTableModel;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class MethodListPresenterTest extends BaseTest {

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
    public void testAddMethod() throws Exception {
        presenter.addMethod();
        assertSame(OpenDialog.NEW_METHOD, parentPresenter.getLastDialog());
    }

    @Test
    public void testMethodSelection() throws Exception {
        ClassModel aClass = parentPresenter.getSelectedClass();
        MethodModel aMethod = aClass.getMethods().getFirst();
        DefaultTableModel model = presenter.getTableModel();
        ListView view = presenter.getView();

        view.getTable().setRowSelectionInterval(0, 0);

        assertSame(aMethod, presenter.getSelected());
    }

    public Component getComponentByName(Container view, String componentName) {
        for (Component comp : view.getComponents())
            if (comp != null)
                if (comp.getName() != null && comp.getName().equals(componentName))
                    return comp;
                else if (comp instanceof Container)
                    return getComponentByName((Container) comp, componentName);
        fail("component not found");
        return null;
    }
}
