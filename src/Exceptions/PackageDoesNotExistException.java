/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Arthur
 */
public class PackageDoesNotExistException extends BaseException{
    
    public PackageDoesNotExistException(Object sender, Object target){
        super(PackageDoesNotExistException.constructMessage(sender, target));
    }
    
}
