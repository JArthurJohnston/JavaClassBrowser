/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Internal.Mocks;

import Models.ClassModel;
import Models.PackageModel;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class MockPackageModel extends PackageModel{
    
    public MockPackageModel(String aName){
        classList = new LinkedList();
        packageList = new LinkedList();
        name = aName;
    }
    
    @Override
    public ClassModel addClass(ClassModel aClass){
        this.classList.add(aClass);
        return aClass;
    }
    
}
