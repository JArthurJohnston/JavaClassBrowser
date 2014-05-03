/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Internal;

import MainBase.MainApplication;                   
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;

/**
 * uses reflection to grab private stuff from objects for testing
 * 
 * @author Arthur
 */
public class BaseTest {
    protected MainApplication main;
    
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
    
    public Object callMethodFromClass(Object objectOfOrigin, String name){
        Method aMethod = this.getMethodFromClass(objectOfOrigin, name);
        Object returnValue = null;
        try {
            returnValue = aMethod.invoke(objectOfOrigin, null);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BaseTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(BaseTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(BaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        aMethod.setAccessible(false);
        return returnValue;
    }
    
    public Method getMethodFromClass(Object objectOfOrigin, String methodName){
        Method aMethod = null;
        try {
            aMethod =  objectOfOrigin.getClass().getDeclaredMethod(methodName, null);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(BaseTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(BaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        aMethod.setAccessible(true);
        return aMethod;
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
    
    private boolean listHasClass(List aList, Object aClass){
        for(Object o : aList){
            if(o.getClass() == aClass)
                return true;
        }
        return false;
    }
    
    protected void assertListHasClass(List aList, Object aClass){
        assertTrue(this.listHasClass(aList, aClass));
    }
    
    protected void denyListHasClass(List aList, Object aClass){
        assertFalse(this.listHasClass(aList, aClass));
    }
    
}
