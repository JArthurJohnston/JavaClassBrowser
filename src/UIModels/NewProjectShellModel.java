
package UIModels;

import Exceptions.NameAlreadyExistsException;
import MainBase.MainApplication;
import Models.ProjectModel;
import UIShells.NewProjectShell;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.BadStringOperationException;

/**
 * Any Error throwing logic should go in the UIModels
 * 
 * @author Arthur
 */
public class NewProjectShellModel extends BaseUIModel{
    
    public NewProjectShellModel(MainApplication current){
        this.application = current;
        shell = new NewProjectShell(this);
    }
    
    public void addProject(String newProjectName){
        try {
            application.addProject(newProjectName);
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(NewProjectShellModel.class.getName()).log(Level.WARNING, null, ex);
        }
    }
    
}
