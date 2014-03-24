/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
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
public class BaseUIShellTest {
    
    private BaseUIShell shell;
    
    public BaseUIShellTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        shell = new BaseUIShell();
    }
    
    @After
    public void tearDown() {
        shell = null;
    }

    /**
     * Test of signalClosedAndDispose method, of class BaseUIShell.
     */
    @Test
    public void testSignalClosedAndDispose() {
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillListModel method, of class BaseUIShell.
     */
    @Test
    public void testFillListModel() {
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testAreYouSureWarning(){
        int answer = shell.yesOrNoMessage(null);
        assertEquals(1, answer);
    }
    
}
