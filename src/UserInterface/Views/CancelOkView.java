/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.Presenters.CancelOkInterface;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author arthur
 */
public class CancelOkView extends BaseView {

    public CancelOkView(CancelOkInterface presenter) {
        super();
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setName("background");
        panel.setLayout(new GridBagLayout());
        this.add(panel);

    }

}
