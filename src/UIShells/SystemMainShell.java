/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import Models.*;
import UIModels.SystemMainShellModel;

/**
 * 
 * @author Arthur
 */
public class SystemMainShell extends javax.swing.JFrame {
    private SystemMainShellModel model;
    
    public SystemMainShell(){
        initComponents();
    }
    
    public SystemMainShell(SystemMainShellModel model){
        initComponents();
        this.model = model;
        this.fillLists();
        this.setTitle("JBrowser");
        this.setVisible(true);
    }
    
    private void fillLists(){
        projectsList.setModel(model.getProjectList());
        packageList.setModel(model.getPackageList());
        classList.setModel(model.getClassList());
        instanceMethodList.setModel(model.getMethodList());
    }
    
    private SystemMainShell setModel(SystemMainShellModel model){
        this.model = model;
        return this;
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
        projectsList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        packageList = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        classList = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        instanceMethodList = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        StaticMethodList = new javax.swing.JList();
        editorSelectorTabls = new javax.swing.JTabbedPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        methodSourceField = new javax.swing.JTextPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        classDefinitionField = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        classCommentField = new javax.swing.JTextPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        packageCommentField = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newProjectMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        projectsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        projectsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectsListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(projectsList);

        jLabel1.setText("Projects");

        packageList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                packageListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(packageList);

        jLabel2.setText("Packages");

        classList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                classListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(classList);

        jLabel3.setText("Classes");

        jLabel4.setText("Methods");

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        instanceMethodList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                instanceMethodListMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(instanceMethodList);

        jTabbedPane1.addTab("Instance", jScrollPane4);

        jScrollPane5.setViewportView(StaticMethodList);

        jTabbedPane1.addTab("Static", jScrollPane5);

        jScrollPane6.setViewportView(methodSourceField);

        editorSelectorTabls.addTab("Method Source", jScrollPane6);

        classDefinitionField.setColumns(20);
        classDefinitionField.setRows(5);
        jScrollPane7.setViewportView(classDefinitionField);

        editorSelectorTabls.addTab("Class Definition", jScrollPane7);

        jScrollPane8.setViewportView(classCommentField);

        editorSelectorTabls.addTab("Class Comment", jScrollPane8);

        packageCommentField.setColumns(20);
        packageCommentField.setRows(5);
        jScrollPane9.setViewportView(packageCommentField);

        editorSelectorTabls.addTab("Package Comment", jScrollPane9);

        descriptionArea.setColumns(20);
        descriptionArea.setRows(5);
        jScrollPane10.setViewportView(descriptionArea);

        editorSelectorTabls.addTab("Description", jScrollPane10);

        jMenu1.setText("File");

        newProjectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newProjectMenuItem.setText("New Project");
        newProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(newProjectMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(editorSelectorTabls)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editorSelectorTabls, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectMenuItemActionPerformed
        model.openNewProjectShell();
    }//GEN-LAST:event_newProjectMenuItemActionPerformed

    private void packageListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_packageListMouseClicked
        if(packageList.getModel().getSize() >= 1)
            model.setSelectedPackage((PackageModel)packageList.getSelectedValue());
    }//GEN-LAST:event_packageListMouseClicked

    private void projectsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectsListMouseClicked
        if(projectsList.getModel().getSize() >= 1)
            model.setSelectedProject((ProjectModel)projectsList.getSelectedValue());
    }//GEN-LAST:event_projectsListMouseClicked

    private void classListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classListMouseClicked
        if(classList.getModel().getSize() >= 1)
            model.setSelectedClass((ClassModel)classList.getSelectedValue());
    }//GEN-LAST:event_classListMouseClicked

    private void instanceMethodListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_instanceMethodListMouseClicked
        if(instanceMethodList.getModel().getSize() >= 1)
            model.setSelectedMethod((MethodModel)instanceMethodList.getSelectedValue());
    }//GEN-LAST:event_instanceMethodListMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SystemMainShell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SystemMainShell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SystemMainShell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SystemMainShell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SystemMainShell().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList StaticMethodList;
    private javax.swing.JTextPane classCommentField;
    private javax.swing.JTextArea classDefinitionField;
    private javax.swing.JList classList;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JTabbedPane editorSelectorTabls;
    private javax.swing.JList instanceMethodList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane methodSourceField;
    private javax.swing.JMenuItem newProjectMenuItem;
    private javax.swing.JTextArea packageCommentField;
    private javax.swing.JList packageList;
    private javax.swing.JList projectsList;
    // End of variables declaration//GEN-END:variables
}
