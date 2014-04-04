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
import Models.ProjectModel;
import UIModels.ClassBrowserShellModel;
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
    }
    
    @Test
    public void testListInitialize(){
        assertEquals("a project", main.getSelectedProject().name());
        JList classList = (JList)this.getVariableFromClass(shell, "classList");
        assertEquals(0, classList.getModel().getSize());
        this.testAddClassesToModel();
        this.reInitShell();
        assertEquals(3, classList.getModel().getSize());
    }

    @Test
    public void testListSetsSelected() {
        this.testAddClassesToModel();
        JList classList = (JList)this.getVariableFromClass(shell, "classList");
        fail();
    }
}
