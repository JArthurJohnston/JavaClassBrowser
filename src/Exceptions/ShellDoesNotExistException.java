/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;

/**
 *
 * @author arthur
 */
public class ShellDoesNotExistException extends BaseException{
    
    public ShellDoesNotExistException(Object sender, Object target){
        super(ShellDoesNotExistException.constructMessage(sender, target));
    }
}
