/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.Presenters.BasePresenter;
import UserInterface.Presenters.ListPresenter;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author arthur
 */
public class BaseView extends JComponent {

    public BasePresenter getPresenter() {
        return null;
    }
    /*
     These inner classes exist solely to avoid having to use assignment
     statements. Because functional-ish programming FTW

     */

    protected class MyScrollPane extends JScrollPane {

        public MyScrollPane(JComponent comp) {
            super();
            this.setViewportView(comp);
        }
    }

    protected class MyTable extends JTable {

        public MyTable(ListPresenter presenter) {
            super();
            this.setModel(presenter.getTableModel());
            this.setAutoCreateRowSorter(true);
            this.getSelectionModel()
                    .addListSelectionListener(
                            presenter.getListSelectionListener());
            this.setName(presenter.getTableName());
        }
    }
}
