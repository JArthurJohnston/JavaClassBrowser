/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.JaxBTests;

import LanguageBase.JAXBHelper;
import java.io.File;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class JAXBHelperTest {
    
    public JAXBHelperTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }

    
    @Test
    public void testWriteHashMapToXML() throws Exception{
        ObjectWithHash testObject = new ObjectWithHash();
        testObject.addThing("One", "ichi");
        testObject.addThing("Two", "ni");
        testObject.addThing("Three", "san");
        
        File output = new File("./test.xml");
        
        //assertFalse(output.exists());
        JAXBHelper.writeToFile(testObject, output);
        assertTrue(output.exists());
        System.out.println(output);
        
        output.delete();
        assertFalse(output.exists());
    }
    
    @XmlRootElement
    private class ObjectWithHash{
        private HashMap<String, Object> stuff = new HashMap();
        
        @XmlElement
        public void addThing(String key, Object value){
            this.stuff.put(key, value);
        }
        
        public Object getThing(String key){
            return this.stuff.get(key);
        }
    }
    
}
