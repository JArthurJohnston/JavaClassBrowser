/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

/**
 *
 * @author Arthur
 */
public class ClassFieldsPanel extends javax.swing.JPanel {

    /**
     * Creates new form ClassFieldsPanel
     */
    public ClassFieldsPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        instanceVarList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        staticVarList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        imporList = new javax.swing.JList();

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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList imporList;
    private javax.swing.JList instanceVarList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList staticVarList;
    // End of variables declaration//GEN-END:variables
}
