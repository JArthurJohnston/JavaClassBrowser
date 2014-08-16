/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.Presenters.Interfaces.CancelOkInterface;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author arthur
 */
public class CancelOkView extends BaseView {

    private final CancelOkInterface presenter;

    public CancelOkView(CancelOkInterface presenter) {
        super();
        this.presenter = presenter;
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setName("background");

        GridBagLayout layout = new GridBagLayout();

        panel.setLayout(layout);
        this.add(panel);

        JButton okButton = new JButton();
        okButton.setName("okButton");

        okButton.setSize(50, 50);

        layout.setConstraints(okButton,
                new GridBagConstraints(0, 0, 0, 0, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        0,
                        new Insets(0, 0, 0, 0), 0, 0));

        panel.add(okButton);
    }

    private AbstractAction okButtonAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }

        };
    }

    public CancelOkInterface getPresenter() {
        return this.presenter;
    }

}
