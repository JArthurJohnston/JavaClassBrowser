/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters.MockPresenters;

import Models.BaseModel;
import Models.ClassModel;
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
    private final JFrame parentFrame;

    public MockPresenter() {
        super(null);
        parentFrame = new JFrame();
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

    @Override
    public ClassModel getSelectedClass() {
        return selectedClass;
    }

    @Override
    public Action[] getRightClickMenuActions() {
        return new Action[]{
            new TestAction(),
            new TestAction(),
            new TestAction()};
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
