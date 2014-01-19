/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Arthur
 */
public class DoesNotExistException extends BaseException{
    public DoesNotExistException(Object sender, Object target){
        super(DoesNotExistException.constructMessage(sender, target));
    }
}
