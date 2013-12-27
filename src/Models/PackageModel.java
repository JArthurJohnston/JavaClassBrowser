/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Arthur
 */
public class PackageModel extends ProjectModel {
    private ProjectModel parentProject;
    
    public PackageModel(ProjectModel parent, String name){
        this.setUpDataStructures();
        this.parentProject = parent;
        this.name = name;
    }
    
    
}
