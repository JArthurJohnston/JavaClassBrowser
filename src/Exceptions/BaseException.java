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
}
