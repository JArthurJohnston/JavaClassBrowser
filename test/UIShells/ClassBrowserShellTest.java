/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseShellTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Models.ProjectModel;
import UIModels.ClassBrowserShellModel;
import java.util.ArrayList;
import java.util.LinkedList;
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
 * @author arthur
 */
public class ClassBrowserShellTest extends BaseShellTest{
    private ClassBrowserShellModel model;
    private ClassBrowserShell shell;
    
    public ClassBrowserShellTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        main = new MainApplication();
        try {
            main.setSelectedProejct(main.addProject(new ProjectModel(main, "a project")));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        model = main.openAddClassBrowser();
        shell = model.openShell();
    }
    
    @After
    public void tearDown() {
        model.closeShell();
        shell = null;
        model = null;
        main = null;
    }
    
    private void reInitShell(){
        model.closeShell();
        shell = model.openShell();
    }
    
    private ClassModel addMethodsToClass(ClassModel aClass){
        try {
            aClass.addMethod(new MethodModel(aClass, "aMethod"));
            aClass.addMethod(new MethodModel(aClass, "anotherMethod"));
            aClass.addMethod(new MethodModel(aClass, "yetAnotherMethod"));
            aClass.addMethod(new MethodModel(aClass, "evenBetterMethod"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(4, aClass.getInstanceMethods().size());
        return aClass;
    }
    
    @Test
    public void testAddClassesToModel(){
        ClassModel one = null;
        ClassModel two = null;
        ClassModel three = null;
        assertEquals(main.getSelectedProject(), (ProjectModel)this.getVariableFromClass(model, "selectedProject"));
        assertEquals(model.getSelected(), main.getSelectedProject().getPackageList().get(0));
        try {
            one =  model.addClass(new ClassModel(model.getSelected(), "NewClass"));
            two = model.addClass(new ClassModel(model.getSelected(), "AnotherClass"));
            three = model.addClass(new ClassModel(model.getSelected(), "YetAnotherClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(3, model.getSelected().getClassList().size());
        assertTrue(model.getSelected().getClassList().contains(one));
        assertTrue(model.getSelected().getClassList().contains(two));
        assertTrue(model.getSelected().getClassList().contains(three));
        assertEquals(3, model.getClasses().size());
        assertTrue(model.getClasses().contains(one));
        assertTrue(model.getClasses().contains(two));
        assertTrue(model.getClasses().contains(three));
        assertEquals(3, model.selectedProject().getClassList().size());
        assertTrue(model.selectedProject().getClassList().contains(one));
        assertTrue(model.selectedProject().getClassList().contains(two));
        assertTrue(model.selectedProject().getClassList().contains(three));
    }
    
    @Test
    public void testAddClassUpdatesClassList(){
        JList classList = (JList)this.getVariableFromClass(shell, "classList");
        assertEquals(0, classList.getModel().getSize());
        try {
            ClassModel aClass = model.addClass(new ClassModel(model.getSelected(), "aClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, classList.getModel().getSize());
    }
    
    @Test
    public void testAddClassWhenMethodSelected(){
        fail("NEEDS to be written!!!!");
        /*
        if a class isnt selected, just add it to the selected package
        */
    }
    
    @Test
    public void testListInitialize(){
        assertEquals("a project", main.getSelectedProject().name());
        JList classList = (JList)this.getVariableFromClass(shell, "classList");
        assertEquals(0, classList.getModel().getSize());
        this.testAddClassesToModel();
        this.reInitShell();
        classList = (JList)this.getVariableFromClass(shell, "classList");
        assertEquals(3, model.getSelected().getClassList().size());
        assertEquals(3, classList.getModel().getSize()); 
    }

    @Test
    public void testListSetsSelectedClass() {
        this.testAddClassesToModel();
        LinkedList classes = model.getSelected().getClassList();
        this.reInitShell();
        JList classList = (JList)this.getVariableFromClass(shell, "classList");
        assertEquals(3, classList.getModel().getSize());
        assertEquals(classes.getFirst(), classList.getSelectedValue());
        assertEquals("NewClass", ((ClassModel)classList.getSelectedValue()).name());
        classList.setSelectedIndex(1);
        assertEquals(classes.get(1), classList.getSelectedValue());
        assertEquals("AnotherClass", ((ClassModel)classList.getSelectedValue()).name());
        classList.setSelectedIndex(2);
        assertEquals(classes.get(2), classList.getSelectedValue());
        assertEquals("YetAnotherClass", ((ClassModel)classList.getSelectedValue()).name());
    }
    
    @Test
    public void testInitalMethodList(){
        JList methods = (JList)this.getVariableFromClass(shell, "methodList");
        assertEquals(0, methods.getModel().getSize());
        fail("to make testing a lot easier, hows about I write the addStuff logic first");
    }
    
    @Test
    @Override
    public void testCloseAndDispose(){
        model.close();
        assertFalse(((ArrayList)(this.getVariableFromClass(main, "openWindowModels"))).contains(model));
    }
}
