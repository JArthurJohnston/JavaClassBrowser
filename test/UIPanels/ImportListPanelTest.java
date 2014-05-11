/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arthur
 */
public class ImportListPanelTest {
    private ImportListPanel panel;
    
    public ImportListPanelTest() {
    }
    
    @Before
    public void setUp() {
        panel = new ImportListPanel();
    }
    
    @After
    public void tearDown() {
        panel = null;
    }

    /**
     * Test of table method, of class ImportListPanel.
     */
    @Test
    public void testTable() {
        fail();
        /*
        need to make a CellModel class that 
        prints the models import label instead of name.
        */
    }
    
}
