/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import MainBase.UsefulList;
import Models.BaseModel;
import Models.ClassModel;
import Models.VariableModel;
import UIModels.BrowserUIModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author Arthur
 */
public class ClassFieldsPanel extends BasePanel {
    
    /**
     * Creates new form ClassFieldsPanel
     */
    public ClassFieldsPanel() {
        initComponents();
        this.setUpRightClickMenues();
    }
    
    @Override
    public void setModel(BrowserUIModel aModel){
        this.model = aModel;
        this.setUpLists();
        this.fillListsFromClass(model.getSelectedClass());
    }
    
    public void fillListsFromClass(ClassModel aClass){
        if(aClass == null)
            return;
        for(VariableModel v : aClass.getInstanceVars())
            ((DefaultListModel)this.instanceVarList.getModel()).addElement(v);
        for(VariableModel v : aClass.getStaticVars())
            ((DefaultListModel)this.staticVarList.getModel()).addElement(v);
    }
    
    @Override
    protected UsefulList<JList> myLists(){
        return super.myLists()
                .addElm(imporList)
                .addElm(instanceVarList)
                .addElm(staticVarList);
    }
    
    @Override
    public void selectionChanged(BaseModel aClass){
        this.clearLists();
        if(aClass == null || !aClass.isClass())
            return;
        this.fillListsFromClass((ClassModel)aClass);
    }
    
    public void setUpRightClickMenues(){
        this.setUpRightClick(instanceVarList, varRightClickMenu);
        this.setUpRightClick(staticVarList, varRightClickMenu);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        varRightClickMenu = new javax.swing.JPopupMenu();
        addVarMenuItem = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        instanceVarList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        staticVarList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        imporList = new javax.swing.JList();

        addVarMenuItem.setText("New Variable");
        addVarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVarMenuItemActionPerformed(evt);
            }
        });
        varRightClickMenu.add(addVarMenuItem);

        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jScrollPane1.setViewportView(instanceVarList);

        jTabbedPane1.addTab("Instance Variables", jScrollPane1);

        jScrollPane2.setViewportView(staticVarList);

        jTabbedPane1.addTab("Static Variables", jScrollPane2);

        jScrollPane3.setViewportView(imporList);

        jTabbedPane1.addTab("Imports", jScrollPane3);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addVarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVarMenuItemActionPerformed
        
    }//GEN-LAST:event_addVarMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addVarMenuItem;
    private javax.swing.JList imporList;
    private javax.swing.JList instanceVarList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList staticVarList;
    private javax.swing.JPopupMenu varRightClickMenu;
    // End of variables declaration//GEN-END:variables
}
