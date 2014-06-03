/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;

import Internal.BaseTest;
import Models.ClassModel;
import Models.MethodModel;
import Types.ScopeType;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class CompilerTest extends BaseTest{
    private Compiler compiler;
    
    public CompilerTest() {
    }
    
    @Before
    @Override
    public void setUp() {
    }
    
    @After
    @Override
    public void tearDown() {
        compiler = null;
    }
    
    private void assertValidCompile(){
        if(!compiler.performCompilation()){
            compiler.printErrors();
            fail();
        }
        
    }

    @Test
    public void testParseClass() {
        fail();
        /*
        String source = "public class SomeClass {"
                + "public String helloWorld(){"
                + "System.out.println(\"hello world\");"
                + "}"
                + "}\n";
        compiler = new Compiler(source);
        this.assertValidCompile();
                */
    }
    
    @Test
    public void testParseMethod(){
        MethodModel aMethod = new MethodModel("someMethod");
        aMethod.setScope(ScopeType.PRIVATE);
        aMethod.setReturnType(ClassModel.getPrimitive("char"));
        aMethod.setSource("return 'c';");
        
        assertTrue(
                this.compareStrings("private char someMethod(){\nreturn 'c';\n}"
                        , aMethod.toSourceString()));
        
        compiler = new Compiler(aMethod);
        this.assertValidCompile();
    }
    
    @Test
    public void getParseTree(){
        this.testParseMethod();
        Trees aTree = compiler.getParseTree();
        TreePathScanner scanner = new TreePathScanner();
        System.out.println(aTree.toString());
    }
    
}
