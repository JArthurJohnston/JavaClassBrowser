/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.EditPanels;

import Models.ClassModel;

/**
 *
 * @author arthur
 */
public class ClassEditPanel extends BaseEditPanel {

    /**
     * Creates new form ClassEditPanel
     */
    public ClassEditPanel() {
        initComponents();
    }
    
    public void setModel(ClassModel aClass){
        this.classDescriptionField.setText(aClass.getDescription());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        classDescriptionField = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());
        add(classDescriptionField, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel classDescriptionField;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void applyChanges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void revertChanges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
