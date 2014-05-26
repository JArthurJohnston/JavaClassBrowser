/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Exceptions.AlreadyExistsException;
import Exceptions.BaseException;
import Exceptions.VeryVeryBadException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class InterfaceModelTest extends BaseModelTest{
    
    private InterfaceModel anInterface;
    
    public InterfaceModelTest() {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        try {
            anInterface = parentPackage
                    .addInterface(new InterfaceModel("TestInterface"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        anInterface = null;
    }

    @Test
    public void testInit(){
        assertEquals(InterfaceModel.class, anInterface.getClass());
        assertEquals(parentProject, anInterface.getProject());
        assertEquals(parentPackage, anInterface.getParentPackage());
        assertEquals(parentPackage, anInterface.getParent());
        assertTrue(anInterface.getInterfaces().isEmpty());
        assertTrue(anInterface.isInterface());
        assertEquals(anInterface, parentProject.findInterface("TestInterface"));
    }
    
    @Test
    public void testAddInterface(){
        InterfaceModel child = null;
        try {
            child = anInterface.addInterface(new InterfaceModel("ChildInterface"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        assertEquals(anInterface, child.getParent());
        assertEquals(parentPackage, child.getParentPackage());
        assertEquals(parentProject, child.getProject());
        assertTrue(anInterface.getInterfaces().contains(child));
        assertFalse(parentPackage.getInterfaces().contains(child));
    }
    
    @Test
    public void testRemoveInterface(){
        this.testAddInterface();
        InterfaceModel child = anInterface.getInterfaces().getFirst();
        try {
            anInterface.removeInterface(child);
        } catch (VeryVeryBadException ex) {
            fail(ex.getMessage());
        }
        
        assertFalse(anInterface.getInterfaces().contains(child));
        assertNull(parentProject.findClass("ChildInterface"));
    }
    
    @Test
    public void testRemove(){
        this.testAddInterface();
        InterfaceModel child = anInterface.getInterfaces().getFirst();
        try {
            child.remove();
        } catch (BaseException ex) {
            fail(ex.getMessage());
        }
        assertFalse(anInterface.getInterfaces().contains(child));
        assertNull(parentProject.findClass("ChildInterface"));
    }
    
    @Test
    public void testCantRemoveInterfaceWithChildren(){
        fail();
    }
    
    @Test
    public void testAddInterfaceTriggersEvent(){
        fail();
    }
    
    @Test
    public void testRemoveInterfaceTriggersEvent(){
        fail();
    }
    
    @Test
    public void testClassImplementorsList(){
        fail();
        /*
        use the interface's classList var to keep a list 
        of every class that implements this interface.
        */
    }
    
    @Test
    public void testRemoveClassRemovesImplementorReference(){
        fail();
        //remove(), assertFalse(list.contains(removed));
    }
    
}
