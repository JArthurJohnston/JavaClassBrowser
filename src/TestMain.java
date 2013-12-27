
import Exceptions.NameAlreadyExistsException;
import Models.*;
import MainBase.MainApplication;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arthur
 */
public class TestMain {
    private static MainApplication main;
    private static ProjectModel project1;
    private static ProjectModel project2;
    private static PackageModel aPackage;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        main = new MainApplication();
        try {
            main.addProject("AProject");
            project1 = main.addProject("TestProject");
            main.addProject("NewProject");
            project2 = main.addProject("SomeProject");
            aPackage = project1.addPackage("NewPackage");
            project1.addPackage("APackage");
            project1.addPackage("SomePackage");
            project2.addPackage("NewPackage");
            project2.addPackage("APackage");
            project2.addPackage("SomePackage");
            
        } catch (NameAlreadyExistsException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
/*
 * Notes
 */