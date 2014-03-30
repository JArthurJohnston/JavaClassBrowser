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
import UIModels.PackageSelectionShellModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
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
public class AddNewPackageShellTest extends BaseShellTest{
    private PackageSelectionShellModel model;
    private AddNewPackageShell shell;
    
    public AddNewPackageShellTest() {
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
        ProjectModel aProject = null;
        try {
            aProject = main.addProject(new ProjectModel(main, "a project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        main.setSelectedProejct(aProject);
        model = main.openPackageSelection();
        shell = new AddNewPackageShell(model);
    }
    
    @After
    public void tearDown() {
        main = null;
        model = null;
        shell.dispose();
        shell = null;
    }

    @Test
    public void testAddPackage() {
        JButton addButton = (JButton)this.getVariableFromClass(shell, "addPackageButton");
        addButton.doClick();
        assertEquals(2, model.selectedProject().getPackageList().size());
    }
    
    @Test
    public void testCancelButton(){
        JButton cancelButton = (JButton)this.getVariableFromClass(shell, "cancelButton");
        cancelButton.doClick();
        assertEquals(1, model.selectedProject().getPackageList().size());
        assertFalse(shell.isVisible());
    }
    
    @Test
    public void shellIsModal(){
        fail();
    }
    
}
