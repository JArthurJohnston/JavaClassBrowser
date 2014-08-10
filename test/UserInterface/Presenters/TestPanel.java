/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Presenters;

import Exceptions.AlreadyExistsException;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Models.PackageModel;
import Models.ProjectModel;
import Types.ClassType;
import UserInterface.Presenters.MockPresenters.MockPresenter;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author arthur
 */
public class TestPanel {

    private static MockPresenter parent = new MockPresenter();

    public static void main(String[] args) {
        parent.setSelectedClass(getTestClass());
        BasePresenter comp = new MethodListPresenter(parent,
                ClassType.INSTANCE);
        JFrame aFrame = new JFrame();
        aFrame.setSize(200, 400);
        aFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        aFrame.setLayout(new BorderLayout());
        aFrame.getContentPane().add(comp.getView(), BorderLayout.CENTER);
        aFrame.setVisible(true);
    }

    private static ClassModel getTestClass() {
        ClassModel aClass = null;
        try {
            new MainApplication()
                    .addProject(new ProjectModel(""))
                    .addPackage(new PackageModel("Some Package"))
                    .addClass(aClass = new ClassModel("SomeClass"));
            MethodModel aMethod = aClass.addMethod(new MethodModel("SomeMethod"));
            aMethod.setReturnType(ClassModel.getPrimitive("int"));
            aMethod.setType(ClassType.INSTANCE);
            aMethod = aClass.addMethod(new MethodModel("oneMethod"));
            aMethod.setReturnType(ClassModel.getPrimitive("int"));
            aMethod.setType(ClassType.STATIC);
            aMethod = aClass.addMethod(new MethodModel("twoMethod"));
            aMethod.setReturnType(ClassModel.getPrimitive("int"));
            aMethod.setType(ClassType.INSTANCE);
            aMethod = aClass.addMethod(new MethodModel("threeMethod"));
            aMethod.setReturnType(ClassModel.getPrimitive("int"));
            aMethod.setType(ClassType.STATIC);
        } catch (AlreadyExistsException ex) {
            ex.printStackTrace();
        }
        return aClass;
    }
}
