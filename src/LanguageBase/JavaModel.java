/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;

import Models.*;
import java.util.HashMap;

/**
 * a class to handle any java-specific code
 * @author arthur
 */
public class JavaModel {
    
    private static PackageModel javaLangPac;
    
    public static void setUpJavaLangClasses(ProjectModel aProject){
        HashMap classMap = aProject.getClasses();
        javaLangPac = new PackageModel(aProject, "Java.Lang");
        for(JavaDotLangClasses cls: JavaDotLangClasses.values()){
            classMap.put(cls, new ClassModel(javaLangPac, cls.toString()));
        }
    }
    
}
