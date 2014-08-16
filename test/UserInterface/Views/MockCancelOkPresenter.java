/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Views;

import UserInterface.Presenters.Interfaces.CancelOkInterface;
import UserInterface.Presenters.MockPresenters.MockPresenter;

/**
 *
 * @author arthur
 */
public class MockCancelOkPresenter extends MockPresenter implements CancelOkInterface {

    public MockCancelOkPresenter() {
        super();
    }

    @Override
    public void cancel() {
        System.out.println("cancel has been pressed");
    }

    @Override
    public void ok() {
        System.out.println("ok has been pressed");
    }

}
