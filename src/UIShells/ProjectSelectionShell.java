/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import Models.ProjectModel;
import UIModels.ProjectSelectionShellModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author arthur
 */
public class ProjectSelectionShell extends BaseUIShell {
    private ProjectSelectionShellModel model;
    /**
     * Creates new form ProjectSelectionShell
     * @param model
     */
    public ProjectSelectionShell(ProjectSelectionShellModel model) {
        super();
        initComponents();
        this.model = model;
        this.setUpSelectionList();
        this.setVisible(true);
    }
    
    private void setUpSelectionList(){
        projectSelectionList.setModel(model.getListModel());
        projectSelectionList.getSelectionModel().addListSelectionListener(this.setUpProjectListener());
    }
    
    
    private ListSelectionListener setUpProjectListener(){
        return new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
            }
        };
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        projectSelectionList = new javax.swing.JList();
        addProjectButton = new javax.swing.JButton();
        removeProjectButton = new javax.swing.JButton();
        closeProjectListButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jScrollPane1.setViewportView(projectSelectionList);

        addProjectButton.setText("+");
        addProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProjectButtonActionPerformed(evt);
            }
        });

        removeProjectButton.setText("-");
        removeProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeProjectButtonActionPerformed(evt);
            }
        });

        closeProjectListButton.setText("^");
        closeProjectListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeProjectListButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(closeProjectListButton, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(removeProjectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addProjectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeProjectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addProjectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeProjectListButton, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeProjectListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeProjectListButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_closeProjectListButtonActionPerformed

    private void addProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProjectButtonActionPerformed
        
    }//GEN-LAST:event_addProjectButtonActionPerformed

    private void removeProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeProjectButtonActionPerformed

    }//GEN-LAST:event_removeProjectButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProjectButton;
    private javax.swing.JButton closeProjectListButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList projectSelectionList;
    private javax.swing.JButton removeProjectButton;
    // End of variables declaration//GEN-END:variables

    
}
