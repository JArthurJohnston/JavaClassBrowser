/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Models.ProjectModel;
import Types.ClassType;
import UIModels.BrowserUIController;
import javax.swing.JTextArea;
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
public class ModelEditPanelTest extends BaseTest{
    private ModelEditPanel panel;
    private BrowserUIController model;
    
    public ModelEditPanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        MainApplication main = new MainApplication();
        try {
            ProjectModel aProject = 
                    main.setSelectedProejct(main.addProject(
                    new ProjectModel("a project")));
            ClassModel aClass = 
                    aProject.getDefaultPackage().addClass(new ClassModel("AClass"));
            MethodModel aMethod = aClass.addMethod(new MethodModel("aMethod", ClassType.INSTANCE));
            model = new BrowserUIController(main);
            model.setSelected(aMethod);
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        panel = new ModelEditPanel();
        panel.setModel(model);
    }
    
    @After
    public void tearDown() {
        panel = null;
        model = null;
    }
    
    @Test
    public void testInit(){
        assertEquals(model, this.getVariableFromClass(panel, "model"));
        JTextArea editSource = (JTextArea)this.getVariableFromClass(panel, "modelEditTextArea");
        assertTrue(this.compareStrings("private void aMethod(){\n" +
"\n" +
"}", editSource.getText()));
        assertTrue(editSource.isEditable());
    }
    
    @Test
    public void testSaveToModel(){
        fail();
    }
}
