/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;
import javax.naming.Context;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author arthur
 */

@XmlRootElement(name = "Language_Name")
public class Language {    
    
    public static final String filePath = 
            "/home/arthur/NetBeansProjects/JavaClassBrowser/Resources/testXML.xml";
    private String type;
    private String name;
    
    /**
     * static or dynamic
     * @return 
     */
    public String getType(){
        return this.type;
    }
    
    public void setType(final String type){
        this.type = type;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(final String name){
        this.name = name;
    }
    
    public void writeToFile() throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(this.getClass()); 
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }
}
