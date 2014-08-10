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
import Types.ScopeType;
import UserInterface.Presenters.MockPresenters.MockPresenter;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author arthur
 */
public class TestPanel {

    public static void main(String[] args) {
        JFrame aFrame = new JFrame();
        aFrame.setSize(300, 200);
        aFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        aFrame.setLayout(new BorderLayout());
        aFrame.getContentPane().add(getComponent(), BorderLayout.CENTER);
        aFrame.setVisible(true);
    }

    private static JComponent getComponent() {
        MockPresenter parent = new MockPresenter();
        parent.setSelectedClass(getTestClass());
        MethodListPresenter presenter = new MethodListPresenter(parent,
                ClassType.INSTANCE);
        System.out.println("Rows: " + presenter.model.getRowCount());
        return presenter.getView();
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
            aMethod.setScope(ScopeType.PRIVATE);
            aMethod = aClass.addMethod(new MethodModel("oneMethod"));
            aMethod.setReturnType(ClassModel.getPrimitive("int"));
            aMethod.setType(ClassType.STATIC);
            aMethod.setScope(ScopeType.PUBLIC);
            aMethod = aClass.addMethod(new MethodModel("twoMethod"));
            aMethod.setReturnType(ClassModel.getPrimitive("int"));
            aMethod.setType(ClassType.INSTANCE);
            aMethod.setScope(ScopeType.PUBLIC);
            aMethod = aClass.addMethod(new MethodModel("threeMethod"));
            aMethod.setReturnType(ClassModel.getPrimitive("int"));
            aMethod.setType(ClassType.STATIC);
            aMethod.setScope(ScopeType.PRIVATE);
            aMethod = aClass.addMethod(new MethodModel("aMethod"));
            aMethod.setReturnType(ClassModel.getPrimitive("int"));
            aMethod.setType(ClassType.INSTANCE);
            aMethod.setScope(ScopeType.PUBLIC);
            aMethod = aClass.addMethod(new MethodModel("anotherMethod"));
            aMethod.setReturnType(ClassModel.getPrimitive("int"));
            aMethod.setType(ClassType.INSTANCE);
            aMethod.setScope(ScopeType.PUBLIC);
            aMethod = aClass.addMethod(new MethodModel("yetAnotherMethod"));
            aMethod.setReturnType(ClassModel.getPrimitive("int"));
            aMethod.setType(ClassType.INSTANCE);
            aMethod.setScope(ScopeType.PUBLIC);
        } catch (AlreadyExistsException ex) {
            ex.printStackTrace();
        }
        return aClass;
    }
}
