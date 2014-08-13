/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanguageBase;

import Models.ClassModel;
import Models.PrimitiveTypeModel;
import java.io.File;
import java.util.HashMap;
import javax.xml.bind.JAXBException;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

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
    public void testWritesLanguageClassToXML() throws Exception {
        HashMap<String, ClassModel> primitives = new HashMap();
        primitives.put("int", new PrimitiveTypeModel("int"));

        Language java = new Language();
        java.setName("java");
        java.setType("static");
        java.setPrimitives(primitives);

        JAXBHelper.writeToFile(java);

        File test = JAXBHelper.file;
        assertTrue(test.exists());

        test.delete();
        assertFalse(test.exists());
    }

    @Test
    public void testCreatesClassFromXML() throws Exception {
        File input = new File(JAXBHelper.filePath + "Java_Lang.xml");

        Language java = (Language) JAXBHelper.objectFromFile(input);

        assertEquals("java", java.getName());
        assertEquals("static", java.getType());
        assertSame(HashMap.class, java.getPrimitives().getClass());
        assertEquals(1, java.getPrimitives().size());
        ClassModel aClass = java.getPrimitives().get("int");
        assertEquals("int", aClass.name());
    }

}
