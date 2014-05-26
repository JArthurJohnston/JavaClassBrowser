/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels;

import Exceptions.AlreadyExistsException;
import Exceptions.BaseException;
import Internal.BaseTest;
import Models.*;
import Types.ScopeType;
import UIModels.BrowserUIController;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.fail;

/**
 *
 * @author arthur
 */
public class BasePanelTest extends BaseTest{
    public BrowserUIController controller;
    public ClassModel testClass;
    public MethodModel testMethod;
    public VariableModel testVar;
    
    @Override
    public void setUp(){
        super.setUp();
        controller = new BrowserUIController(main);
        try {
            testClass = parentPackage.addClass(new ClassModel("TestClass"));
            testMethod = testClass.addMethod(new MethodModel("testMethod"));
                testMethod.setScope(ScopeType.PUBLIC);
                testMethod.setReturnType(ClassModel.getPrimitive("int"));
            testVar = testClass.addVariable(new VariableModel("testVar"));
                testVar.setObjectType(ClassModel.getPrimitive("char"));
                testVar.setScope(ScopeType.PROTECTED);
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Override
    public void tearDown(){
        controller = null;
    }
    
}
