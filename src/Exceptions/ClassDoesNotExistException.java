/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Arthur
 */
public class ClassDoesNotExistException extends BaseException{
    
    public ClassDoesNotExistException(Object sender, Object target){
        super(ClassDoesNotExistException.constructMessage(sender, target));
    }
    
}
