/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Exceptions.NameAlreadyExistsException;
import Types.ReturnType;
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
public class VariableModelTest {
    private ProjectModel parentProject;
    private PackageModel parentPackage;
    private ClassModel parentClass;
    private MethodModel parentMethod;
    private VariableModel var;
    
    public VariableModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.setUpParents();
        try {
            var = parentClass.addVariable(new VariableModel(ReturnType.INT, "aVar"));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @After
    public void tearDown() {
        parentProject = null;
        parentPackage = null;
        parentClass = null;
        parentMethod = null;
        var = null;
    }
    
    private void setUpParents(){
        parentProject = new ProjectModel();
        parentPackage = new PackageModel(parentProject, "a package");
        parentClass = new ClassModel(parentPackage, "AClass");
    }
    private MethodModel setUpParentMethod(){
        parentMethod = new MethodModel(parentClass, "aMethod");
        return parentMethod;
    }

    /**
     * Test of getType method, of class VariableModel.
     */
    @Test
    public void testGetType() {
        assertEquals(ReturnType.INT, var.getType());
    }

    /**
     * Test of getValue method, of class VariableModel.
     */
    @Test
    public void testGetValue() {
        assertEquals(null, var.getValue());
    }

    /**
     * Test of setType method, of class VariableModel.
     */
    @Test
    public void testSetType() {
        var.setType(ReturnType.CHAR);
        assertEquals(ReturnType.CHAR, var.getType());
    }

    /**
     * Test of setValue method, of class VariableModel.
     */
    @Test
    public void testSetValue() {
        var.setValue(5);
        assertEquals(5, var.getValue());
    }

    /**
     * Test of toSourceString method, of class VariableModel.
     */
    @Test
    public void testToSourceString() {
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetPath() {
        fail("The test case is a prototype.");
    }
    
}
