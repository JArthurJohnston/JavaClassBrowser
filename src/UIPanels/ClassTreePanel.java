/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Models.ClassModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Arthur
 */
public class ClassTreePanel extends BaseTreePanel {

    /**
     * Creates new form ClassTreePanel
     */
    public ClassTreePanel() {
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ClassTree = new javax.swing.JTree();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Classes");
        add(jLabel1, java.awt.BorderLayout.NORTH);
        jLabel1.getAccessibleContext().setAccessibleDescription("");

        jScrollPane1.setViewportView(ClassTree);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree ClassTree;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    
}
