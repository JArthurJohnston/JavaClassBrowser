/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import Models.BaseModel;
import Models.ClassModel;
import UserInterface.Views.ListView;
import UserInterface.Views.NetbeansViews.ListPanelView;
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

    protected ListPanelView view;
    protected DefaultTableModel model;
    protected String[] modelLabels;

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
        if (this.modelLabels != null)
            return new DefaultTableModel(new BaseModel[][]{},
                    this.modelLabels);
        return new DefaultTableModel();
    }

    @Override
    protected void selectionChanged(BaseModel aModel) {
        selected = aModel;
    }

    @Override
    public ListPanelView getView() {
        if (view == null)
            view = new ListPanelView(this);
        return view;
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
    public Action[] getRightClickMenuActions() {
        return new Action[]{};
    }

    @Override
    public JFrame getParentFrame() {
        return parentPresenter.getParentFrame();
    }

    void setModelLabels(String[] labels) {
        this.modelLabels = labels;
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
