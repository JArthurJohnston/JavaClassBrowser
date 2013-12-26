/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import MainBase.MainApplication;
import Models.*;
import UIShells.SystemMainShell;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class SystemMainShellModel extends BaseUIModel{
    private DefaultListModel<ProjectModel> projectList;
    private DefaultListModel<PackageModel> packageList;
    private DefaultListModel<ClassModel> classList;
    private DefaultListModel<MethodModel> methodList;
    
    
    public SystemMainShellModel(MainApplication current){
        this.application = current;
        projectList = new DefaultListModel();
        this.fillListModel();
        shell = new SystemMainShell(this);
    }
    
    private void fillProjectsList(){
        for(ProjectModel p : application.projects()){
            projectList.addElement(p);
        }
    }
    private void fillPackageList(){
        for(PackageModel p : application.projects()){
            projectList.addElement(p);
        }
    }
    private void fillClassList(){
        for(ProjectModel p : application.projects()){
            projectList.addElement(p);
        }
    }
    private void fillMethodList(){
        for(ProjectModel p : application.projects()){
            projectList.addElement(p);
        }
    }
    
    public DefaultListModel getList(){
        return projectList;
    }
    
    public void openNewProjectShell(){
        new NewProjectShellModel(application);
    }
    
    public void projectAdded(ProjectModel newProject){
        projectList.addElement(newProject);
    }
    public void setSelectedPackage(PackageModel aPackage){
        application.setSelectedPackage(aPackage);
    }
    public void setSelectedProject(ProjectModel aProject){
        application.setSelectedProject(aProject);
    }
    public void setSelectedClass(ClassModel aClass){
        application.setSelectedClass(aClass);
    }
    public void setSelectedMethod(MethodModel aMethod){
        application.setSelectedMethod(aMethod);
    }
    
    public void getPackageList(){
        
    }
}
