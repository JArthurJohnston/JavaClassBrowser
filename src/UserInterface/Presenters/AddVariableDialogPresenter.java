/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import Models.BaseModel;
import Models.ClassModel;
import Types.ClassType;
import UserInterface.Views.BaseView;
import javax.swing.Action;
import javax.swing.JFrame;

/**
 *
 * @author arthur
 */
public class AddVariableDialogPresenter extends BasePresenter {

    private final VariableListPresenter listPresenter;

    public AddVariableDialogPresenter(BasePresenter parentPresenter) {
        super(parentPresenter);
        listPresenter = new VariableListPresenter(this);
    }

    @Override
    protected BaseView getView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void selectionChanged(BaseModel aModel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClassModel getSelectedClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Action[] getRightClickMenuActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JFrame getParentFrame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
