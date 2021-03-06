/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import MainBase.SortedList;
import Types.ClassType;

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
        instVarList.setSelectionType(ClassType.INSTANCE);
        statVarList.setSelectionType(ClassType.STATIC);
        this.setUpRightClickMenues();
    }
    
    @Override
    public SortedList<BasePanel> myPanels(){
        return new SortedList()
                .addElm(instVarList)
                .addElm(statVarList)
                .addElm(importList);
    }
    
    private void setUpRightClickMenues(){
        this.setUpRightClickListener(instVarList, varRightClickMenu);
        this.setUpRightClickListener(statVarList, varRightClickMenu);
    }
    
    public ClassType getSelectedVarType(){
        if(tabs.getSelectedIndex() == 2)
            return ClassType.STATIC;
        return ClassType.INSTANCE;
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
        tabs = new javax.swing.JTabbedPane();
        instVarList = new UIPanels.ListPanels.VariableListPanel();
        statVarList = new UIPanels.ListPanels.VariableListPanel();
        importList = new UIPanels.ListPanels.ImportListPanel();

        addVarMenuItem.setText("New Variable");
        addVarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVarMenuItemActionPerformed(evt);
            }
        });
        varRightClickMenu.add(addVarMenuItem);

        setLayout(new java.awt.BorderLayout());

        tabs.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tabs.setToolTipText("");
        tabs.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabs.addTab("Instance", instVarList);
        tabs.addTab("Static", statVarList);
        tabs.addTab("Imports", importList);

        add(tabs, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addVarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVarMenuItemActionPerformed
        controller.openAddVariable();
    }//GEN-LAST:event_addVarMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addVarMenuItem;
    private UIPanels.ListPanels.ImportListPanel importList;
    private UIPanels.ListPanels.VariableListPanel instVarList;
    private UIPanels.ListPanels.VariableListPanel statVarList;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JPopupMenu varRightClickMenu;
    // End of variables declaration//GEN-END:variables

}
