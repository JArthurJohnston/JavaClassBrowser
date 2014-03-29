/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import Internal.BaseShellTest;
import MainBase.MainApplication;
import UIModels.PackageSelectionShellModel;
import java.util.ArrayList;
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
public class PackageSelectionShellTest extends BaseShellTest{
    private PackageSelectionShell shell;
    private PackageSelectionShellModel model;
    
    public PackageSelectionShellTest() {
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
        model = main.openPackageSelection();
        shell = model.getShell();
    }
    
    @After
    public void tearDown() {
        main = null;
        shell.signalClosedAndDispose();
        shell = null;
    }
    
    @Test
    public void testMainKnowsShell(){
        assertTrue(((ArrayList)this.getVariableFromClass(main, "openWindowShells")).contains(shell));
    }
    
    @Override
    public void testCloseAndDispose(){
        shell.signalClosedAndDispose();
        assertFalse(((ArrayList)this.getVariableFromClass(main, "openWindowShells")).contains(shell));
    }
    
    @Test
    public void testSelectionChangedSetsMain(){
        fail("write add package first");
    }
    
    @Test
    public void testAddPackageButton(){
        JButton addButton = (JButton)this.getVariableFromClass(shell, "addPackageButton");
        addButton.doClick();
        fail("need an AddPackageShell...");
    }
    
}
