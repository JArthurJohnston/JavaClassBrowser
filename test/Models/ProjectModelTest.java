/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.AlreadyExistsException;
import Exceptions.BaseException;
import Exceptions.DoesNotExistException;
import Exceptions.PackageDoesNotExistException;
import Internal.BaseTest;
import MainBase.EventTester;
import MainBase.MainApplication;
import MainBase.SortedList;
import Models.MethodSignature;
import Models.ProjectModel.AllPackage;
import Types.SyntaxCharacters;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ProjectModelTest extends BaseTest{
    private ProjectModel project;
    
    public ProjectModelTest() {
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
        project = parentProject;
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        project = null;
    }

    @Test
    public void testInitialize(){
        assertEquals(project.getClassList().size(),  0);
        assertEquals(project.getClassList().getClass(),  LinkedList.class);
        assertEquals(project.getPackageList().getClass(),  LinkedList.class);
        assertEquals("parent project", project.name());
        assertEquals(2, project.getPackageList().size());
        assertEquals(project.findPackage("default package"), project.getPackageList().getFirst());
        assertEquals(project.findPackage("Parent Package"), project.getPackageList().getLast());
        
        project = new ProjectModel("A Project");
        assertEquals(ProjectModel.class, project.getClass());
        assertEquals(project.getPackageList().getClass(),  LinkedList.class);
        assertEquals(0, project.getClassList().size());
        assertEquals(project.findPackage("default package"), project.getPackageList().getFirst());
    }
    
    @Test
    public void testDefaultPackage(){
        assertEquals(PackageModel.class, project.getDefaultPackage().getClass());
        assertEquals("default package", project.getDefaultPackage().name());
        try {
            project.removePackage(project.getDefaultPackage());
            project.getDefaultPackage();
        } catch ( PackageDoesNotExistException ex) {
            assertEquals(DoesNotExistException.class, ex.getClass());
        }
    }
    
    @Test
    public void testAddPackage() {
        PackageModel newPackage = null;
        try {
            newPackage = project.addPackage(new PackageModel("New Package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(project, newPackage.getParent());
        assertEquals(newPackage ,project.findPackage("New Package"));
        
        assertEquals(newPackage, project.getPackageList().getLast());
        assertEquals(3, project.getPackageList().size());
        assertEquals("default package", project.getPackageList().get(0).name());
        assertEquals("Parent Package", project.getPackageList().get(1).name());
        assertEquals("New Package", project.getPackageList().get(2).name());
        try {
            project.addPackage(new PackageModel("New Package"));
            fail("NameAlreadyExistsException not thrown");
        } catch (AlreadyExistsException ex) {
            //there really should be an assertion here...
        }
        assertTrue(project.getPackageList().size() == 3);
    }
    
    @Test
    public void testGetPackageList(){
        assertEquals(2, project.getPackageList().size());
        assertEquals("default package", project.getPackageList().getFirst().name());
        assertEquals("Parent Package", project.getPackageList().getLast().name());
    }
    
    @Test
    public void testAddClass(){
        PackageModel testPackage = null;
        try {
            testPackage = project.addPackage(new PackageModel("Test Package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        ClassModel newClass = null;
        try {
            newClass = testPackage.addClass(new ClassModel("NewClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, project.getClassList().size());
        try {
            assertEquals(newClass, project.findClass("NewClass"));
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        try {
            testPackage.addClass(new ClassModel("NewClass"));
            fail("Expected exception was not thrown");
        } catch (AlreadyExistsException ex) {}
    }
    
    @Test
    public void testAddMethod(){
        HashMap<MethodSignature, LinkedList<MethodModel>> methods = 
                (HashMap)this.getVariableFromClass(project, "methodDefinitions");
        HashMap<String, LinkedList<MethodSignature>> methodNames = 
                (HashMap)this.getVariableFromClass(project, "methodNames");
        MethodModel aMethod = new MethodModel("aMethod");
        assertTrue(methods.isEmpty());
        assertTrue(methodNames.isEmpty());
        try {
            project.addMethod(aMethod);
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, methods.size());
        assertTrue(methods.containsKey(aMethod.getSignature()));
        assertEquals(1, methodNames.size());
        assertTrue(methodNames.containsKey(aMethod.name()));
        assertEquals(aMethod, methods.get(aMethod.getSignature()).getFirst());
        assertEquals(aMethod.getSignature(), methodNames.get(aMethod.name()).getFirst());
    }
    
    @Test
    public void testAddDuplicateMethodSignature(){
        HashMap<MethodSignature, LinkedList<MethodModel>> projectMethods = 
                (HashMap)this.getVariableFromClass(project, "methodDefinitions");
        MethodModel aMethod1 = null;
        MethodModel aMethod2 = null;
        try {
            assertTrue(projectMethods.isEmpty());
            aMethod1 = project.addMethod(new MethodModel("aMethod"));
            assertEquals(1, projectMethods.size());
            aMethod2 = project.addMethod(new MethodModel("aMethod"));
            assertEquals(1, projectMethods.size()); //assert duplicate methodSignatures cant be made
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(aMethod1.getSignature(), aMethod2.getSignature());
        assertEquals(2, projectMethods.get(aMethod1.getSignature()).size());
        assertTrue( projectMethods.get(aMethod1.getSignature()).contains(aMethod1));
        assertTrue( projectMethods.get(aMethod1.getSignature()).contains(aMethod2));
    }
    
    @Test
    public void testOverloadedMethods(){
        HashMap<MethodSignature, LinkedList<MethodModel>> methodDefs = 
                (HashMap)this.getVariableFromClass(project, "methodDefinitions");
        HashMap<String, LinkedList<MethodSignature>> methodNames = 
                (HashMap)this.getVariableFromClass(project, "methodNames");
        
        MethodModel method1 = new MethodModel("testMethod");
        MethodModel method2 = new MethodModel("testMethod");
        method2.setArguments(new SortedList()
                .addElm(new VariableModel("X", ClassModel.getPrimitive("int")))
                .addElm(new VariableModel("Y", ClassModel.getPrimitive("char"))));
        try {
            project.addMethod(method1);
            project.addMethod(method2);
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, methodNames.size());
        assertEquals(2, methodNames.get("testMethod").size());
        assertTrue(methodNames.get("testMethod").contains(method1.getSignature()));
        assertTrue(methodNames.get("testMethod").contains(method2.getSignature()));
        
        assertEquals(2, methodDefs.size());
        
        assertEquals(1, methodDefs.get(method1.getSignature()).size());
        assertTrue(methodDefs.get(method1.getSignature()).contains(method1));
        assertFalse(methodDefs.get(method1.getSignature()).contains(method2));
        
        assertEquals(1, methodDefs.get(method2.getSignature()).size());
        assertTrue(methodDefs.get(method2.getSignature()).contains(method2));
        assertFalse(methodDefs.get(method2.getSignature()).contains(method1));
        
        try {
            assertEquals(2, project.findMethods("testMethod").size());
            assertTrue(project.findMethods("testMethod").contains(method1));
            assertTrue(project.findMethods("testMethod").contains(method2));
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testRemoveMethod(){
        MethodModel aMethod = null;
        HashMap methods = 
                (HashMap)this.getVariableFromClass(project, "methodDefinitions");
        HashMap methodNames = 
                (HashMap)this.getVariableFromClass(project, "methodNames");
        this.testAddMethod();
        try {
            aMethod = project.findMethods("aMethod").getFirst();
            project.removeMethod(aMethod);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        try {
            project.findMethods("aMethod");
            fail("exception not thrown");
        } catch (DoesNotExistException ex) {
        }
        assertFalse(methods.containsKey(aMethod.getSignature()));
        assertFalse(methodNames.containsKey(aMethod.name()));
    }
    
    @Test
    public void testRemovePackage(){
        PackageModel packageToBeRemoved = null;
        try {
            packageToBeRemoved = project.addPackage(new PackageModel("PackageToBeRemoved"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(packageToBeRemoved, project.findPackage("PackageToBeRemoved"));
        assertTrue(project.getPackageList().contains(packageToBeRemoved));
        try {
            project.removePackage(packageToBeRemoved);
        } catch (PackageDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(null, project.findPackage("PackageToBeRemoved"));
        assertFalse(project.getPackageList().contains(packageToBeRemoved));
    }
    
    @Test
    public void testDescription(){
        //remember, every project starts out with a package called 'default package'
        project.setUserName("Barry Allen");
        String expectedDescr = "Project Name: parent project\n"+
                               "Author: Barry Allen\n"+
                               "Date Created: "+new Date().toString()+"\n"+
                               "Number of Packages: 2\n"+
                               "Number of Classes: 0";
        this.compareStrings(expectedDescr, project.getDescription());
    }
    
    @Test
    public void testDateCreated(){
        assertEquals(new Date().toString(), project.getDateCreated().toString());
    }
    
    @Test
    public void testUserName(){
        MainApplication testMain = new MainApplication();
        testMain.setUserName("Barry Allen");
        try {
            project = testMain.addProject(new ProjectModel("New Project"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(testMain.getUserName(), project.getUserName());
        project.setUserName("Kyle Raynor");
        assertEquals("Kyle Raynor", project.getUserName());
        assertEquals("Barry Allen", testMain.getUserName());
    }
    
    
    @Test
    public void testListClasses(){
        assertEquals(0, project.getClassList().size());
        PackageModel aPackage = null;
        ClassModel aClass = null;
        try {
            aPackage = project.addPackage(new PackageModel("New Package"));
            aClass = aPackage.addClass(new ClassModel("AClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, project.getClassList().size());
        ClassModel anotherClass = null;
        try {
            anotherClass = aClass.addClass(new ClassModel("AnotherClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, project.getClassList().size());
        ClassModel yetAnotherClass = null;
        try {
            yetAnotherClass = anotherClass.addClass(new ClassModel("YetAnotherClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(3, project.getClassList().size());
        PackageModel anotherPackage = null;
        try {
            anotherPackage = project.addPackage(new PackageModel("Another Package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        ClassModel classInAnotherPackage = null;
        try {
            classInAnotherPackage = anotherPackage.addClass(new ClassModel("AnotherClassInAnotherPackage"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(4, project.getClassList().size());
        LinkedList classList = project.getClassList();
        assertEquals(aClass, classList.get(0));
        assertEquals(anotherClass, classList.get(1));
        assertEquals(yetAnotherClass, classList.get(2));
        assertEquals(classInAnotherPackage, classList.get(3));
        PackageModel subPackage = null;
        try {
            subPackage = aPackage.addPackage(new PackageModel("Sub Package"));
        } catch (AlreadyExistsException ex) {
            Logger.getLogger(ProjectModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ClassModel subPackageClass =
                    subPackage.addClass(new ClassModel("SubPackageClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(5, project.getClassList().size());
    }
    
    
    @Test
    public void testGetMethodDefinitions(){
        try {
            PackageModel aPackage = project.addPackage(new PackageModel("A Package"));
            ClassModel aClass = aPackage.addClass(new ClassModel("AClass"));
            MethodModel aMethod = aClass.addMethod(new MethodModel("newMethod"));
            ClassModel anotherClass = aPackage.addClass(new ClassModel("AnotherClass"));
            
            assertEquals(aMethod, project.getMethodDefinitions(aMethod).getFirst());
            assertEquals(1, project.getMethodDefinitions(aMethod).size());
            aMethod = anotherClass.addMethod(new MethodModel("newMethod"));
            assertEquals(2, project.getMethodDefinitions(aMethod).size());
            assertEquals(aMethod, project.getMethodDefinitions(aMethod).getLast());
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testGetMain(){
        MainApplication main = new MainApplication();
        try {
            project = main.addProject(new ProjectModel("aProject"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(main, project.getMain());
    }
    
    @Test
    public void testGetDefaultPackage(){
        this.compareStrings("default package", project.getDefaultPackage().name);
    }
    
    @Test
    public void testAddPackageToDefaultPackage(){
        ClassModel aClass = null;
        try {
            aClass = project.getDefaultPackage().addClass(
                    new ClassModel("AClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals("default package", aClass.getParentPackage().name());
    }
    
    @Test
    public void testReservedWords(){
        assertEquals(32, ProjectModel.getReservedWords().size());
        LinkedList<String> copy = new LinkedList();
        for(String s : ProjectModel.getReservedWords().keySet())
            copy.add(s);
        for(String s : ProjectModel.getReservedWords().keySet())
            if(!copy.remove(s))
                fail();
        //^asserts that there are no duplicates in getReservedWords()
        assertTrue(copy.isEmpty());
    }
    
    @Test
    public void testLanguageSymbols(){
        assertSame(HashMap.class, ProjectModel.getLanguageSymbols().getClass());
        assertTrue(ProjectModel.getLanguageSymbols().containsKey('{'));
        assertEquals(SyntaxCharacters.CLOSE_BRACKET, ProjectModel.getLanguageSymbols().get(']'));
        assertEquals(16, ProjectModel.getLanguageSymbols().size());
    }
    
    @Test
    public void testFindObjectFromSource() throws Exception{
        ClassModel aClass = parentPackage.addClass(new ClassModel("SomeClass"));
        project.findObjectFromSource("SomeClass");
        assertEquals(1, project.findObjectFromSource("SomeClass").size());
        assertSame(aClass, project.findObjectFromSource("SomeClass").getFirst());
        
        MethodModel aMethod = aClass.addMethod(new MethodModel("someMethod"));
        assertEquals(1, project.findObjectFromSource("someMethod").size());
        assertSame(aClass, project.findObjectFromSource("someMethod").getFirst());
        
        
    }
    
    @Test
    public void testAddPackageTriggersEvent(){
        EventTester listener = this.getTestListener();
        try {
            project.addPackage(new PackageModel("SomePackage"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(listener.getEvent().isAdd());
        assertEquals(project, listener.getEvent().getSource());
        assertEquals("SomePackage", listener.getEvent().getModel().name());
    }
    
    @Test
    public void testRemovePackageTriggersEvent(){
        EventTester listener = this.getTestListener();
        PackageModel aPackage = null;
        try {
            aPackage = project.addPackage(new PackageModel("TO Be Removed"));
            project.removePackage(aPackage);
        } catch (AlreadyExistsException | PackageDoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertTrue(listener.getEvent().isRemove());
        assertEquals(project, listener.getEvent().getSource());
        assertEquals(aPackage, listener.getEvent().getModel());
    }
    
    @Test
    public void testNameChangedTriggersEvent(){
        EventTester listener = this.getTestListener();
        project.setName("NewProjectName");
        assertTrue(listener.getEvent().isChange());
        assertEquals(project, listener.getEvent().getSource());
    }
    
    @Test
    public void testAllPackageIsSingleton(){
        AllPackage all = project.getAllPackage();
        AllPackage anotherAll = project.getAllPackage();
        assertEquals(all, anotherAll);
    }
    
    private void setUpProjectPackageAndClasses(){
        try {
            PackageModel aPackage = project.addPackage(new PackageModel("a package"));
            aPackage.addClass(new ClassModel("AClass"));
            aPackage.addClass(new ClassModel("AnotherClass"));
            aPackage = project.addPackage(new PackageModel("Another Package"));
            aPackage.addClass(new ClassModel("SomeClass"));
            ClassModel aClass = aPackage.addClass(new ClassModel("SomeOtherClass"));
            aClass.addClass(new ClassModel("SomeSubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testAllPackageClassList(){
        AllPackage all= project.getAllPackage();
        assertEquals(2, all.getPackageList().size());
        assertEquals(project.getDefaultPackage(), all.getPackageList().getFirst());
        assertEquals(project.findPackage("Parent Package"), all.getPackageList().getLast());
        assertEquals(0, all.getClassList().size());
        
        try {
            project.findPackage("Parent Package")
                    .addClass(new ClassModel("TestClass"))
                        .addClass(new ClassModel("SubClass"));
            project.findPackage("default package").addClass(new ClassModel("SomeClass"));
            project.findPackage("Parent Package")
                    .addClass(new ClassModel("AnotherClass"))
                        .addClass(new ClassModel("SomeSubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(5, all.getClassList().size());
    }
    
    @Test
    public void testFindPrimitive(){
        try {
            assertEquals(ClassModel.getPrimitive("int"), project.findClass("int"));
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
    }
    
}
