/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.*;
import Types.ClassType;
import UIModels.SystemBrowserShellModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arthur
 */
public class ClassFieldsPanelTest extends BaseTest{
    private SystemBrowserShellModel model;
    private ClassFieldsPanel panel;
    
    public ClassFieldsPanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        model = this.setUpModel();
        panel = new ClassFieldsPanel();
    }
    
    @After
    public void tearDown() {
        model = null;
        panel  = null;
    }
    
    private SystemBrowserShellModel setUpModel(){
        MainApplication main = new MainApplication();
        try {
            ProjectModel aProject = 
                    main.setSelectedProejct(main.addProject(new ProjectModel(main, "a project")));
            PackageModel aPackage = aProject.getDefaultPackage();
            ClassModel aClass = aPackage.addClass(new ClassModel(aPackage, "AClass"));
            aClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel("Object"), "aVar"));
            aClass.addVariable(new VariableModel(ClassType.CLASS, new ClassModel("Object"), "anotherVar"));
            aClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel("Object"), "yetAnotherVar"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        model = new SystemBrowserShellModel(main);
        return model;
    }

    @Test
    public void testInitialLists() {
        JList instVarList = (JList)this.getVariableFromClass(panel, "instVarList");
        JList statVarList = (JList)this.getVariableFromClass(panel, "instVarList");
        assertEquals(2, instVarList.getModel().getSize());
        assertEquals(1, statVarList.getModel().getSize());
    }
    
    @Test
    public void testImportsList(){
        fail();
    }
}
