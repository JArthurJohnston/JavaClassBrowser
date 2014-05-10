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
    ClassModel selectedClass;
    BaseModel selected;
    
    public void setSelectedClass(ClassModel aClass){
        selectedClass = aClass;
    }
    
    @Override
    public ClassModel getSelectedClass(){
        return selectedClass;
    }
    @Override
    public void setSelected(BaseModel aModel){
        this.selected = aModel;
    }
    
}
