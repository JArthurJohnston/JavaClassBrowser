/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views.NetbeansViews;

import UserInterface.Presenters.ListPresenter;
import UserInterface.Views.BaseView;

/**
 *
 * @author arthur
 */
public class ListPanelView extends BaseView {

    /**
     * Creates new form ListPanelView
     */
    public ListPanelView() {
        initComponents();
    }

    public ListPanelView(ListPresenter presenter) {
        this();
        table.setModel(presenter.getTableModel());
        table.getSelectionModel()
                .addListSelectionListener(
                        presenter.getListSelectionListener(table));
        table.addMouseListener(
                new MyPopupMenu(presenter.getRightClickMenuActions(), "rightClickMenu")
                .getPopupListener());
        table.setName(presenter.getTableName());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
