/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels;

import Models.BaseModel;
import Models.ClassModel;
import Models.MethodModel;
import Types.ClassType;

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
        this.setVisible(true);
    }
    
    private void setUpTable(){
        this.modelTable.getSelectionModel()
                .addListSelectionListener(this.rowSelectionListener());
    }
    
    public void addMethodToList(MethodModel aMethod){
        this.tableModel(modelTable).addRow(
                new CellModel[] 
                   {new CellModel(aMethod.scopeString(), aMethod), 
                    new CellModel(aMethod.getReturnType().name(), aMethod),
                    new CellModel(aMethod.getType().toString().toLowerCase(), aMethod)});
    }
    
    @Override
    public BaseModel getSelected(){
        return ((CellModel)modelTable.getModel()
                .getValueAt(modelTable.getSelectedColumn(), 
                            modelTable.getSelectedRow()))
                                .getBase(); 
    }
    
    @Override
    public void selectionChanged(BaseModel aModel){
        if(aModel.isClass())
            this.fillListFromClass((ClassModel)aModel);
    }
    
    private void fillListFromClass(ClassModel aModel){
        for(MethodModel m : aModel.getStaticMethods())
            if(m.getType() == this.type)
                this.addMethodToList(m);
    }
    
    public void setSelectionType(ClassType aType){
        this.type = aType;
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

        modelTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Scope", "Name", "Return Type"
            }
        ));
        jScrollPane1.setViewportView(modelTable);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable modelTable;
    // End of variables declaration//GEN-END:variables


}
