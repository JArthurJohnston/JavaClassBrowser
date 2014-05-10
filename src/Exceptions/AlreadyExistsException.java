/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Arthur
 */
public class AlreadyExistsException extends BaseException{
    
    public AlreadyExistsException(Object sender, Object target){
        super(AlreadyExistsException.constructMessage(sender, target));
    }
    
}
