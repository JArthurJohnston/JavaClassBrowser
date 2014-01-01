/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Arthur
 */
public class MethodDoesNotExistException extends BaseException{
    
    public MethodDoesNotExistException(Object sender, Object target){
        super(MethodDoesNotExistException.constructMessage(sender, target));
    }
    
}
