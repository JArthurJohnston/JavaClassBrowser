/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Exceptions.BaseException;
import Models.ClassModel;
import Models.MethodModel;
import Types.ClassType;
import Types.ScopeType;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class MethodModelBufferTest extends BaseBufferTest {
    private MethodModelBuffer buffer;
    private MethodModel aMethod;
    private ClassModel parentClass;
    
    public MethodModelBufferTest() {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        try {
            parentClass = parentPackage.addClass(new ClassModel("ParentClass"));
            aMethod = parentClass.addMethod(new MethodModel("testMethod"));
                aMethod.setScope(ScopeType.PRIVATE);
                aMethod.setReturnType(ClassModel.getPrimitive("double"));
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
        buffer = new MethodModelBuffer(aMethod);
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        buffer = null;
        parentClass = null;
        aMethod = null;
    }
    
    @Test
    public void testInit(){
        assertEquals(ScopeType.PRIVATE, buffer.getScope());
        assertEquals(ClassType.INSTANCE, buffer.getClasType());
        assertFalse(buffer.isAbstract());
        assertEquals(ClassModel.getPrimitive("double"), buffer.getReturnType());
        assertNull(buffer.getBody());
        assertTrue(buffer.getParameters().isEmpty());
    }

    /**
     * Test of editableString method, of class MethodModelBuffer.
     */
    @Test
    public void testEditableString() {
        assertTrue(this.compareStrings(
                "private double testMethod(){\n\n"
                        + "}", buffer.editableString()));
    }

    /**
     * Test of parseSource method, of class MethodModelBuffer.
     */
    @Test
    public void testParseSource() {
        buffer.parseSource("public double testMethod(){};");
        assertEquals(ClassModel.getPrimitive("double"), buffer.getReturnType());
        assertEquals("{}", buffer.getBody());
    }
    
    @Test
    public void testScope(){
        buffer.parseSource("public double testMethod(){}");
        assertEquals(ScopeType.PUBLIC, buffer.getScope());
        buffer.parseSource("private double testMethod(){}");
        assertEquals(ScopeType.PRIVATE, buffer.getScope());
        buffer.parseSource("double testMethod(){}");
        assertEquals(ScopeType.NONE, buffer.getScope());
    }
    
    @Test
    public void testStatic(){
        buffer.parseSource("static double testMethod(){}");
        assertEquals(ClassType.STATIC, buffer.getType());
        buffer.parseSource("double testMethod(){}");
        assertEquals(ClassType.INSTANCE, buffer.getType());
    }
    
    @Test
    public void testAbstract(){
        buffer.parseSource("abstract double testMethod(){}");
        assertTrue(buffer.isAbstract());
        buffer.parseSource("double testMethod(){}");
        assertFalse(buffer.isAbstract());
    }
    
    @Test
    public void parseReturnType(){
        buffer.parseSource("float testMethod(){}");
        assertEquals(ClassModel.getPrimitive("float"), buffer.getReturnType());
    }
    
    @Test
    public void parseParameters(){
        fail();
    }
    
}
