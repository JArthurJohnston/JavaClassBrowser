
import Models.*;
import MainBase.MainApplication;

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
    private static ProjectModel project;
    private static ProjectModel project2;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        project = new ProjectModel("Test Project");
        PackageModel testPackage = project.addPackage(new PackageModel(project, "TestPackage"));
        testPackage.addClass(new ClassModel(testPackage, "TestClass"));
        testPackage.addClass(new ClassModel(testPackage, "TestClass2"));
        testPackage.addClass(new ClassModel(testPackage, "TestClass3"));
        
        
        PackageModel somePackage = project.addPackage(new PackageModel(project, "SomePackage"));
        somePackage.addClass(new ClassModel(testPackage, "SomeClass"));
        somePackage.addClass(new ClassModel(testPackage, "SomeClass2"));
        somePackage.addClass(new ClassModel(testPackage, "SomeClass3"));
        
        main = new MainApplication();
        main.addProject(new ProjectModel("a project"));
        main.addProject(new ProjectModel("another project"));
        main.addProject(project);
    }
}
/*
 * Notes
 */