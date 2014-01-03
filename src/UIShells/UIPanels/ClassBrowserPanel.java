/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells.UIPanels;

import UIModels.UIPanelsModels.ClassBrowserPanelModel;

/**
 *
 * @author Arthur
 */
public class ClassBrowserPanel extends javax.swing.JPanel {
    private ClassBrowserPanelModel model;

    /**
     * Creates new form ClassBrowserPanel
     */
    public ClassBrowserPanel() {
        initComponents();
    }
    public ClassBrowserPanel(ClassBrowserPanelModel model){
        this.model = model;
        initComponents();
        this.setUpModels();
        this.setVisible(true);
    }
    
    private void setUpModels(){
        this.classMethodList.setModel(model.getClassMethodList());
        this.instanceMethodList.setModel(model.getInstMethodList());
        this.classVaraibleList.setModel(model.getClassVarsList());
        this.instanceVariableList.setModel(model.getInstVarList());
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
        jScrollPane2 = new javax.swing.JScrollPane();
        classVaraibleList = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        instanceVariableList = new javax.swing.JList();
        addVariableButton = new javax.swing.JButton();
        variableLabel = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        classMethodList = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        constructorList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        instanceMethodList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        removeVariableButton = new javax.swing.JButton();
        addMetodButton = new javax.swing.JButton();
        removeMethodButton = new javax.swing.JButton();
        showInheritedMethodsButton = new javax.swing.JButton();
        showInheritedVariablesButton = new javax.swing.JButton();

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jScrollPane2.setViewportView(classVaraibleList);

        jTabbedPane1.addTab("Static Variables", jScrollPane2);

        jScrollPane1.setViewportView(instanceVariableList);

        jTabbedPane1.addTab("Instance Variables", jScrollPane1);

        addVariableButton.setText("+");

        variableLabel.setText("Variables");

        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jScrollPane4.setViewportView(classMethodList);

        jTabbedPane2.addTab("Static Methods", jScrollPane4);

        jScrollPane5.setViewportView(constructorList);

        jTabbedPane2.addTab("Constructors", jScrollPane5);

        jScrollPane3.setViewportView(instanceMethodList);

        jTabbedPane2.addTab("Instance Methods", jScrollPane3);

        jLabel1.setText("Methods");

        removeVariableButton.setText("-");

        addMetodButton.setText("+");

        removeMethodButton.setText("-");
        removeMethodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMethodButtonActionPerformed(evt);
            }
        });

        showInheritedMethodsButton.setText("SI");

        showInheritedVariablesButton.setText("SI");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(variableLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(showInheritedMethodsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(removeVariableButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addVariableButton))
                    .addComponent(showInheritedVariablesButton)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(removeMethodButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addMetodButton))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(variableLabel)
                    .addComponent(jLabel1)
                    .addComponent(showInheritedMethodsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showInheritedVariablesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addVariableButton)
                    .addComponent(removeVariableButton)
                    .addComponent(addMetodButton)
                    .addComponent(removeMethodButton)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeMethodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMethodButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_removeMethodButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMetodButton;
    private javax.swing.JButton addVariableButton;
    private javax.swing.JList classMethodList;
    private javax.swing.JList classVaraibleList;
    private javax.swing.JList constructorList;
    private javax.swing.JList instanceMethodList;
    private javax.swing.JList instanceVariableList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton removeMethodButton;
    private javax.swing.JButton removeVariableButton;
    private javax.swing.JButton showInheritedMethodsButton;
    private javax.swing.JButton showInheritedVariablesButton;
    private javax.swing.JLabel variableLabel;
    // End of variables declaration//GEN-END:variables
}