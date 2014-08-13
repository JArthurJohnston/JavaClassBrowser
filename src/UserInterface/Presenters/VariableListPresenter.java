/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import Models.ClassModel;
import Models.VariableModel;
import Types.ClassType;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arthur
 */
public class VariableListPresenter extends ListPresenter {

    private final ClassType type;

    public VariableListPresenter(BasePresenter parentPresenter) {
        super(parentPresenter);
        type = null;
    }

    public VariableListPresenter(BasePresenter parentPresenter, ClassType type) {
        super(parentPresenter);
        this.type = type;
        if (parentPresenter.getSelectedClass() != null)
            this.fillModelFromClass(parentPresenter.getSelectedClass());
    }

    @Override
    public DefaultTableModel setUpTableModel() {
        return new DefaultTableModel(new VariableModel[][]{},
                new String[]{"Name", "Type"});
    }

    private void fillModelFromClass(ClassModel aClass) {
        for (VariableModel var : aClass.getVariablesOfType(type)) {
            if (selected == null)
                selected = var;
            this.addVarToList(var);
        }
    }

    private void addVarToList(VariableModel aVar) {
        this.getTableModel().addRow(
                new CellModel[]{
                    new CellModel(aVar.name(), aVar),
                    new CellModel(aVar.scopeString(), aVar)});
    }
}
