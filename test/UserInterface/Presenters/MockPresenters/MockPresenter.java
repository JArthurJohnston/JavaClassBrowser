/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters.MockPresenters;

import Models.BaseModel;
import Models.ClassModel;
import UserInterface.Dialogs.OpenDialog;
import UserInterface.Presenters.BasePresenter;
import UserInterface.Views.BaseView;

/**
 *
 * @author arthur
 */
public class MockPresenter extends BasePresenter {

    BaseModel selected;
    ClassModel selectedClass;
    OpenDialog dialog;

    public MockPresenter() {
        super(null);
    }

    public void setSelectedClass(ClassModel aClass) {
        this.selectedClass = aClass;
    }

    @Override
    protected BaseView getView() {
        return null;
    }

    @Override
    protected void selectionChanged(BaseModel aModel) {
        this.selected = aModel;
    }

    public ClassModel getSelectedClass() {
        return selectedClass;
    }

    public OpenDialog getLastDialog() {
        return this.dialog;
    }

    @Override
    public void openDialog(OpenDialog dialog) {
        this.dialog = dialog;
    }

}
