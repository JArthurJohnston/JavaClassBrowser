
package UIModels;

import ApplicationModels.*;
import Models.ProjectModel;
import UIShells.NewProjectShell;
import javax.management.BadStringOperationException;

/**
 * Any Error throwing logic should go in the UIModels
 * 
 * @author Arthur
 */
public class NewProjectShellModel extends BaseUIModel{
    private NewProjectShell shell;
    
    public NewProjectShellModel(MainModel current){
        this.applicationMain = current;
        NewProjectShell.showShellWithModel(this);
    }
    
    
    public void addProject(String newProjectName) throws BadStringOperationException{
       if(applicationMain.okToAddProjectWithName(newProjectName))
           applicationMain.addProject(new ProjectModel(newProjectName));
       else
           throw new BadStringOperationException(newProjectName);
    }
    
}
