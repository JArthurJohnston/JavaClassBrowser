/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.PackageModel;
import Models.ProjectModel;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Arthur
 */
public class BrowserUIControllerTest extends BaseTest {

    private BrowserUIController controller;

    public BrowserUIControllerTest() {
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        controller = new BrowserUIController(parentProject);
    }

    @After
    @Override
    public void tearDown() {
        controller = null;
        super.tearDown();
    }

    private ProjectModel getProject() {
        ProjectModel aProject = new ProjectModel("a project");
        aProject.setMain(main);
        try {
            ClassModel aClass = aProject.addClass(new ClassModel("AClass1"));
            aClass.addClass(new ClassModel("ASubClass1"));
            aClass.addClass(new ClassModel("ASubClass2"));
            aClass.addClass(new ClassModel("ASubClass3"));
            aClass.addClass(new ClassModel("ASubClass4"));
            aProject.addClass(new ClassModel("AClass2"));
            aProject.addClass(new ClassModel("AClass3"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return aProject;
    }

    @Test
    public void testOpenFromMain() throws Exception {
        main = new MainApplication();
        main.addProject(new ProjectModel("some project"));
        controller = new BrowserUIController(main);
        assertEquals(BrowserUIController.class, controller.getClass());
    }

    @Test
    public void testInit() {
        assertSame(main, controller.main);
        assertSame(main.getSelectedProject(),
                this.getVariableFromClass(controller, "selectedProject"));
    }

    @Test
    public void testPackageList() {
        assertEquals(2, controller.getPackages().size());
    }

    @Test
    public void testAddPackage() throws Exception {
        controller.addPackage(new PackageModel("a package"));
        assertEquals(3, controller.getPackages().size());
    }

    @Test
    public void testAddClass() {
        try {
            controller.addClass(new ClassModel("AClass"));
            fail("exception not thrown");
        } catch (Exception ex) {
            this.compareStrings("", ex.getMessage());
        }
    }

    @Test
    public void testSelectedClass() {
        ClassModel aClass = null;
        assertEquals(null, controller.getSelectedClass());
        try {
            PackageModel aPackage
                    = main.getSelectedProject().addPackage(
                            new PackageModel("a package"));
            aClass = aPackage.addClass(new ClassModel("AClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(aClass, controller.getSelectedClass());
    }

    @Test
    public void testOpenAddVariable() {
        controller.getShell();
        //AddVariableDialogue dialogue = controller.openAddVariable();
        //assertEquals(AddVariableDialogue.class, dialogue.getClass());
        //assertTrue(dialogue.isVisible());
        //dialogue.dispose();
        controller.close();
        fail();
    }

    @Test
    public void testGetSelectedPackage() {
        assertEquals(controller.getSelectedPackage(), main.getSelectedProject().getDefaultPackage());

    }

    @Test
    public void testDefaultModelSelection() {
        assertEquals(parentProject, controller.getSelectedProject());
        assertEquals(parentProject.getAllPackage(), controller.getSelectedPackage());
    }

    @Test
    public void testGetAllClasses() {
        fail("need to decide how Ill handle caching the big class tree");
    }
}
