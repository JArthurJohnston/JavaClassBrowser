/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Internal.Mocks;

import Models.ClassModel;
import UIModels.BrowserUIController;

/**
 *
 * @author arthur
 */
public class MockBrowserController extends BrowserUIController {
    ClassModel selectedClass;
    
    public void setSelectedClass(ClassModel aClass){
        selectedClass = aClass;
    }
    
    @Override
    public ClassModel getSelectedClass(){
        return selectedClass;
    }
    
}
