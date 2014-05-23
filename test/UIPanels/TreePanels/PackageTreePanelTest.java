/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels;

import Models.ProjectModel;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class PackageTreePanelTest {
    private ProjectModel aProject;
    private PackageTreePanel panel;
    
    public PackageTreePanelTest() {
    }
    
    @Before
    public void setUp() {
        panel = new PackageTreePanel();
    }
    
    @After
    public void tearDown() {
        panel = null;
    }

    /**
     * Test of tree method, of class PackageTreePanel.
     */
    @Test
    public void testTree() {
    }

    /**
     * Test of getRootNode method, of class PackageTreePanel.
     */
    @Test
    public void testGetRootNode() {
    }
    
}
