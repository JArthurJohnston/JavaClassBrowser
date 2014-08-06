/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arthur
 */
public class LanguageTest {
    
    
    public LanguageTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void testWritesLanguageClassToXML(){
        Language java = new Language();
        java.setName("java");
        java.setType("static");
        
        
    }

    /**
     * Test of getType method, of class Language.
     */
    @Test
    public void testGetType() {
    }

    /**
     * Test of setType method, of class Language.
     */
    @Test
    public void testSetType() {
    }

    /**
     * Test of getName method, of class Language.
     */
    @Test
    public void testGetName() {
    }

    /**
     * Test of setName method, of class Language.
     */
    @Test
    public void testSetName() {
    }
    
}
