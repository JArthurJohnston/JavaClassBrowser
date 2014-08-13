/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views.NetbeansViews;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.JPopupMenu;

/**
 *
 * @author arthur
 */
public class MyPopupMenu extends JPopupMenu {

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
                    MyPopupMenu.this.show(e.getComponent(), e.getX(), e.getY());
            }

        };
    }
}
