/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.Presenters.BasePresenter;
import UserInterface.Presenters.ListPresenter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author arthur
 */
public class BaseView extends JComponent {

    public BaseView(final BasePresenter presenter) {
        super();
        setLayout(new java.awt.BorderLayout());
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

        public MyTable(final ListPresenter presenter) {
            super();
            this.setModel(presenter.getTableModel());
            this.setAutoCreateRowSorter(true);
            this.getSelectionModel()
                    .addListSelectionListener(
                            presenter.getListSelectionListener(this));
            this.setName(presenter.getTableName());

            this.addMouseListener(
                    new MyPopupMenu(presenter.rightClickMenuActions(), "rightClickMenu")
                    .getPopupListener());
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    protected class MyPopupMenu extends JPopupMenu {

        public MyPopupMenu(final Action[] actions, final String name) {
            super();
            this.addActionsToMenu(actions);
            this.setName(name);
        }

        private void addActionsToMenu(Action[] actions) {
            for (Action act : actions)
                this.add(act);
        }

        public MouseAdapter getPopupListener() {
            return new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    this.showPopup(e);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    this.showPopup(e);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    this.showPopup(e);
                }

                private void showPopup(MouseEvent e) {
                    if (e.isPopupTrigger())
                        MyPopupMenu.this.show(BaseView.this, e.getX(), e.getY());
                }

            };
        }
    }
}
