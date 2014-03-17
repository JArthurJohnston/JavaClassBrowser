/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import Internal.BaseTest;
import MainBase.MainApplication;
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
public class MainMenuShellTest extends BaseTest{
    private MainMenuShell shell;
    private MainApplication main;
    
    
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
    }

    /**
     * Test of main method, of class MainMenuShell.
     */
    @Test
    public void testInitilizedFields() {
        assertEquals(MainApplication.class, main.getClass());
    }
    
}
