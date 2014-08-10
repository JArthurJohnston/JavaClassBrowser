/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import Models.BaseModel;
import Models.ClassModel;
import UserInterface.Dialogs.OpenDialog;
import UserInterface.Views.ListView;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arthur
 */
public class ListPresenter extends BasePresenter {

    protected DefaultTableModel model;

    public ListPresenter(BasePresenter parentPresenter) {
        super(parentPresenter);
    }

    public ListSelectionListener getListSelectionListener(final JTable table) {
        return new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                if (!e.getValueIsAdjusting())
                    selectionChanged(this.getValueAt(table.getSelectedRow()));
            }

            private BaseModel getValueAt(final int index) {
                return ((CellModel) model.getValueAt(index, 0)).getBase();
            }
        };
    }

    public DefaultTableModel getTableModel() {
        if (model == null)
            model = this.setUpTableModel();
        return model;
    }

    protected DefaultTableModel setUpTableModel() {
        return new DefaultTableModel();
    }

    @Override
    protected void selectionChanged(BaseModel aModel) {
        selected = aModel;
    }

    @Override
    public ListView getView() {
        return new ListView(this);
    }

    protected void clear() {
        model.setRowCount(0);
        selected = null;
    }

    public String getTableName() {
        return "table";
    }

    @Override
    public ClassModel getSelectedClass() {
        return null;
    }

    @Override
    public void openDialog(OpenDialog dialog) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Action[] rightClickMenuActions() {
        return new Action[]{};
    }

    @Override
    public Action[] leftClickMenuActions() {
        return new Action[]{};
    }

    @Override
    public JFrame getParentFrame() {
        return parentPresenter.getParentFrame();
    }

    /**
     * acts as a wrapper. so that a tables cell can contain both the string it
     * needs to display to the user, and the object to which that string belongs
     */
    protected class CellModel {

        private final BaseModel base;
        private final String label;

        public CellModel(String label, BaseModel base) {
            this.label = label;
            this.base = base;
        }

        @Override
        public String toString() {
            return label;
        }

        public BaseModel getBase() {
            return base;
        }
    }
}
