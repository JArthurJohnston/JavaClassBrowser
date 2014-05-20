/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Models.PackageModel;

/**
 *
 * @author arthur
 */
public class PackageNode extends ModelNode{
    
    public PackageNode(PackageModel aPackage){
        super(aPackage);
        this.generatePackageTree();
    }
    
    private void generatePackageTree(){
        for(PackageModel p : this.getPackage().getPackageList())
            this.add(new PackageNode(p));
    }
    
    private PackageModel getPackage(){
        return (PackageModel)super.getModel();
    }
    
}
