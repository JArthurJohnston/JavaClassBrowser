/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import Types.ClassType;
import Types.ScopeType;
import UIShells.ClassBrowserShell;
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
public class ClassBrowserShellModelTest extends BaseTest{
    private ClassBrowserShellModel model;
    private ProjectModel aProject;
    
    public ClassBrowserShellModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        aProject = new ProjectModel();
        try {
            PackageModel aPackage = aProject.addPackage(new PackageModel(aProject, "A Package"));
            ClassModel aClass = aPackage.addClass(new ClassModel (aPackage, "First Class"));
            aPackage.addClass(new ClassModel(aPackage, "Second Class"));
            aPackage.addClass(new ClassModel(aPackage, "Third Class"));
            this.addMethodToClass("firstMethod", aClass);
            this.addMethodToClass("secondMethod", aClass);
            this.addMethodToClass("thirdMethod", aClass);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        model = new ClassBrowserShellModel(aProject);
    }
    
    @After
    public void tearDown() {
        aProject = null;
        model = null;
    }

    @Test
    public void testConstructor(){
        ClassBrowserShell shell = (ClassBrowserShell)this.getVariableFromClass(model, "shell");
        assertEquals(ClassBrowserShell.class, shell.getClass());
    }
}
