/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Dialogs;

import Models.BaseModel;

/**
 *
 * @author arthur
 */
public enum OpenDialog {

    NEW_METHOD(new BaseDialog());

    private final BaseDialog dialog;

    private OpenDialog(BaseDialog dialog) {
        this.dialog = dialog;
    }

    public void openDialogUsing(BaseModel aModel) {

    }
}
