/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.ListPanels;

import Models.BaseModel;
import Models.ClassModel;
import Models.MethodModel;
import Types.ClassType;
import UIModels.BrowserUIController;
import java.util.LinkedList;
import javax.swing.JTable;

/**
 *
 * @author arthur
 */
public class MethodlListPanel extends BaseListPanel {
    private ClassType type;
    
    /**
     * Creates new form ModelListPanel
     */
    public MethodlListPanel() {
        initComponents();
        this.setUpTable();
    }
    
    public void setSelectionType(ClassType aType){
        this.type = aType;
    }
    
    public void addMethodToList(MethodModel aMethod){
        this.tableModel(modelTable).addRow(
                new CellModel[] 
                   {new CellModel(aMethod.scopeString(), aMethod), 
                    new CellModel(aMethod.getReturnType().name(), aMethod),
                    new CellModel(aMethod.getType().toString().toLowerCase(), aMethod)});
    }
    
    @Override
    protected JTable table(){
        return modelTable;
    }
    
    @Override
    public void setModel(BrowserUIController controller){
        super.setModel(controller);
        if(controller.getSelectedClass() != null)
            this.fillListFromClass(controller.getSelectedClass());
    }
    
    private void fillListFromClass(ClassModel aModel){
        if(aModel == null)
            return;
        this.clear();
        if(this.type == ClassType.INSTANCE)
            this.fillListFromList(aModel.getInstanceMethods());
        if(this.type == ClassType.STATIC)
            this.fillListFromList(aModel.getStaticMethods());
    }
    
    private void fillListFromList(LinkedList<MethodModel> aList){
        for(MethodModel m : aList)
            if(m.getType() == this.type)
                this.addMethodToList(m);
    }
    
    @Override
    public void selectionChanged(BaseModel aModel){
        if(aModel.isClass())
            this.fillListFromClass((ClassModel)aModel);
    }
    
    @Override
    public void modelAdded(BaseModel aModel){
        if(this.checkModel(aModel))
            this.addMethodToList((MethodModel)aModel);
    }
    
    @Override
    protected boolean checkModel(BaseModel aModel){
        if(super.checkModel(aModel)) 
            if(aModel.isMethod())
                if(((MethodModel)aModel).getType() == this.type)
                    if(((MethodModel)aModel).getParent() == controller.getSelectedClass())
                        return true;
        return false;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        modelTable = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        modelTable.setAutoCreateRowSorter(true);
        modelTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Scope", "Name", "Return Type"
            }
        ));
        jScrollPane1.setViewportView(modelTable);

        add(jScrollPane1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable modelTable;
    // End of variables declaration//GEN-END:variables


}
