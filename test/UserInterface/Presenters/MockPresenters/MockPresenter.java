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
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;

/**
 *
 * @author arthur
 */
public class MockPresenter extends BasePresenter {

    BaseModel selected;
    ClassModel selectedClass;
    OpenDialog dialog;
    JFrame parentFrame;

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

    @Override
    public Action[] rightClickMenuActions() {
        return new Action[]{
            new TestAction(),
            new TestAction(),
            new TestAction()};
    }

    @Override
    public Action[] leftClickMenuActions() {
        return new Action[]{};
    }

    public void setActions(Action[] action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    @Override
    public JFrame getParentFrame() {
        return this.parentFrame;
    }

    public class TestAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    @Override
    public boolean isTesting() {
        return true;
    }

}
