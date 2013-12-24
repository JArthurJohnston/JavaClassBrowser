
import Models.ProjectModel;
import UIModels.MainModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arthur
 */
public class TestMain {
    private static MainModel main;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        main = new MainModel();
        main.addProject(new ProjectModel("a project"));
        main.addProject(new ProjectModel("another project"));
        main.openMainApplicationShell();
    }
}
/*
 * Notes
 */