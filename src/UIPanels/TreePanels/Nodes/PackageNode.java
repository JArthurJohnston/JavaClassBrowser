/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Models.PackageModel;
import java.util.HashMap;

/**
 *
 * @author arthur
 */
public class PackageNode extends ModelNode{
    
    public PackageNode(PackageModel aPackage, HashMap aMap){
        super(aPackage, aMap);
        this.generateTreeFromPackage(aMap);
    }
    
    private void generateTreeFromPackage(HashMap aMap){
        for(PackageModel p : this.getModel().getTopLevelPackages()){
            this.add(new PackageNode(p, aMap));
        }
    }
    
    @Override
    public PackageNode addNode(ModelNode aNode){
        return (PackageNode)super.addNode(aNode);
    }
    
    @Override
    public PackageModel getModel(){
        return (PackageModel)super.getModel();
    }
    
}
