/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.Presenters.ListPresenter;

/**
 *
 * @author arthur
 */
public class ListView extends BaseView {

    public ListView(ListPresenter presenter) {
        super(presenter);
        add(new MyScrollPane(new MyTable(presenter)),
                java.awt.BorderLayout.PAGE_START);
    }
}
