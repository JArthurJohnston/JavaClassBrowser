/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIModels.Buffer;

import Exceptions.BaseException;
import Models.ClassModel;
import Models.MethodModel;
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
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        buffer = null;
        parentClass = null;
        aMethod = null;
    }

    /**
     * Test of editableString method, of class MethodModelBuffer.
     */
    @Test
    public void testEditableString() {
        assertTrue(this.compareStrings(
                "private double testMethod(){}", buffer.editableString()));
    }

    /**
     * Test of parseSource method, of class MethodModelBuffer.
     */
    @Test
    public void testParseSource() {
    }
    
}
