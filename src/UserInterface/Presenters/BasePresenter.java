/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import Models.BaseModel;
import Models.ClassModel;
import UserInterface.Dialogs.OpenDialog;
import UserInterface.Views.BaseView;

/**
 *
 * @author arthur
 */
public abstract class BasePresenter {

    protected BaseModel selected;
    protected final BasePresenter parentPresenter;

    public BasePresenter(BasePresenter parentPresenter) {
        this.parentPresenter = parentPresenter;
    }

    public BaseModel getSelected() {
        return selected;
    }

    protected BasePresenter getParentPresenter() {
        return parentPresenter;
    }

    abstract protected BaseView getView();

    abstract protected void selectionChanged(BaseModel aModel);

    public abstract ClassModel getSelectedClass();

    public abstract void openDialog(OpenDialog dialog);

}
