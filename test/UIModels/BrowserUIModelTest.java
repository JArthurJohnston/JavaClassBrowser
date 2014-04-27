/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIShells.Dialogs.AddVariableDialogue;
import UIShells.SystemBrowserShell;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arthur
 */
public class BrowserUIModelTest extends BaseTest{
    private MainApplication main;
    private BrowserUIModel model;
    
    public BrowserUIModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        main =  new MainApplication();
        try {
            main.setSelectedProejct(main.addProject(new ProjectModel(main, "a project")));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        model = main.openSystemBrowser();
    }
    
    @After
    public void tearDown() {
        main = null;
        model = null;
        
    }
    
    @Test
    public void testConstructors(){
        model = new BrowserUIModel(new MainApplication());
        assertEquals(BrowserUIModel.class, model.getClass());
    }
    
    @Test
    public void testInit(){
        assertEquals(main, model.main);
        assertEquals(main.getSelectedProject(), 
                (ProjectModel)this.getVariableFromClass(model, "selectedProject"));
    }

    @Test
    public void testClose() {
        SystemBrowserShell aShell = model.getShell();
        model.close();
        assertFalse(aShell.isVisible());
        assertFalse(((LinkedList)this.getVariableFromClass(main, "openWindowModels")).contains(model));
    }
    
    @Test
    public void testGetShell(){
        SystemBrowserShell aShell = model.getShell();
        assertEquals(SystemBrowserShell.class, model.getShell().getClass());
        assertEquals(aShell, model.getShell());
        assertTrue(model.getShell().isVisible());
    }
    
    @Test
    public void testSelectedProject(){
        assertEquals("a project", ((ProjectModel)this.getVariableFromClass(model, "selectedProject")).name());
    }
    
    @Test
    public void testPackageList(){
        assertEquals(2, model.getPackages().size());
    }
    
    @Test
    public void testAddPackage(){
        try {
            model.addPackage(new PackageModel(main.getSelectedProject(), "a package"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(3, model.getPackages().size());
    }
    
    @Test
    public void testSelectedClass(){
        ClassModel aClass = null;
        assertEquals(null, model.getSelectedClass());
        try {
            PackageModel aPackage = 
                    main.getSelectedProject().addPackage(
                    new PackageModel(main.getSelectedProject(), "a package"));
            aClass = aPackage.addClass(new ClassModel(aPackage, "AClass"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(aClass, model.getSelectedClass());
    }
    
    @Test
    public void testOpenAddVariable(){
        model.getShell();
        AddVariableDialogue dialogue = model.openAddVariable();
        assertEquals(AddVariableDialogue.class, dialogue.getClass());
        //assertTrue(dialogue.isVisible());
        dialogue.dispose();
        model.close();
    }
}
