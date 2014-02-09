/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Models.ProjectModel;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arthur
 */
public class BaseUIModelTest {
    private BaseUIModel base;
    private ArrayList testModels;
    private DefaultListModel testList;
    
    public BaseUIModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        base = new BaseUIModel();
        testModels = new ArrayList();
        testList = new DefaultListModel();
    }
    
    @After
    public void tearDown() {
        base = null;
        testModels = null;
        testList = null;
    }

    /**
     * Test of fillListModel method, of class BaseUIModel.
     */
    @Test
    public void testFillListModel() {
        base.fillListModel(testModels, testList);
        assertEquals(0, testList.size());
        testModels.add(new ProjectModel());
        testModels.add(new ProjectModel());
        testModels.add(new ProjectModel());
        base.fillListModel(testModels, testList);
        assertEquals(3, testList.size());
    }
}
