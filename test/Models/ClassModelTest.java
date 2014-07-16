/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Exceptions.AlreadyExistsException;
import Exceptions.BaseException;
import Exceptions.CannotBeDeletedException;
import Exceptions.DoesNotExistException;
import Exceptions.VeryVeryBadException;
import MainBase.EventTester;
import MainBase.Events.ModelEvents.BaseModelUpdatedEvent;
import MainBase.Events.ModelEvents.ModelAddedEvent;
import MainBase.MainApplication;
import MainBase.SortedList;
import Types.ClassType;
import Types.ScopeType;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Arthur
 */
public class ClassModelTest extends BaseModelTest{
    private ClassModel testClass;
    
    public ClassModelTest() {
    }
    
    @Before
    @Override
    public void setUp(){
        super.setUp();
        try {
            testClass = parentPackage.addClass(new ClassModel("InstanceClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        testClass = null;
    }
    
    private void setUpSubClasses(){
        try {
            testClass.addClass(new ClassModel("ASubClasss1"));
            ClassModel subClass = testClass.addClass(new ClassModel("ASubClasss2"));
            testClass.addClass(new ClassModel("ASubClasss3"));
            subClass.addClass(new ClassModel("ASubSubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testInitialize(){
        assertEquals("InstanceClass", testClass.name());
        assertEquals(parentProject, testClass.getProject());
        assertEquals(parentPackage, testClass.getParentPackage());
        assertEquals(ClassModel.class, testClass.getClass());
        assertEquals(LinkedList.class, testClass.getClassList().getClass());
    }

    /**
     * Test of addClass method, of class ClassModel.
     */
    @Test
    public void testAddClass() {
        ClassModel newSubClass = null;
        try {
            newSubClass = testClass.addClass(new ClassModel("NewSubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(parentProject, newSubClass.getProject());
        assertEquals(testClass, newSubClass.getParent());
        try {
            assertEquals(newSubClass, parentProject.findClass("NewSubClass"));
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(parentPackage, newSubClass.getParentPackage());
        assertTrue(testClass.getClassList().contains(newSubClass));
        assertTrue(parentProject.getClassList().contains(newSubClass));
        assertTrue(parentPackage.getClassList().contains(newSubClass));
        
        assertEquals(2, parentProject.getClassList().size());
        assertEquals(2, testClass.getClassList().size());
        assertEquals(newSubClass, testClass.getClassList().getLast());
        try {
            testClass.addClass(new ClassModel("NewSubClass"));
            fail("Expected exception not thrown");
        } catch (AlreadyExistsException ex) {
            assertEquals(AlreadyExistsException.class, ex.getClass());
        }
    }
    
    @Test
    public void testRemoveClass(){
        ClassModel classToBeRemoved = null;
        try {
            classToBeRemoved = parentPackage.addClass(new ClassModel("ClassToBeRemoved"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, parentProject.getClassList().size());
        assertEquals(parentPackage, classToBeRemoved.getParentPackage());
        try {
            classToBeRemoved.remove();
        } catch (VeryVeryBadException | CannotBeDeletedException ex) {
            fail(ex.getMessage());
        }
        //if the above were in java 6 youd have to use the  || operator
        assertFalse(testClass.getClassList().contains(classToBeRemoved));
        assertFalse(parentProject.getClassList().contains(classToBeRemoved));
        try {
            classToBeRemoved = testClass.addClass(new ClassModel("classToBeRemoved"));
            testClass.remove();
            fail("exception not thrown");
        } catch (CannotBeDeletedException | VeryVeryBadException | AlreadyExistsException ex) {
            assertEquals(CannotBeDeletedException.class, ex.getClass());
        }
        assertTrue(testClass.getClassList().contains(classToBeRemoved));
    }
    
    @Test
    public void testMethodDefinitions() throws Exception{
        MethodModel methodOne = testClass.addMethod(new MethodModel("methodOne"));
        assertEquals(1, parentProject.getMethodDefinitions(methodOne).size());
        assertTrue(parentProject.getMethodDefinitions(methodOne).contains(methodOne));
        
        ClassModel classTwo = parentPackage.addClass(new ClassModel("ClassTwo"));
        MethodModel methodTwo = classTwo.addMethod(new MethodModel("methodOne"));
        
        assertEquals(2, parentProject.getMethodDefinitions(methodTwo).size());
        assertTrue(parentProject.getMethodDefinitions(methodOne).contains(methodOne));
        assertTrue(parentProject.getMethodDefinitions(methodOne).contains(methodTwo));
    }
    
    @Test
    public void testOverloadedMethodDefinitions() throws Exception{
        MethodModel methodOne = new MethodModel("someMethod");
        methodOne.arguments(new SortedList<VariableModel>()
                .addElm(new VariableModel("intX", ClassModel.getPrimitive("int")))
                .addElm(new VariableModel("charY", ClassModel.getPrimitive("char"))));
        testClass.addMethod(methodOne);
        
        assertEquals(1, parentProject.getMethodDefinitions(methodOne).size());
    }
    
    @Test
    public void testAddMethod(){
        MethodModel newMethod = null;
        try {
            newMethod = testClass.addMethod(new MethodModel("newMethod"));
        } catch (BaseException ex) {
            fail("Exception thrown when it shouldnt");
        }
        assertEquals(testClass, newMethod.getParent());
        assertEquals(1, testClass.getMethods().size());
        assertTrue(testClass.getMethods().contains(newMethod));
        try {
            assertEquals(newMethod, parentProject.findMethods("newMethod").getFirst());
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        try {
            testClass.addMethod(new MethodModel("newMethod"));
            fail("Exception not thrown");
        } catch (BaseException ex) {
            assertEquals(AlreadyExistsException.class, ex.getClass());
        }
    }
    
    /*
    Project will hold onto ...
    Hashmap<String LinkedList<MethodModel>> methodDefinitions;
    Hashmap<String LinkedList<ClassModel>> classDefinitions;
    
    findClass or findMethod will return a list of the corresponding model...
        or just one model if the list has only one element.
    */
    
    @Test
    public void testRemoveMethod(){
        this.testAddMethod();
        MethodModel newMethod = null;
        try {
            newMethod = parentProject.findMethods("newMethod").getFirst();
            testClass.removeMethod(newMethod);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getMethods().isEmpty());
        try {
            parentProject.findMethods("newMethod");
            fail("exception not thrown");
        } catch (DoesNotExistException ex) {
        }
    }
    
    @Test
    public void testMoveToPackage(){
        PackageModel anotherPackage = new PackageModel("AnotherPackage");
        try {
            testClass.moveToPackage(anotherPackage);
        } catch (AlreadyExistsException | VeryVeryBadException ex) {
            fail(ex.getMessage());
        }
        assertTrue(anotherPackage.getClassList().contains(testClass));
        assertFalse(parentPackage.getClassList().contains(testClass));
        assertEquals(anotherPackage, testClass.getParentPackage());
        ClassModel subClass = null;
        try {
            subClass = testClass.addClass(new ClassModel("SubClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(anotherPackage, subClass.getParentPackage());
        assertTrue(anotherPackage.getClassList().contains(subClass));
        assertFalse(parentPackage.getClassList().contains(subClass));
    }
    
    @Test
    public void testAddVariable(){
        VariableModel var = new VariableModel(ScopeType.PUBLIC, testClass, "aVar");
        LinkedList varList = (LinkedList)this.getVariableFromClass(testClass, "variables");
        try {
            testClass.addVariable(var);
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, varList.size());
        try {
            testClass.addVariable(var);
            fail("exception not thrown");
        } catch (AlreadyExistsException ex) {}
        assertEquals(testClass, var.getParentClass());
    }
    
    @Test
    public void testGetVariables(){
        assertEquals(0, testClass.getVariables().size());
        try {
            testClass.addVariable(new VariableModel(ScopeType.PRIVATE, testClass, "aVar"));
            testClass.addVariable(new VariableModel(ScopeType.PUBLIC, testClass, "anotherVar"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(2, testClass.getVariables().size());
        try {
            testClass.removeVariable((VariableModel)testClass.getVariables().get(0));
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertEquals(1, testClass.getVariables().size());
        try {
            testClass.removeVariable(new VariableModel(ScopeType.NONE, testClass, "nonExistentVar"));
            fail("Exception not thrown");
        } catch (DoesNotExistException ex) {}
    }
    
    @Test
    public void testRemoveVariable(){
        VariableModel aVar = null;
        try {
            aVar = testClass.addVariable(new VariableModel(ScopeType.PRIVATE, testClass, "aVar"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        try {
            testClass.removeVariable(aVar);
        } catch (DoesNotExistException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getVariables().isEmpty());
        try {
            testClass.removeVariable(aVar);
            fail("exception not thrown");
        } catch (DoesNotExistException ex) { }
    }
    
    @Test
    public void testGetClassList(){
        assertEquals(1, testClass.getClassList().size());
        assertEquals(testClass, testClass.getClassList().getFirst());
        ClassModel newClass = null;
        try {
            newClass = testClass.addClass(new ClassModel("NewClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(newClass, testClass.getClassList().getLast());
        assertEquals(2, testClass.getClassList().size());
        try {
            newClass.remove();
        } catch (CannotBeDeletedException | VeryVeryBadException ex) {
            assertEquals(VeryVeryBadException.class, ex.getClass());
            fail(ex.getMessage());
        }
        assertEquals(1, testClass.getClassList().size());
        assertEquals(testClass, testClass.getClassList().getLast());
        assertEquals(testClass.getClassList().getFirst(), testClass.getClassList().getLast());
    }
    
    @Test
    public void testGetStaticMethods(){
        MethodModel aMethod = null;
        assertTrue(testClass.getStaticMethods().isEmpty());
        try {
            aMethod = testClass
                    .addMethod(new MethodModel("aMethod", ClassType.STATIC));
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getStaticMethods().contains(aMethod));
        try {
            aMethod = testClass
                    .addMethod(new MethodModel("anotherMethod", ClassType.INSTANCE));
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
        assertFalse(testClass.getStaticMethods().contains(aMethod));
    }
    
    @Test
    public void testGetInstanceMethods(){
        MethodModel aMethod = null;
        assertTrue(testClass.getInstanceMethods().isEmpty());
        try {
            aMethod = testClass.addMethod(new MethodModel("aMethod", ClassType.INSTANCE));
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getInstanceMethods().contains(aMethod));
        try {
            aMethod = testClass.addMethod(new MethodModel("anotherMethod", ClassType.STATIC));
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
        assertFalse(testClass.getInstanceMethods().contains(aMethod));
    }
    
    @Test
    public void testGetStaticVars(){
        VariableModel aVar = null;
        assertTrue(testClass.getStaticVars().isEmpty());
        try {
            aVar = testClass
                    .addVariable(
                            new VariableModel(ClassType.STATIC, 
                                    new ClassModel(), "aVar"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getVariables().contains(aVar));
        assertTrue(testClass.getStaticVars().contains(aVar));
        try {
            aVar = testClass
                    .addVariable(
                            new VariableModel(ClassType.INSTANCE,
                                    new ClassModel(), "anotherVar"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(testClass.getStaticVars().contains(aVar));
    }
    
    @Test
    public void testGetInstanceVars(){
        VariableModel aVar = null;
        assertTrue(testClass.getStaticVars().isEmpty());
        try {
            aVar = testClass.addVariable(new VariableModel(ClassType.INSTANCE, new ClassModel(), "aVar"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertTrue(testClass.getInstanceVars().contains(aVar));
        try {
            aVar = testClass.addVariable(new VariableModel(ClassType.STATIC, new ClassModel(), "anotherVar"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertFalse(testClass.getInstanceVars().contains(aVar));
    }
    
    @Test
    public void testGetReturnTyppe(){
        assertEquals(testClass,testClass.getReturnType());
    }
    
    @Test
    public void testGetMain(){
        MainApplication main = new MainApplication();
        try {
            ProjectModel aProject = main.addProject(new ProjectModel("aProject"));
            PackageModel aPackage = aProject.addPackage(new PackageModel("a package"));
            testClass = aPackage.addClass(new ClassModel("AClass"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(main, testClass.getMain());
    }
    
    @Test
    public void testIsPrimitive(){
        assertFalse(testClass.isPrimitive());
        testClass.setName("int");
        assertTrue(testClass.isPrimitive());
    }
    
    @Test
    public void testGetPrimitiveType(){
        ClassModel intModel = ClassModel.getPrimitive("int");
        assertTrue(intModel.isPrimitive());
        ClassModel anotherInt = ClassModel.getPrimitive("int");
        assertEquals(anotherInt, intModel);
    }
    
    @Test
    public void testGetParentPackage(){
        try {
            assertEquals(parentPackage, testClass.getParentPackage());
            ClassModel subClass = testClass.addClass(new ClassModel("ASubClass"));
            assertEquals(parentPackage, subClass.getParentPackage());
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testRemove() throws Exception{
        fail();
    }
    
    @Test
    public void testNameChange(){
        /*
        needs to be wrapped up into re-factoring logic. 
        */
        try {
            parentPackage.addClass(new ClassModel("OneName"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        try {
            testClass.rename("OneName");
            fail("exception Not Thrown");
        } catch (AlreadyExistsException ex) {}
        assertEquals("InstanceClass", testClass.name());
        //assert other instances of the class have been renamed
    }
    
    
    @Test
    public void testAddClassTriggersEvent(){
        EventTester listener = this.getTestListener();
        try {
            ClassModel newClass = testClass.addClass(new ClassModel("SomeNewClass"));
            assertTrue(listener.eventTriggered());
            BaseModelUpdatedEvent e = listener.getEvent();
            assertEquals(ModelAddedEvent.class, e.getClass());
            assertEquals(e.getSource(), testClass);
            assertEquals(e.getModel(), newClass);
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testRemoveClassTriggersEvent(){
        EventTester listener = this.getTestListener();
        this.setUpSubClasses();
        ClassModel subClass = testClass.getSubClasses().getLast();
        try {
            testClass.removeClass(subClass);
        } catch (VeryVeryBadException ex) {
            fail(ex.getMessage());
        }
        assertTrue(listener.eventTriggered());
        BaseModelUpdatedEvent e = listener.getEvent();
        assertTrue(e.isRemove());
        assertEquals(testClass, e.getSource());
        assertEquals(subClass, e.getModel());
    }
    
    @Test
    public void testClassNameChangedTriggersEvent(){
        EventTester listener = this.getTestListener();
        testClass.setName("NewClassName");
        assertTrue(listener.eventTriggered());
        BaseModelUpdatedEvent e = listener.getEvent();
        assertTrue(e.isChange());
        assertEquals(testClass, e.getSource());
    }
    
    @Test
    public void testAddDuplicateClassDoenstFireEvent(){
        //cant remember why I wrote this...
        fail();
    }
    
    @Test
    public void testClassDeclaration(){
        String expected =  "class InstanceClass";
        this.compareStrings(expected, testClass.getDeclaration());
        testClass.setScope(ScopeType.PUBLIC);
        expected =  "public class InstanceClass";
        this.compareStrings(expected, testClass.getDeclaration());
        testClass.setParent(new ClassModel("ParentClass"));
        expected =  "public class InstanceClass extends ParentClass";
        this.compareStrings(expected, testClass.getDeclaration());
        try {
            testClass.addInterface(new InterfaceModel("SomeInterface"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        expected =  "public class InstanceClass extends ParentClass implements SomeInterface";
        this.compareStrings(expected, testClass.getDeclaration());
        try {
            testClass.addInterface(new InterfaceModel("SomeOtherInterface"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        expected =  "public class InstanceClass extends ParentClass implements SomeInterface implements SomeOtherInterface";
        this.compareStrings(expected, testClass.getDeclaration());
    }
    
    @Test
    public void testClassDeclarationFinal(){
        testClass.setFinal(true);
        this.compareStrings("public final InstanceClass", testClass.getDeclaration());
    }
    
    @Test
    public void testClasImplementsInterface(){
        InterfaceModel anInterface = new InterfaceModel("SomeInterface");
        anInterface = testClass.implementsInterface(anInterface);
        assertTrue(testClass.getInterfaces().contains(anInterface));
        assertTrue(anInterface.getClassList().contains(testClass));
        /*
        an interface should have reference to all the classes that implement it
        so that it can tell them to update when a new abstract method is added
        or removed from the interface.
        */
    }
    
    @Test
    public void testMoveClass(){
        ClassModel p1 = null;
        ClassModel p2 = null;
        ClassModel child = null;
        try {
            p1 = parentPackage.addClass(new ClassModel("ParentOne"));
            p2 = parentPackage.addClass(new ClassModel("ParentTwo"));
            child = p1.addClass(new ClassModel("Child"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        child.moveToClass(p2);
        assertEquals(p2, child.getParent());
        assertFalse(p1.getSubClasses().contains(child));
        assertTrue(p2.getSubClasses().contains(child));
    }
    
    @Test
    public void testToSourceString(){
        fail();
    }
    
    @Test
    public void testGeneric(){
        fail();
    }
}
