/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Models.ClassModel;
import Models.PackageModel;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class PackageNode extends ModelNode{
    
    private LinkedList<ClassNode> classes;
    
    public PackageNode(PackageModel aPackage){
        super(aPackage);
        this.generatePackageTree();
    }
    
    private void generatePackageTree(){
        for(PackageModel p : this.getPackage().getPackageList())
            this.add(new PackageNode(p));
    }
    
    private void generateClassTreeBranches(){
        for(ClassModel c : this.getPackage().getClassList())
            classes.add(new ClassNode(c));
    }
    
    public LinkedList<ClassNode> getClassTrees(){
        if(classes == null)
            this.generateClassTreeBranches();
        return classes;
    }
    
    public void invalidateClasses(){
        classes = null;
    }
    
    private PackageModel getPackage(){
        return (PackageModel)super.getModel();
    }
    
}
