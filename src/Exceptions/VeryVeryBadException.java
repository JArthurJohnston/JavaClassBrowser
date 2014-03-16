/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;

/**
 * Every reference to this Should/will be deleted before this is
 * put out into the world. If its ever put out into the world.
 * @author arthur
 */
public class VeryVeryBadException extends BaseException{
    
    public static String constructMessage(Object a, Object b){
        return "Something has gone horribly wrong in: "+ a.toString()+" using "+ b.toString();
    }
    
    public VeryVeryBadException(Object a, Object b){
        super (constructMessage(a,b));
    }
    
}
