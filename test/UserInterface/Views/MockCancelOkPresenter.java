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

    @Override
    public void cancel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ok() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
