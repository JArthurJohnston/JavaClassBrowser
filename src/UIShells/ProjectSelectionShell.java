/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import Exceptions.DoesNotExistException;
import Models.ProjectModel;
import UIModels.ProjectSelectionModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author arthur
 */
public class ProjectSelectionShell extends BaseUIShell {
    private ProjectSelectionModel model;

    /**
     * Creates new form ProjectSelectionShell
     * @param model
     */
    public ProjectSelectionShell(ProjectSelectionModel model) {
        initComponents();
        this.model = model;
        this.projectList.setModel(model.getListModel());
        this.projectList.setSelectedValue(model.getSelectedProject(), true);
        this.projectList.getSelectionModel().addListSelectionListener(this.setUpProjectListener());
        this.updateFields();
    }
    
    private ListSelectionListener setUpProjectListener(){
        return new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting())
                    model.setSelectedProject((ProjectModel)projectList.getSelectedValue());
            }
        };
    }
    
    private void updateFields(){
        this.removeProjectButton.setEnabled(model.getSelectedProject() != null);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeShellButton = new javax.swing.JButton();
        removeProjectButton = new javax.swing.JButton();
        createProjectButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        projectList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        projectInfoTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        closeShellButton.setText("^");
        closeShellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeShellButtonActionPerformed(evt);
            }
        });

        removeProjectButton.setText("Remove");
        removeProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeProjectButtonActionPerformed(evt);
            }
        });

        createProjectButton.setText("Add");

        jLabel1.setText("Current Projects");

        jScrollPane1.setViewportView(projectList);

        projectInfoTextArea.setEditable(false);
        projectInfoTextArea.setColumns(20);
        projectInfoTextArea.setRows(5);
        jScrollPane2.setViewportView(projectInfoTextArea);

        jLabel2.setText("Project Information");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(closeShellButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(removeProjectButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(createProjectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(removeProjectButton)
                            .addComponent(createProjectButton)))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeShellButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void removeProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeProjectButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_removeProjectButtonActionPerformed

    private void closeShellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeShellButtonActionPerformed
        this.signalClosedAndDispose();
    }//GEN-LAST:event_closeShellButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeShellButton;
    private javax.swing.JButton createProjectButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea projectInfoTextArea;
    private javax.swing.JList projectList;
    private javax.swing.JButton removeProjectButton;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void signalClosedAndDispose(){
        model.close();
    }
    
    private void removeProject(){
        if(this.projectList.getSelectedValue() != null)
            try {
                model.removeProject((ProjectModel)projectList.getSelectedValue());
        } catch (DoesNotExistException ex) {
            JOptionPane.showConfirmDialog(null, ex, "Error", JOptionPane.OK_OPTION);
        }
    }

}
