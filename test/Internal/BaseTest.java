/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Internal;

import Exceptions.NameAlreadyExistsException;
import Models.ClassModel;
import Models.MethodModel;
import Models.PackageModel;
import Models.ProjectModel;
import java.lang.reflect.Field;                   
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.fail;

/**
 * uses reflection to grab private stuff from objects for testing
 * 
 * @author Arthur
 */
public class BaseTest {
    
    /**
     * gets a private variable from a class.
     * the user must cast the return object into the desired object
     * when this method is called.
     * this is for testing ONLY.
     * 
     * @param fromShell
     * @param componentName
     * @return 
     */
    public Object getVariableFromClass(Object objOfOrigin, String varName){
        Field privateField = null;
        try {
            privateField = getFieldFromClass(objOfOrigin.getClass(), varName);
            privateField.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(BaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            return privateField.get(objOfOrigin);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(BaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private Field getFieldFromClass(Class aClass, String fieldName) throws NoSuchFieldException{
        try {
            return aClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            Class superClass = aClass.getSuperclass();
            if (superClass == null) {
                throw ex;
            } else {
                return getFieldFromClass(superClass, fieldName);
            }
        } catch (SecurityException ex) {
            Logger.getLogger(BaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean compareStrings(String a, String b){
        if(a.compareTo(b)!=0){
            System.out.println("Expected: "+a);
            System.out.println("Got:      "+b);
            return false;
        }
        return true;
    }
    
    public void addClassToParent(ClassModel newObject, PackageModel parentObject){
        try {
            parentObject.addClass(newObject);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    public void addPackageToProject(PackageModel newObject, ProjectModel parentObject){
        try {
            parentObject.addPackage(newObject);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
    public void addMethodToClass(MethodModel newObject, ClassModel parentObject){
        try {
            parentObject.addClass(newObject);
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
    }
    
}
