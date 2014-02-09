/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import MainBase.MainApplication;
import UIModels.BaseUIModel;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arthur
 */
public class BaseUIModelTest {
    private BaseUIModel baseModel;
    private MainApplication main;
    
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
        baseModel = new BaseUIModel();
        main = new MainApplication();
    }
    
    @After
    public void tearDown() {
        baseModel = null;
        main = null;
    }

    @Test
    public void testFillListModel() {
        System.out.print("Test Fill List Model");
        DefaultListModel testListModel = new DefaultListModel();
        ProjectModel first, second, third, fourth;
        ArrayList testList = new ArrayList();
        testList.add(first = new ProjectModel(main,"test1"));
        testList.add(second = new ProjectModel(main, "test2"));
        testList.add(third = new ProjectModel(main, "test3"));
        testList.add(fourth = new ProjectModel(main, "test4"));
        baseModel.fillListModel(testList, testListModel);
        assertEquals(4, testListModel.size());
        assertTrue(testListModel.contains(first));
        assertTrue(testListModel.contains(second));
        assertTrue(testListModel.contains(third));
        assertTrue(testListModel.contains(fourth));
        System.out.println(" passed");
    }
}
