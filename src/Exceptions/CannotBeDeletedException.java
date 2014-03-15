/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;

import Models.PackageModel;
import Models.ProjectModel;

/**
 *
 * @author arthur
 */
public class CannotBeDeletedException extends BaseException{
    public CannotBeDeletedException(ProjectModel attemptedDelete, String message){
        super(constructMessage(attemptedDelete, message));
    }
    
    public static String constructMessage(PackageModel attemptedDelete, String message){
        return attemptedDelete.name() + " Cannot be deleted because: "+ message;
    }
}
