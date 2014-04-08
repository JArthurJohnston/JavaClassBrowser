/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Exceptions.NameAlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Models.PackageModel;
import Models.ProjectModel;
import UIModels.ClassBrowserShellModel;
import UIShells.ClassBrowserShell;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
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
public class MethodPanelTest extends BaseTest{
    private MethodPanel panel;
    private ClassBrowserShell shell;
    private MainApplication main;
    private ClassBrowserShellModel model;
    
    public MethodPanelTest() {
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
        try {
            main.addProject(new ProjectModel(main, "AProject"));
            PackageModel aPackage = main.getProjects().get(0).getPackageList().get(0);
            ClassModel aClass = aPackage.addClass(new ClassModel(aPackage, "AClass"));
            aClass.addMethod(new MethodModel(aClass,"aMethod"));
            aClass.addMethod(new MethodModel(aClass, "anotherMethod"));
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(MethodPanelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = main.openClassBrowser();
        shell = model.openShell();
        panel = (MethodPanel)this.getVariableFromClass(shell, "methodPresenter");
    }
    
    @After
    public void tearDown() {
        model.close();
        shell.dispose();
        panel = null;
        shell = null;
        model = null;
        main = null;
    }

    @Test
    public void testInitialValues() {
        JList instList = (JList)this.getVariableFromClass(panel, "instanceMethodList");
        assertEquals(model, this.getVariableFromClass(panel, "model"));
        assertEquals(2, instList.getModel().getSize());
    }
}
