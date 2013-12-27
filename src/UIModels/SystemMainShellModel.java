/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import MainBase.MainApplication;
import Models.*;
import UIShells.SystemMainShell;
import java.util.ArrayList;
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
        this.fillLists();
        shell = new SystemMainShell(this);
    }
    
    private void fillListModel(ArrayList newList, DefaultListModel myList){
        for(int i=0; i<newList.size(); i++){
            myList.addElement(newList.get(i));
        }
    }
    
    /**
     * should only be called on startup in the constructor
     * otherwise you should be filling/re-filling lists separately
     */
    private void fillLists(){
        this.fillListModel(application.getProjects(), projectList);
        if(application.getSelectedProject() != null){
            this.fillListModel(application.getSelectedProject().packageList(), packageList);
        }else{
            packageList = new DefaultListModel();
        }if(application.getSelectedPackage() != null){
            this.fillListModel(application.getSelectedPackage().classList(), classList);
        }else{
            classList = new DefaultListModel();
        }if(application.getSelectedClass() != null){
            this.fillListModel(application.getSelectedClass().methods(), methodList);
        }else{
            methodList = new DefaultListModel();
        }
        
    }
    
    public DefaultListModel getProjectList(){
        return projectList;
    }
    public DefaultListModel getPackageList(){
        return packageList;
    }
    public DefaultListModel getClassList(){
        return classList;
    }
    public DefaultListModel getMethodList(){
        return methodList;
    }
    
    
    public void openNewProjectShell(){
        new NewProjectShellModel(application);
    }
    
    public void projectAdded(ProjectModel newProject){
        projectList.addElement(newProject);
    }
    
    public void setSelectedPackage(PackageModel aPackage){
        application.setSelectedPackage(aPackage);
        this.newPackageSelected();
    }
    public void setSelectedProject(ProjectModel aProject){
        application.setSelectedProject(aProject);
        this.newProjectSelected();
    }
    public void setSelectedClass(ClassModel aClass){
        application.setSelectedClass(aClass);
        this.newClassSelected();
    }
    public void setSelectedMethod(MethodModel aMethod){
        application.setSelectedMethod(aMethod);
    }
    
    
    public void newProjectSelected(){
        packageList.clear();
        this.fillListModel(application.getSelectedProject().packageList(), packageList);
        classList.clear();
        methodList.clear();
    }
    public void newPackageSelected(){
        classList.clear();
        this.fillListModel(application.getSelectedPackage().classList(), classList);
        methodList.clear();
    }
    public void newClassSelected(){
        methodList.clear();
        this.fillListModel(application.getSelectedClass().methods(), methodList);
    }
}
