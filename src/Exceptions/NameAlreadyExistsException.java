/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Arthur
 */
public class NameAlreadyExistsException extends BaseException{
    protected static final String message = " with that name already exists in ";
    
    public NameAlreadyExistsException(Object sender, Object target){
        super(NameAlreadyExistsException.constructMessage(sender, target));
    }
    
    private static String constructMessage(Object sender, Object target){
        return target.getClass().toString() + message + sender.toString();
    }
}
