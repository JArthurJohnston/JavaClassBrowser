/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class AllPackageModel extends PackageModel{
    
    
    
    public AllPackageModel() {
        this.name = "All";
    }
    
    @Override
    public LinkedList<ClassModel> getClassList(){
        return classList;
    }
    
}
