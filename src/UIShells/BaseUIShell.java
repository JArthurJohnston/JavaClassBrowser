/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import MainBase.SortedList;
import Models.*;
import UIModels.BaseUIController;
import UIPanels.BasePanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author arthur
 */
public class BaseUIShell extends javax.swing.JFrame {
    
    protected BaseUIController controller;
    
    protected BaseUIShell(){
        setUpClosedWindowListener();
    }
    
    private void setUpClosedWindowListener(){
        //this should also be pushed up.
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosed(WindowEvent e){
                signalClosedAndDispose();
            }
        });
    }
    
    protected BaseUIController controller(){
        return controller;
    }
    
    protected ListSelectionListener setUpListener(final JList aList){
        return new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting())
                    selectionChanged((BaseModel)aList.getSelectedValue());
            }
        };
    }
    
    protected void signalClosedAndDispose(){
        
    } // might just wanna get rid of this?
        
    /**
     * a method for filling list models.
     * I used a traditional for-loop in leu of a for-each loop
     * to make the method a bit more dynamic. this way, you can pass
     * in a list containing any object you want, and it will work.
     * @param list
     * @param listModel 
     */
    public void fillListModel(List list, DefaultListModel listModel){
        for(int i=0; i < list.size(); i++){
            listModel.addElement(list.get(i));
        }
    }
    
    public int yesOrNoMessage(String message){
        return JOptionPane.showConfirmDialog(null, ProjectModel.DELETE_WARNING, "Confirm Delete Project"
                , JOptionPane.YES_NO_OPTION);
    }
    
    protected SortedList<BasePanel> myPanels(){
        return new SortedList();
    }
    public void modelAdded(BaseModel newModel){
        for(BasePanel bp : this.myPanels())
            bp.modelAdded(newModel);
    }
    public void modelChanged(BaseModel aModel){
        for(BasePanel bp : this.myPanels())
            bp.modelChanged(aModel);
    }
    public void modelRemoved(BaseModel aModel){
        for(BasePanel bp : this.myPanels())
            bp.modelRemoved(aModel);
    }
    public void selectionChanged(BaseModel aModel){
        for(BasePanel bp : this.myPanels())
            bp.selectionChanged(aModel);
    }
}
