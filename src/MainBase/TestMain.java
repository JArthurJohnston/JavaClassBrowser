/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainBase;

import Exceptions.NameAlreadyExistsException;
import Models.*;
import Types.ClassType;
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
            main.setSelectedProejct(aProject = main.addProject(new ProjectModel(main, "a project")));
            aProject.getDefaultPackage().addClass(
                    aClass = new ClassModel(aProject.getDefaultPackage(), "AClass"));
            aProject.getDefaultPackage().addClass(
                    new ClassModel(aProject.getDefaultPackage(), "AClass"));
            aProject.getDefaultPackage().addClass(
                    new ClassModel(aProject.getDefaultPackage(), "AClass"));
            aProject.getDefaultPackage().addClass(
                    new ClassModel(aProject.getDefaultPackage(), "AClass"));
            aClass.addMethod(new MethodModel(aClass, "aMethod", ClassType.CLASS));
            aClass.addMethod(new MethodModel(aClass, "anotherMethod", ClassType.CLASS));
            aClass.addMethod(new MethodModel(aClass, "yetAnotherMethod", ClassType.CLASS));
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        main.openSystemBrowser().getShell().setVisible(true);
    }
    
}
