/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels;

import Exceptions.NameAlreadyExistsException;
import Exceptions.PackageDoesNotExistException;
import Models.PackageModel;
import Models.ProjectModel;
import java.util.ArrayList;
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
public class PackageSelectionShellModelTest extends BaseUIModelTest{
    
    private PackageSelectionShellModel model;
    
    public PackageSelectionShellModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        try {
            ProjectModel aProject = null;
            main.addProject(aProject = new ProjectModel(main, "A Project"));
            main.setSelectedProejct(aProject);
            model = main.openPackageSelection();
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        model = null;
    }
    
    private void setUpMainWithPackages(){
        try {
            model.close();
            main.getSelectedProject().addPackage(new PackageModel(main.getSelectedProject(), "A Project"));
            main.getSelectedProject().addPackage(new PackageModel(main.getSelectedProject(), "Another Project"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        model = main.openPackageSelection();
    }

    @Test
    public void testMainKnowsModel() {
        assertTrue(((ArrayList)this.getVariableFromClass(main, "openWindowModels")).contains(model));
    }
    
    @Test
    public void testClose(){
        model.close();
        assertFalse(((ArrayList)this.getVariableFromClass(main, "openWindowModels")).contains(model));
    }
    
    @Test
    public void testSelectedPackage(){
        assertTrue(this.compareStrings("default package", model.getSelectedPackage().name()));
        model.setSelectedPackage(new PackageModel(main.getSelectedProject(), "another package"));
    }
    
    @Test
    public void testInitialPackageList(){
        //remember, each project starts out with a default package.
        assertEquals(1, model.getPackageList().size());
        this.setUpMainWithPackages();
        assertEquals(3, model.getPackageList().size());
    }
    
    @Test
    public void testAddPackage(){
        PackageModel aPackage = null;
        try {
            model.addPackage(aPackage = new PackageModel(main.getSelectedProject(), "another package"));
            assertEquals(PackageModel.class, aPackage.getClass());
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(model.getPackageList().contains(aPackage));
    }
    
    @Test
    public void testRemovePackage(){
        PackageModel aPackage = null;
        try {
            model.addPackage(aPackage = new PackageModel(main.getSelectedProject(), "another package"));
            assertEquals(PackageModel.class, aPackage.getClass());
            assertTrue(model.getPackageList().contains(aPackage));
            
            model.removePackage(aPackage);
        } catch (NameAlreadyExistsException | PackageDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertFalse(model.getPackageList().contains(aPackage));
    }
    
}
