/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;
import Models.ClassModel;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arthur
 */

@XmlRootElement
public class Language {    
    
    private String type;
    private String name;
    private HashMap<String, ClassModel> primitives;
    
    /**
     * static or dynamic
     * @return 
     */
    public String getType(){
        return this.type;
    }
    
    @XmlElement
    public void setType(final String type){
        this.type = type;
    }
    
    public String getName(){
        return this.name;
    }
    
    @XmlElement
    public void setName(final String name){
        this.name = name;
    }
    
    public HashMap<String, ClassModel> getPrimitives(){
        return this.primitives;
    }
    
    @XmlElement
    public void setPrimitives(HashMap<String, ClassModel> primitives){
        this.primitives = primitives;
    }
}
