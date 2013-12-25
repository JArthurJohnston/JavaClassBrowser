
package UIModels;

import Models.ProjectModel;
import UIShells.NewProjectShell;
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
    
    public void addProject(String newProjectName) throws BadStringOperationException{
       if(application.okToAddProjectWithName(newProjectName))
           application.addProject(new ProjectModel(newProjectName));
       else
           throw new BadStringOperationException(newProjectName);
    }
    
}
