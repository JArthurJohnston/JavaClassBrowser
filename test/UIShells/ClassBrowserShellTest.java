/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseShellTest;
import MainBase.MainApplication;
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
            main.addProject(new ProjectModel(main, "a project"));
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
        model.addClass(null)
    }

    @Test
    public void testListSetsSelected() {
        JList classList = (JList)this.getVariableFromClass(shell, "classList");
    }
    
}
