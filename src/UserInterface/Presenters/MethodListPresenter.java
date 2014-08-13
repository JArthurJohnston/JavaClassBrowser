/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import Models.BaseModel;
import Models.ClassModel;
import Models.MethodModel;
import Types.ClassType;
import UserInterface.Views.ListView;
import UserInterface.Views.NetbeansViews.ListPanelView;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arthur
 */
public class MethodListPresenter extends ListPresenter {

    private final ClassType type;

    protected MethodListPresenter(BasePresenter parentPresenter, ClassType aType) {
        super(parentPresenter);
        type = aType;
        this.fillModelFromClass(parentPresenter.getSelectedClass());
    }

    @Override
    public DefaultTableModel setUpTableModel() {
        return new DefaultTableModel(new Object[][]{},
                new String[]{"Scope", "Name", "Return Type"});
    }

    private void fillModelFromClass(ClassModel aClass) {
        for (MethodModel method : aClass.getMethodsOfType(type)) {
            if (selected == null)
                selected = method;
            this.addMethodToList(method);
        }
    }

    @Override
    public ListPanelView getView() {
        if (view == null)
            view = new ListPanelView(this);
        return view;
    }

    private void addMethodToList(MethodModel aMethod) {
        this.getTableModel().addRow(
                new CellModel[]{new CellModel(aMethod.scopeString(), aMethod),
                    new CellModel(aMethod.name(), aMethod),
                    new CellModel(aMethod.getReturnType().name(), aMethod)});
    }

    @Override
    public void selectionChanged(BaseModel aModel) {
        if (aModel.isClass()) {
            this.clear();
            this.fillModelFromClass((ClassModel) aModel);
            return;
        }
        if (aModel.isMethod())
            if (aModel.getType().equals(this.type))
                super.selectionChanged(aModel);
    }

    @Override
    public ClassModel getSelectedClass() {
        return ((MethodModel) selected).getParentClass();
    }

    @Override
    public Action[] getRightClickMenuActions() {
        return new Action[]{
            new AbstractAction("Add Method") {

                @Override
                public void actionPerformed(ActionEvent e) {
                }
            }
        };
    }

    @Override
    public String getTableName() {
        return "methodList";
    }

}
