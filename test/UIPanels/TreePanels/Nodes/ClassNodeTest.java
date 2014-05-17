/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Internal.Mocks.MockClassModel;
import Models.BaseModel;
import Models.ClassModel;
import java.util.HashMap;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class ClassNodeTest {
    private HashMap<BaseModel, ModelNode> treeHash;
    private ClassNode node;
    
    public ClassNodeTest() {
    }
    
    @Before
    public void setUp() {
        treeHash = new HashMap();
        node = ClassNode.getDefaultRoot();
    }
    
    @After
    public void tearDown() {
        node = null;
    }
    
    private MockClassModel getTestClass(){
        MockClassModel m = new MockClassModel("AClass");
        m.addClass(new MockClassModel("aSubClass1"));
        m.addClass(new MockClassModel("aSubClass2"));
        m.addClass(new MockClassModel("aSubClass3"));
        return m;
    }
    
    private void verifyNodeSize(int x){
        assertEquals(x, node.size());//minus one for the root node
        assertEquals(x, treeHash.size());
    }

    /**
     * Test of getModel method, of class ClassNode.
     */
    @Test
    public void testDefaultRoot() {
        assertEquals(ClassModel.getObjectClass(), node.getModel());
        assertEquals(1, node.size());
        assertEquals(0, treeHash.size());
    }
    
    @Test
    public void testNodeFromClass(){
        MockClassModel aClass = this.getTestClass();
        node = new ClassNode(aClass, treeHash);
        assertEquals(aClass, node.getModel());
        this.verifyNodeSize(4);
    }
    
    @Test
    public void testNodeFromClassAndSubClasses(){
        MockClassModel aClass = this.getTestClass();
        ((MockClassModel)aClass.getSubClasses().get(1))
                .addClass(new MockClassModel("aSUbSubClass"));
        
        node = new ClassNode(aClass, treeHash);
        assertEquals(aClass, node.getModel());
        this.verifyNodeSize(5);
    }
    
    @Test
    public void testAddClassToNode(){
        node.addNode(new ClassNode(new ClassModel("SomeClassX"), treeHash));
        assertEquals(2, node.size());
        assertEquals(1, treeHash.size());
    }
    
    @Test
    public void testRemoveClassFromNode(){
        ClassNode nodeToBeRemoved = 
                new ClassNode(new ClassModel("RemovedClass"), treeHash);
        node.addNode(nodeToBeRemoved);
        assertEquals(2, node.size());
        assertEquals(1, treeHash.size());
        node.removeNode(nodeToBeRemoved, treeHash);
        assertEquals(1, node.size());
        assertEquals(0, treeHash.size());
    }
}
