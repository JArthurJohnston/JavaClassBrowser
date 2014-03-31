/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import Exceptions.NameAlreadyExistsException;
import MainBase.MainApplication;
import Models.ProjectModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * @author Arthur
 */
public class AddNewProjectShell extends BaseUIShell {
    private ProjectModel newProject;
    private MainApplication main;
    private static AddNewProjectShell instance = null;
    
    protected AddNewProjectShell(MainApplication main) {
        super();
        initComponents();
        this.main = main;
        this.newProject =  new ProjectModel(main, new String());
        this.newProject.setUserName(main.getUserName());
        this.authorNameField.setText(main.getUserName());
        this.projectNameField.getDocument().addDocumentListener(this.setUpDocListener());
        this.authorNameField.getDocument().addDocumentListener(this.setUpDocListener());
        this.setVisible(true);
    }
    
    public static AddNewProjectShell getInstance(MainApplication main){
        if(instance == null)
            instance = new AddNewProjectShell(main);
        return instance;
    }
    
    public static void closeInstance(){
        if(instance != null){
            instance.signalClosedAndDispose();
            instance = null;
        }
    }
    
    //this method should be pushed up
    //as should the corresponding removeShell() method
    @Override
    protected void signalClosedAndDispose(){
        main.removeShell(this);
        this.dispose();
    }
    
    private void updateProject(){
        newProject.setName(projectNameField.getText());
        newProject.setUserName(authorNameField.getText());
    }
    
    private boolean isProjectValid(){ 
        if(newProject.name() == null)
            return false;
        if(newProject.name().compareTo(new String()) == 0)
            return false;
        return main.okToAdd(newProject.name());
    }
    
    private DocumentListener setUpDocListener(){
        return new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateProject();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateProject();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        };
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
        projectNameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        authorNameField = new javax.swing.JTextField();
        projectInfoField = new javax.swing.JLabel();
        createProjectButton = new javax.swing.JButton();
        cancelProjectButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(" Project Name: ");

        projectNameField.setText("New Project");

        jLabel2.setText("Project Author:");

        projectInfoField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        createProjectButton.setText("Create");
        createProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createProjectButtonActionPerformed(evt);
            }
        });

        cancelProjectButton.setText("Cancel");
        cancelProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelProjectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(projectInfoField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(projectNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(authorNameField))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cancelProjectButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(createProjectButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(projectNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(authorNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectInfoField, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createProjectButton)
                    .addComponent(cancelProjectButton)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createProjectButtonActionPerformed
        this.updateProject();
        if(!this.isProjectValid())
            return;
        try {
            main.addProject(newProject);
            this.signalClosedAndDispose();
        } catch (NameAlreadyExistsException ex) {
            JOptionPane.showConfirmDialog(null, ex.getMessage(), "Project Already Exists", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_createProjectButtonActionPerformed

    private void cancelProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelProjectButtonActionPerformed
        this.signalClosedAndDispose();
    }//GEN-LAST:event_cancelProjectButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField authorNameField;
    private javax.swing.JButton cancelProjectButton;
    private javax.swing.JButton createProjectButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel projectInfoField;
    private javax.swing.JTextField projectNameField;
    // End of variables declaration//GEN-END:variables
}
