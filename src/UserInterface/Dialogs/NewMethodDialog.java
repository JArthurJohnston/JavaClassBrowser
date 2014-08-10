/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Dialogs;

import UserInterface.Presenters.BasePresenter;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 *
 * @author arthur
 */
public class NewMethodDialog extends JDialog {

    public NewMethodDialog(BasePresenter presenter) {
        super(presenter.getParentFrame(), "New Method");
        this.setLayout(new BorderLayout());
        JButton okButton = new JButton("OK");
        okButton.setName("okButton");
        this.getContentPane().add(okButton, BorderLayout.SOUTH);
    }
}
