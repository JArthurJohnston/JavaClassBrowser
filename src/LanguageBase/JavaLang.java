/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase;

import Models.ClassModel;

/**
 *
 * @author arthur
 */
public class JavaLang {
    private static final int numberOfClasses = 1;
    private static final ClassModel[] baseClasses = new ClassModel[numberOfClasses];
    
    private static ClassModel getPrimitive(int index, String className){
        if(baseClasses[index] == null)
            baseClasses[index] = new ClassModel(className);
        return baseClasses[index];
    }
    
    public static ClassModel getVoid(){
        return getPrimitive(0, "Void");
    }
}
