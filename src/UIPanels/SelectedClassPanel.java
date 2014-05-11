/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import MainBase.SortedList;
import Models.BaseModel;
import Types.ClassType;
import UIModels.BrowserUIController;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author Arthur
 */
public class SelectedClassPanel extends BasePanel {

    /**
     * Creates new form SelectedClassPanel
     */
    public SelectedClassPanel() {
        initComponents();
        this.setUpListener(classList);
    }
    
    @Override
    public void setModel(BrowserUIController aModel){
        super.setModel(aModel);
        this.fillClassList();
        for(BasePanel b : this.myPanels())
            b.setModel(aModel);
    }
    
    @Override
    protected SortedList<BasePanel> myPanels(){
        return super.myPanels()
                .addElm(classFieldsPresenter)
                .addElm(methodPresenter);
    }
    
    private void fillClassList(){
        this.fillListModel(controller.getSelectedProject().getClassList(), 
                (DefaultListModel)this.classList.getModel());
    }
    
    @Override
    protected SortedList<JList> myLists(){
        return super.myLists().addElm(classList);
    }
    
    public ClassType getSelectedVarType(){
        return this.classFieldsPresenter.getSelectedVarType();
    }
    public ClassType getSelectedMethodType(){
        return this.methodPresenter.getSelectedMethodType();
    }
    
    @Override
    public void modelAdded(BaseModel aModel){
        if(aModel.isClass())
            this.getListModel(classList).addElement(aModel);
        super.modelAdded(aModel);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        classFieldsPresenter = new UIPanels.ClassFieldsPanel();
        methodPresenter = new UIPanels.MethodPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        classList = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setLeftComponent(classFieldsPresenter);
        jSplitPane1.setRightComponent(methodPresenter);

        jSplitPane2.setRightComponent(jSplitPane1);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(classList);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel2.setText("Classes");
        jPanel1.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jSplitPane2.setLeftComponent(jPanel1);

        add(jSplitPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UIPanels.ClassFieldsPanel classFieldsPresenter;
    private javax.swing.JList classList;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private UIPanels.MethodPanel methodPresenter;
    // End of variables declaration//GEN-END:variables
}
