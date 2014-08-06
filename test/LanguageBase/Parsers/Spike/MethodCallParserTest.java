/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Spike;

import Exceptions.AlreadyExistsException;
import Internal.BaseTest;
import MainBase.MainApplication;
import Models.ClassModel;
import Models.MethodModel;
import Models.MethodSignature;
import Models.PackageModel;
import Models.ProjectModel;
import Models.VariableModel;
import Types.ClassType;
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
 * @author arthur
 */
public class MethodCallParserTest extends BaseTest{
    private MethodCallParser parser;
    private static ProjectModel project;
    
    public MethodCallParserTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {
        try {
            MainApplication main = new MainApplication();
            project = main.addProject(new ProjectModel("Test Project"));
            PackageModel aPackage =
                    project.addPackage(new PackageModel("Test Package"));
            ClassModel class1 = aPackage.addClass(new ClassModel("TestClassOne"));
            ClassModel class2 = aPackage.addClass(new ClassModel("TestClassTwo"));
            ClassModel class3 = aPackage.addClass(new ClassModel("TestClassThree"));
            class1.addVariable(new VariableModel(class2, "varOne"));
            class1.addMethod(new MethodModel("methodOne"));
            class1.addMethod(new MethodModel("method1Two"));
            class2.addVariable(new VariableModel(class3, "varOne"));
            class2.addMethod(new MethodModel("methodOne"));
            class2.addMethod(new MethodModel("method2Two"));
            class3.addVariable(new VariableModel(class1, "varOne"));
            class3.addMethod(new MethodModel("methodOne"));
            class3.addMethod(new MethodModel("method3Two"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @AfterClass
    public static void tearDownClass(){
        project = null;
    }
    
    @Before
    @Override
    public void setUp() {
        parser = new MethodCallParser(project);
    }
    
    @After
    @Override
    public void tearDown() {
        parser = null;
    }

    @Test
    public void assertConstructorGetter() {
        String source = "";
        parser = new MethodCallParser(project, source);
        assertSame(ProjectModel.class, parser.getProject().getClass());
        assertSame(project, parser.getProject());
        assertSame(source, parser.getSource());
        assertSame(String.class, parser.getSource().getClass());
        assertTrue(parser.getReferences().isEmpty());
        assertTrue(parser.getNotFoundSource().isEmpty());
    }
    
    @Test
    public void testConstructorGetter_ProjectNoString(){
        parser = new MethodCallParser(project);
        assertSame(ProjectModel.class, parser.getProject().getClass());
        assertSame(project, parser.getProject());
        assertEquals("", parser.getSource());
        assertSame(String.class, parser.getSource().getClass());
        assertTrue(parser.getReferences().isEmpty());
        assertTrue(parser.getNotFoundSource().isEmpty());
        
    }
    
    @Test
    public void testParseForReferenceFindsClass() throws Exception{
        String source = "TestClassOne";
        parser.parseForReference(source);
        assertEquals(1, parser.getReferences().size());
        ClassModel aClass = project.findClass("TestClassOne");
        assertSame(aClass, parser.getReferences().getFirst());
    }
    
    @Test
    public void testParseForReferenceDoesNotFindClass(){
        String source = "TestClassDoesNotExist";
        parser.parseForReference(source);
        assertTrue(parser.getReferences().isEmpty());
        assertEquals(1, parser.getNotFoundSource().size());
        assertSame(source, parser.getNotFoundSource().getFirst());
    }
    
    @Test
    public void testParseForReferenceFindsMethod(){
        String source = "method1Two();";
        parser.parseForReference(source);
        assertEquals(1, parser.getReferences().size());
    }
    
    
    @Test
    public void testSetAndGetSymbolTable() throws Exception{
        assertTrue(parser.getSymbolTable().isEmpty());
        ClassModel testClass3 = project.findClass("TestClassThree");
        parser.addSymbol("someVar", testClass3);
        assertEquals(1, parser.getSymbolTable().size());
        assertEquals(testClass3, parser.getSymbolTable().get("someVar"));
    }
    
    @Test
    public void testSetClassAsBaseModelFillsSymbolTableWithVariables() throws Exception{
        ClassModel aClass = project.findClass("TestClassThree");
        //really should change getPrimitive to an enum
        aClass.addVariable(new VariableModel(ClassModel.getPrimitive("int"), "x"));
        aClass.addVariable(new VariableModel(project.findClass("TestClassOne"), "y"));
        aClass.addVariable(new VariableModel(ClassModel.getPrimitive("char"), "z"));
        
        parser = new MethodCallParser(aClass);
        
        assertEquals(4, parser.getSymbolTable().size());
    }
    
    @Test
    public void testMethodSignatureFromText() throws Exception{
        String source = "methodOne()";
        parser = new MethodCallParser(project);
        MethodSignature sig = parser.methodSignatureFromSource(source);
        assertEquals("methodOne", sig.name());
        parser.addSymbol("intX", ClassModel.getPrimitive("int"));
        parser.addSymbol("charY", ClassModel.getPrimitive("char"));
        
        source = "methodOne(intX, charY)";
        sig = parser.methodSignatureFromSource(source);
        assertEquals("methodOne", sig.name());
        assertEquals(2, sig.arguments().size());
    }
    
    private MethodModel setUpTestMethod() throws Exception{
        ClassModel aClass = project.addClass(new ClassModel("SomeClass"));
        //add some vars to the class
        
        LinkedList<VariableModel> args = new LinkedList();
        args.add(new VariableModel(ClassModel.getPrimitive("char"), "letter"));
        args.add(new VariableModel(project.findClass("TestClassOne"), "thing"));
        args.add(new VariableModel(ClassModel.getPrimitive("double"), "number"));
        //add args to the method
        
        MethodModel aMethod = aClass.addMethod(new MethodModel("testMethod"));
        aMethod.setArguments(args);
        return aMethod;
    }
    
    @Test
    public void testParserFillSymbolTableFromMethod() throws Exception{
        parser = new MethodCallParser(this.setUpTestMethod());
        
        assertEquals(3, parser.getSymbolTable().size());
    }
    
    @Test
    public void testMethodSignatureEqualsMethod() throws Exception{
        MethodModel testMethod = this.setUpTestMethod();
        MethodSignature expectedSig = testMethod.getSignature();
        parser = new MethodCallParser(testMethod);
        
    }
}
