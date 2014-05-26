/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arthur
 */
public class ModelEditPanelTest extends BasePanelTest{
    private ModelEditPanel panel;
    
    public ModelEditPanelTest() {
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
        panel = new ModelEditPanel();
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        panel = null;
    }
    
    @Test
    public void testSetSelectedClass(){
        panel.setSelected(testClass);
    }
}
