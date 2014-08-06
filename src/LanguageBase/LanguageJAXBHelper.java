/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author arthur
 */
public class LanguageJAXBHelper {
    public static final String filePath = 
            "/home/arthur/NetBeans8Projects/JavaClassBrowser/Resources/";
    
    public static File file;
    
    public static void writeToFile(Object objectToBeWritten) throws JAXBException{
        file = new File(filePath + fileNameForObject(objectToBeWritten.getClass()));
        
        JAXBContext context = JAXBContext.newInstance(objectToBeWritten.getClass()); 
        
        Marshaller m = context.createMarshaller();
        
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        m.marshal(objectToBeWritten, System.out);
        m.marshal(objectToBeWritten, file);
    }
    
    public static Language objectFromFile(File input) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Language.class);
        Unmarshaller um = context.createUnmarshaller();
        return (Language)um.unmarshal(input);
    }
    
    static private String fileNameForObject(Class objectClass){
        return objectClass.getName()+ ".xml";
    }
    
}
