/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import Exceptions.AlreadyExistsException;
import MainBase.MainApplication;
import Models.*;
import Types.ClassType;
import UIModels.Buffer.VariableModelBuffer;
import UIPanels.TreePanels.Nodes.*;
import UIShells.Dialogs.AddVariableDialogue;
import UIShells.SystemBrowserShell;
import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public class BrowserUIController extends BaseUIController{
    private SystemBrowserShell shell;
    private BaseModel selectedModel;
    private ClassNode allClassNode;
    private PackageNode allPackageNode;
    
    /**
     * Constructor for testing only
     */
    public BrowserUIController(){}
    
    public BrowserUIController(MainApplication main){
        super(main);
        this.setUpFromMain();
        shell = new SystemBrowserShell(this);
    }
    
    protected BrowserUIController(ProjectModel aProject){
        super(aProject.getMain());
        this.setUpFromMain();
    }
    
    private void setUpFromMain(){
        if(main.getSelectedProject() != null){
            this.selectedModel = main.getSelectedProject().getAllPackage();
        }
    }
    
    @Override
    protected SystemBrowserShell shell(){
        return shell;
    }
    
    public SystemBrowserShell getShell(){
        if(shell == null)
            shell = new SystemBrowserShell(this);
        return shell;
    }
    
    public ClassModel getSelectedClass(){
        //needs to be refactored. just ask the shell for its selected class
        if(selectedModel.isClass())
            return (ClassModel)selectedModel;
        return null;
    }
    
    public MethodModel getSelectedMethod(){
        if(selectedModel.isMethod())
            return (MethodModel)selectedModel;
        return null;
    }
    
    public VariableModel getSelectedVariable(){
        if(selectedModel.isMethod())
            return (VariableModel)selectedModel;
        return null;
    }
    
    public PackageModel getSelectedPackage(){
        if(selectedModel.isPackage())
            return (PackageModel)selectedModel;
        return null;
    }
    
    public LinkedList<PackageModel> getPackages(){
        return selectedProject.getPackageList();
    }
    
    public void addPackage(PackageModel aPackage) throws AlreadyExistsException{
        selectedProject.addPackage(aPackage);
    }
    
    public ClassModel addClass(ClassModel newClass) throws Exception{
        if(this.getSelected().isProject() || 
                this.getSelected().isMethod() ||
                this.getSelected().isVariable())
            throw new Exception("Class cannot be added to "+ this.getSelected().name());
        if(this.getSelected().isPackage())
            return ((PackageModel)this.getSelected()).addClass(newClass);
        if(this.getSelected().isClass())
            return ((ClassModel)this.getSelected()).addClass(newClass);
        return null;
    }
    
    @Override
    public void setSelected(BaseModel aModel){
        if(aModel == null)
            return;
        selectedModel = aModel;
        shell.selectionChanged(aModel);
    }
    
    public BaseModel getSelected(){
        return selectedModel;
    }
    
    public VariableModelBuffer addVariableBuffer(ClassType aType){
        return new VariableModelBuffer(
                new VariableModel(
                        aType, 
                        new ClassModel("Object"), 
                        "newVariable").setParent(selectedClass)
        );
    }
    
    public AddVariableDialogue openAddVariable(){
        return new AddVariableDialogue(shell, this.addVariableBuffer(shell.getSelectedVarType()));
    }
}
