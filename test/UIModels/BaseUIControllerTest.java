/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Internal.BaseTest;
import MainBase.MainApplication;
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
public class BaseUIControllerTest extends BaseTest{
    protected MainApplication main;
    protected BaseUIController model;
    private ArrayList testModels;
    private DefaultListModel testList;
    
    public BaseUIControllerTest() {
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
        model = new BaseUIController(main);
    }
    
    @After
    public void tearDown() {
        model = null;
        main = null;
    }

    /**
     * Test of fillListModel method, of class BaseUIController.
     */
    @Test
    public void testFillListModel() {
        testModels = new ArrayList();
        testList = new DefaultListModel();
        model.fillListModel(testModels, testList);
        assertEquals(0, testList.size());
        testModels.add(new ProjectModel());
        testModels.add(new ProjectModel());
        testModels.add(new ProjectModel());
        model.fillListModel(testModels, testList);
        assertEquals(3, testList.size());
    }
}
