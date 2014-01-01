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
    
    public NameAlreadyExistsException(Object sender, Object target){
        super(NameAlreadyExistsException.constructMessage(sender, target));
    }
    
}
