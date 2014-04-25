/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainBase;

import Exceptions.NameAlreadyExistsException;
import Models.*;
import Types.ClassType;
import UIModels.BrowserUIModel;
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
            main.setSelectedProejct(aProject = main.addProject(new ProjectModel(main, "a project")));
            aProject.getDefaultPackage().addClass(
                    aClass = new ClassModel(aProject.getDefaultPackage(), "AClass"));
            aProject.getDefaultPackage().addClass(
                    new ClassModel(aProject.getDefaultPackage(), "AndAnotherClass"));
            aProject.getDefaultPackage().addClass(
                    anotherClass = new ClassModel(aProject.getDefaultPackage(), "AnotherClass"));
            aProject.getDefaultPackage().addClass(
                    new ClassModel(aProject.getDefaultPackage(), "YetAnotherClass"));
            aClass.addMethod(new MethodModel(aClass, "aMethod", ClassType.INSTANCE));
            aClass.addMethod(new MethodModel(aClass, "anotherMethod", ClassType.STATIC));
            aClass.addMethod(new MethodModel(aClass, "yetAnotherMethod", ClassType.STATIC));
            aClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel("Object"), "aVar"));
            aClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel("Object"), "yetAnotherVar"));
            aClass.addVariable(new VariableModel(ClassType.STATIC, new ClassModel("Object"), "anotherVar"));
            
            anotherClass.addMethod(new MethodModel(aClass, "someMethod", ClassType.INSTANCE));
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        BrowserUIModel aModel = main.openSystemBrowser();
        aModel.getShell().setVisible(true);
        System.out.println(aModel.getSelectedClass().toString());
        System.out.println(aModel.getSelectedClass().getInstanceMethods().size());
        System.out.println(aModel.getSelectedClass().getStaticMethods().size());
    }
    
}
