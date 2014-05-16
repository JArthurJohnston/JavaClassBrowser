/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Internal.Mocks;

import Models.*;
import UIModels.BrowserUIController;

/**
 *
 * @author arthur
 */
public class MockBrowserController extends BrowserUIController {
    BaseModel selected;
    
    @Override
    public ClassModel getSelectedClass(){
        if(selected.isClass())
            return (ClassModel)selected;
        return null;
    }
    
    @Override
    public void setSelected(BaseModel aModel){
        this.selected = aModel;
    }
    
    @Override
    public BaseModel getSelected(){
        return selected;
    }
    
    @Override
    public PackageModel getSelectedPackage(){
        if(selected.isPackage())
            return (PackageModel)selected;
        return null;
    }
    
}
