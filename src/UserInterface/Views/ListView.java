/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.Presenters.ListPresenter;
import javax.swing.JTable;

/**
 *
 * @author arthur
 */
public class ListView extends BaseView {

    private final JTable table;

    public ListView(ListPresenter presenter) {
        setLayout(new java.awt.BorderLayout());
        table = new MyTable(presenter);
        add(new MyScrollPane(table),
                java.awt.BorderLayout.PAGE_START);
    }

    public JTable getTable() {
        return this.table;
    }
}
