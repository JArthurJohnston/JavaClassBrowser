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
import java.util.ArrayList;
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
        shell = (ClassBrowserShell)this.getVariableFromClass(model, "shell");
    }
    
    @After
    public void tearDown() {
        shell.dispose();
        shell = null;
        model = null;
        main = null;
    }
    
    private void setModelWithClasses(){
        ClassModel one = null;
        ClassModel two = null;
        ClassModel three = null;
        try {
            one =  model.addClass(new ClassModel(model.getSelected(), "NewClass"));
            two = model.addClass(new ClassModel(model.getSelected(), "AnotherClass"));
            three = model.addClass(new ClassModel(model.getSelected(), "YetAnotherClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(model.getClasses().contains(one));
        assertTrue(model.getClasses().contains(two));
        assertTrue(model.getClasses().contains(three));
    }
    
    @Test
    public void testListInitialize(){
        JList classList = (JList)this.getVariableFromClass(shell, "classList");
        assertEquals(0, classList.getModel().getSize());
        this.setModelWithClasses();
        shell.dispose();
        shell = (ClassBrowserShell)this.getVariableFromClass(model, "shell");
        assertEquals(3, classList.getModel().getSize());
    }

    @Test
    public void testListSetsSelected() {
        this.setModelWithClasses();
        JList classList = (JList)this.getVariableFromClass(shell, "classList");
        fail();
    }
    
    
    
}
