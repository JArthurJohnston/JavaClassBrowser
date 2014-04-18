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
import UIModels.BrowserUIModel;
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
    private BrowserUIModel model;
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
        panel.setUpWithModel(model);
    }
    
    @After
    public void tearDown() {
        model = null;
        panel  = null;
    }
    
    private BrowserUIModel setUpModel(){
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
        model = main.openSystemBrowser();
        return model;
    }
    
    @Test
    public void testSelectionSetsModel(){
        JList instVarList = (JList)this.getVariableFromClass(panel, "instanceVarList");
        JList statVarList = (JList)this.getVariableFromClass(panel, "staticVarList");
        instVarList.setSelectedIndex(0);
        assertEquals(VariableModel.class, model.getSelected().getClass());
        assertEquals("aVar", model.getSelected().name());
        statVarList.setSelectedIndex(0);
        assertEquals(VariableModel.class, model.getSelected().getClass());
        assertEquals("anotherVar", model.getSelected().name());
    }
    
    @Test
    public void testSelectionClearsLists(){
        JList instVarList = (JList)this.getVariableFromClass(panel, "instanceVarList");
        JList statVarList = (JList)this.getVariableFromClass(panel, "staticVarList");
        instVarList.setSelectedIndex(0);
        statVarList.setSelectedIndex(0);
        assertEquals(-1, instVarList.getSelectedIndex());
    }

    @Test
    public void testInitialPanel() {
        JList instVarList = (JList)this.getVariableFromClass(panel, "instanceVarList");
        JList statVarList = (JList)this.getVariableFromClass(panel, "staticVarList");
        assertEquals(2, instVarList.getModel().getSize());
        assertEquals(1, statVarList.getModel().getSize());
        assertEquals(model, this.getVariableFromClass(panel, "model"));
    }
    
    @Test
    public void testImportsList(){
        fail();
    }
}
