/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells;

import MainBase.UsefulList;
import Models.*;
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
    
    
    protected ListSelectionListener setUpListener(final JList aList){
        return new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting())
                    selectionChanged((BaseModel)aList.getSelectedValue());
            }
        };
    }
    
    protected void signalClosedAndDispose(){ } // might just wanna get rid of this?
        
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
    
    protected UsefulList<BasePanel> myPanels(){
        return new UsefulList();
    }
    
    public void modelAdded(BaseModel newModel){}
    public void modelChanged(BaseModel newModel){}
    public void modelRemoved(BaseModel newModel){}
    
    
    public void selectionChanged(BaseModel aClass){}
    
}
