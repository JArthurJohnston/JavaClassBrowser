/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels.TreePanels;

import Models.BaseModel;
import Models.ClassModel;
import Models.PackageModel;
import UIModels.BrowserUIController;
import UIPanels.TreePanels.Nodes.ClassNode;
import javax.swing.JTree;

/**
 *
 * @author Arthur
 */
public class ClassTreePanel extends BaseTreePanel {

    private ClassNode top;
    /**
     * Creates new form ClassTreePanel
     */
    public ClassTreePanel() {
        super();
        initComponents();
        this.setUpTree();
    }
    
    @Override
    protected ClassNode createNodeFromModel(BaseModel aClass){
        return new ClassNode((ClassModel)aClass, treeMap);
    }
    
    @Override
    public void setModel(BrowserUIController controller){
        super.setModel(controller);
        if(controller.getSelectedProject() == null)
            return;
        if(controller.getSelectedClass() != null){
            this.fillFromClass(controller.getSelectedClass());
            this.expandAll();
            return;
        }
        if(controller.getSelectedPackage() != null)
            this.fillFromPackage(controller.getSelectedPackage());
        this.expandAll();
    }
    
    private void fillFromPackage(PackageModel aPackage){
        this.clear();
        for(ClassModel c : aPackage.getTopLevelClasses())
            this.addModelToRoot(c);
        this.expandAll();
    }
    
    private void fillFromClass(ClassModel aClass){
        this.addModelToRoot(aClass);
    }
    
    @Override
    protected JTree tree(){
        return this.classTree;
    }
    
    @Override
    protected ClassNode getRootNode(){
        if(top == null)
            top = ClassNode.getDefaultRoot();
        return top;
    }
    
    public void addPackageClasses(PackageModel aPackage){
        for(ClassModel c : aPackage.getClassList())
            this.getRootNode().add(new ClassNode(c, treeMap));
    }
    
    @Override
    public void clear(){
        top = null;
        treeMap.clear();
    }
    
    @Override
    public ClassNode getSelectedNode(){
        return (ClassNode)super.getSelectedNode();
    }
    
    @Override
    public ClassModel getSelected(){
        if(this.getSelectedNode() != null)
            return this.getSelectedNode().getModel();
        return null;
    }
    
    @Override
    public void selectionChanged(BaseModel aModel){
        if(!aModel.isPackage())
            return;
        super.selectionChanged(aModel);
        if(aModel.isPackage())
            this.fillFromPackage((PackageModel)aModel);
    }
    
    @Override
    public void modelRemoved(BaseModel aModel){
        if(!aModel.isClass())
            return;
        if(controller.getSelectedPackage().contains(aModel))
            this.removeNode(this.getNodeFromModel(aModel));
    }
    
    @Override
    public void modelAdded(BaseModel aModel){
        if(!aModel.isClass())
            return;
        if(controller.getSelectedPackage().contains(aModel))
            if(((ClassModel)aModel).getParent() != null)
                this.getNodeFromModel(((ClassModel)aModel).getParent())
                    .addNode(new ClassNode((ClassModel)aModel, treeMap));
            else
                this.getRootNode()
                    .addNode(new ClassNode((ClassModel)aModel, treeMap));
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
        classTree = new javax.swing.JTree();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Classes");
        add(jLabel1, java.awt.BorderLayout.NORTH);
        jLabel1.getAccessibleContext().setAccessibleDescription("");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        classTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(classTree);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree classTree;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
