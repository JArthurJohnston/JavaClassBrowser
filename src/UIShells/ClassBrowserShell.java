/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import Models.ClassModel;
import Models.PackageModel;
import UIModels.ClassBrowserShellModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Arthur
 */
public class ClassBrowserShell extends javax.swing.JFrame {
    ClassBrowserShellModel model;

    /**
     * Creates new form ClassBrowserShell
     */
    public ClassBrowserShell(ClassBrowserShellModel model) {
        initComponents();
        this.model = model;
        this.setUpInitialListsModels();
        this.initializePanels();
    }
    
    private void initializePanels(){
        this.methodPresenter.initializeWithModel(model);
    }
    
    private void setUpInitialListsModels(){
        this.setUpJList(classList);
        if(model.selectedProject() != null)
            model.fillListModel(model.selectedProject().getClassList(), 
                    (DefaultListModel)classList.getModel());
        if(classList.getModel().getSize() != 0)
            classList.setSelectedIndex(0);
        this.setUpJList(staticVarList);
        this.setUpJList(instanceVarList);
        this.fillListsFromClass((ClassModel)classList.getSelectedValue());
    }
    
    private void fillListsFromClass(ClassModel selectedClass){
        if(selectedClass != null){
            model.fillListModel(
                    selectedClass.getInstanceVars(), 
                    (DefaultListModel)instanceVarList.getModel());
            model.fillListModel(
                    selectedClass.getStaticVars(), 
                    (DefaultListModel)staticVarList.getModel());
        }
    }
    
    private void setUpJList(JList aList){
        aList.setModel(new DefaultListModel());
        aList.getSelectionModel().addListSelectionListener(this.setUpListener(aList));
    }
    
    private ListSelectionListener setUpListener(final JList aList){
        return new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    model.setSelected((PackageModel)aList.getSelectedValue());
                }
            }
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

        classListRightClickMenu = new javax.swing.JPopupMenu();
        addClassRightClickMenu = new javax.swing.JMenuItem();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        classList = new javax.swing.JList();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        instanceVarList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        staticVarList = new javax.swing.JList();
        jScrollPane7 = new javax.swing.JScrollPane();
        importsList = new javax.swing.JList();
        methodPresenter = new UIPanels.MethodPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        addClassMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        addClassRightClickMenu.setText("jMenuItem1");
        classListRightClickMenu.add(addClassRightClickMenu);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel4.setText("Classes");

        jScrollPane6.setViewportView(classList);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
        );

        jSplitPane3.setLeftComponent(jPanel4);

        jLabel1.setText("Variables");

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jScrollPane1.setViewportView(instanceVarList);

        jTabbedPane1.addTab("Instance Variables", jScrollPane1);

        jScrollPane2.setViewportView(staticVarList);

        jTabbedPane1.addTab("Static Variables", jScrollPane2);

        jScrollPane7.setViewportView(importsList);

        jTabbedPane1.addTab("Imports", jScrollPane7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 157, Short.MAX_VALUE))
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        javax.swing.GroupLayout methodPresenterLayout = new javax.swing.GroupLayout(methodPresenter);
        methodPresenter.setLayout(methodPresenterLayout);
        methodPresenterLayout.setHorizontalGroup(
            methodPresenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 187, Short.MAX_VALUE)
        );
        methodPresenterLayout.setVerticalGroup(
            methodPresenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(methodPresenter);

        jSplitPane3.setRightComponent(jSplitPane1);

        jSplitPane2.setLeftComponent(jSplitPane3);

        jScrollPane5.setViewportView(jEditorPane1);

        jSplitPane2.setRightComponent(jScrollPane5);

        getContentPane().add(jSplitPane2, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        addClassMenuItem.setText("New Class");
        jMenu1.add(addClassMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addClassMenuItem;
    private javax.swing.JMenuItem addClassRightClickMenu;
    private javax.swing.JList classList;
    private javax.swing.JPopupMenu classListRightClickMenu;
    private javax.swing.JList importsList;
    private javax.swing.JList instanceVarList;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private UIPanels.MethodPanel methodPresenter;
    private javax.swing.JList staticVarList;
    // End of variables declaration//GEN-END:variables
}
