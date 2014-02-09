/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Arthur
 */
public class NothingSelectedException extends BaseException{
    
    public NothingSelectedException(String requestedSelection){
        super(constructMessage(requestedSelection));
    }
    
    private static String constructMessage(String requestedSelection){
        return "No " + requestedSelection + " has been selected.";
    }
}
