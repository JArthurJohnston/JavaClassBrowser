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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.fail;
import org.junit.Test;

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
            System.out.println("Expected:\n"+a);
            System.out.println("****");
            System.out.println("Got:\n"+b);
            return false;
        }
        return true;
    }
    
    public ClassModel addClassToParent(String newClassName, PackageModel parentObject){
        try {
            return parentObject.addClass(new ClassModel(parentObject, newClassName));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return null;
    }
    
    public PackageModel addPackageToProject(String newPackageName, ProjectModel parentObject){
        try {
            return parentObject.addPackage(new PackageModel(parentObject, newPackageName));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return null;
    }
    
    public MethodModel addMethodToClass(String newMethodName, ClassModel parentObject){
        try {
            return parentObject.addMethod(new MethodModel(parentObject, newMethodName));
        } catch (NameAlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return null;
    }
    
    /**
     * Compares two lists, checks to see if their sizes
     * are the same, then check to see if their elements are the same.
     * note, this means that the ORDER of the elements is important.
     * Keep that in mind when your building a list in a test.
     * @param a
     * @param b
     * @return 
     */
    public boolean compareLists(List a, List b){
        if(a.size() != b.size())
            return false;
        for(int i=0;i<a.size();i++){
            if(a.get(i) != b.get(i))
                return false;
        }
        return true;
    }
    
    @Test
    public void testCloseAndDispose(){
        fail("subclass responsibility");
    }
}
