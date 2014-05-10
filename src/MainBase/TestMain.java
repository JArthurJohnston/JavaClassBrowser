/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainBase;

import Exceptions.AlreadyExistsException;
import Models.*;
import Types.ClassType;
import UIModels.BrowserUIController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arthur
 */
public class TestMain {
    
    public static void main (String [] args){
        MainApplication main = new MainApplication();
        try {
            ProjectModel aProject;
            ClassModel aClass;
            ClassModel anotherClass;
            main.setSelectedProejct(aProject = main.addProject(new ProjectModel("a project")));
            aProject.getDefaultPackage().addClass(
                    aClass = new ClassModel("AClass"));
            aProject.getDefaultPackage().addClass(
                    new ClassModel("AndAnotherClass"));
            aProject.getDefaultPackage().addClass(
                    anotherClass = new ClassModel("AnotherClass"));
            aProject.getDefaultPackage().addClass(
                    new ClassModel("YetAnotherClass"));
            aClass.addMethod(new MethodModel("aMethod", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel("anotherMethod", ClassType.STATIC));
            aClass.addMethod(new MethodModel("yetAnotherMethod", ClassType.STATIC));
            aClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel("Object"), "aVar"));
            aClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel("Object"), "yetAnotherVar"));
            aClass.addVariable(new VariableModel(ClassType.STATIC, new ClassModel("Object"), "anotherVar"));
            
            anotherClass.addMethod(new MethodModel("someMethod", ClassType.INSTANCE));
        } catch (AlreadyExistsException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        BrowserUIController aModel = main.openSystemBrowser();
        aModel.getShell().setVisible(true);
    }
    
}
