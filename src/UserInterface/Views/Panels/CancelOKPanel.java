/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views.Panels;

import java.awt.BorderLayout;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author arthur
 */
public class CancelOKPanel extends JPanel {

    private final JButton okButton;
    private final JButton cancelButton;

    public CancelOKPanel(final AbstractAction okAction,
            final AbstractAction cancelAction) {
        setLayout(new BorderLayout());
        okButton = new JButton(okAction);
        add(okButton, BorderLayout.EAST);
        cancelButton = new JButton(cancelAction);
        add(cancelButton, BorderLayout.WEST);
    }
}
