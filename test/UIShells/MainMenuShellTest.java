/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import Internal.BaseShellTest;
import MainBase.MainApplication;
import java.util.ArrayList;
import javax.swing.JToggleButton;
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
public class MainMenuShellTest extends BaseShellTest{
    private MainMenuShell shell;
    
    public MainMenuShellTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        shell = new MainMenuShell();
        main = (MainApplication)this.getVariableFromClass(shell, "main");
    }
    
    @After
    public void tearDown() {
        shell = null;
    }

    /**
     * Test of main method, of class MainMenuShell.
     */
    @Test
    public void testInitilizedFields() {
        assertEquals(MainApplication.class, main.getClass());
        assertTrue(((ArrayList)this.getVariableFromClass(main, "openWindowShells")).contains(shell));
    }
    
    @Test
    @Override
    public void testCloseAndDispose(){
        shell.signalClosedAndDispose();
        assertFalse(((ArrayList)this.getVariableFromClass(main, "openWindowShells")).contains(shell));
    }
    
    @Test
    public void testProjectsButton(){
        JToggleButton projectsButton = (JToggleButton)this.getVariableFromClass(shell, "projectsToggleButton");
        projectsButton.doClick();
        assertListHasClass(((ArrayList)
                this.getVariableFromClass(main, "openWindowModels")), ProjectSelectionShell.class);
        assertTrue(projectsButton.isSelected());
        projectsButton.doClick();
        assertFalse(projectsButton.isSelected());
        denyListHasClass(((ArrayList)
                this.getVariableFromClass(main, "openWindowShells")), ProjectSelectionShell.class);
    }
    
}
