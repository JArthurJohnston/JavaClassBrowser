/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;


/**
 *
 * @author Arthur
 */
public class BaseException extends Exception{
    protected static final String message = " with that name already exists in ";
    
    public BaseException(){}
    
    public BaseException(String message){
        super(message);
    }
    public BaseException(Throwable cause){
        super(cause);
    }
    public BaseException(String message, Throwable cause){
        super(message, cause);
    }
    
    
    protected static String constructMessage(Object sender, Object target){
        return target.getClass().toString() + message + sender.toString();
    }
}
