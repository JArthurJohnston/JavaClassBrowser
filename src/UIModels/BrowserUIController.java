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
    private PackageModel selectedPackage;
    private BaseModel selectedModel;
    private ClassModel selectedClass;
    private ClassNode allClassNode;
    private PackageNode allPackageNode;
    
    /**
     * Constructor for testing only
     */
    public BrowserUIController(){}
    
    public BrowserUIController(MainApplication main){
        super(main);
        shell = new SystemBrowserShell(this);
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
        if(selectedClass == null)
            if(!selectedProject.getClassList().isEmpty())
                selectedClass = selectedProject.getClassList().getFirst();
        return this.selectedClass;
    }
    
    public PackageModel getSelectedPackage(){
        if(selectedPackage == null)
            selectedPackage = selectedProject.getDefaultPackage();
        return selectedPackage;
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
        if(aModel.isClass())
            selectedClass = (ClassModel)aModel;
        if(aModel.isPackage())
            selectedPackage = (PackageModel)aModel;
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
