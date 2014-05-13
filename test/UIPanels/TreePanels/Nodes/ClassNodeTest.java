/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Internal.Mocks.MockClassModel;
import Models.ClassModel;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ClassNodeTest {
    private ClassNode node;
    
    public ClassNodeTest() {
    }
    
    @Before
    public void setUp() {
        node = ClassNode.getDefaultRoot();
    }
    
    @After
    public void tearDown() {
        node = null;
    }
    
    private MockClassModel getTestClass(){
        MockClassModel m = new MockClassModel("AClass");
        m.addClass(new MockClassModel("aSubClass"));
        m.addClass(new MockClassModel("aSubClass"));
        m.addClass(new MockClassModel("aSubClass"));
        return m;
    }

    /**
     * Test of getModel method, of class ClassNode.
     */
    @Test
    public void testDefaultRoot() {
        assertEquals(ClassModel.getObjectClass(), node.getModel());
        assertEquals(1, node.size());
    }
    
    @Test
    public void testNodeFromClass(){
        MockClassModel aClass = this.getTestClass();
        node = new ClassNode(aClass);
        assertEquals(aClass, node.getModel());
        assertEquals(4, node.size());
    }
    
    @Test
    public void testNodeFromClassAndSubClasses(){
        MockClassModel aClass = this.getTestClass();
        ((MockClassModel)aClass.getSubClasses().get(1))
                .addClass(new MockClassModel("aSUbSubClass"));
        
        node = new ClassNode(aClass);
        assertEquals(aClass, node.getModel());
        assertEquals(5, node.size());
    }
}
