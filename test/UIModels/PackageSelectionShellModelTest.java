/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels;

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
        model = main.openPackageSelection();
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        model = null;
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
    
}
