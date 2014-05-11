/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import MainBase.SortedList;
import Models.*;
import UIModels.BrowserUIController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Arthur
 */
public class BasePanel extends javax.swing.JPanel{
    
    protected BrowserUIController controller;
    
    public BasePanel(){}
    
    public void setModel(BrowserUIController aController){
        this.controller = aController;
        for(BasePanel bp : this.myPanels())
            bp.setModel(aController);
    }
    
    protected SortedList<BasePanel> myPanels(){
        return new SortedList();
    }
    
    protected DefaultListModel getListModel(JList aList){
        return (DefaultListModel)aList.getModel();
    }
    
    protected void clearPanels(){
        for(BasePanel bp : this.myPanels())
            bp.clear();
    }
    
    protected void clear(){
        //subclass responsibility
    }
    
    protected ListSelectionListener setUpListener(final JList aList){
        return new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    updateModel(aList);
                }
            }
        };
    }
    
    protected void setUpJList(JList aList){
        aList.setModel(new DefaultListModel());
        aList.getSelectionModel().addListSelectionListener(this.setUpListener(aList));
    }
    
    protected void updateModel(JList aList){
        controller.setSelected((BaseModel)aList.getSelectedValue());
    }
    
    public BaseModel getSelected(){
        return null;
    }
    
    protected void clearOtherPanels(BasePanel aPanel){
        for(BasePanel bp : this.myPanels())
            if(bp != aPanel)
                bp.clear();
    }
    
    protected void setUpRightClickListener(final JComponent aComponent, final JPopupMenu aMenu){
        aComponent.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(e.isPopupTrigger())
                    aMenu.show(aComponent, e.getX(), e.getY());
            }
            @Override
            public void mouseReleased(MouseEvent e){
                if(e.isPopupTrigger())
                    aMenu.show(aComponent, e.getX(), e.getY());
            }
        });
    }
    
    
    public void selectionChanged(BaseModel aModel){
        if(aModel == null)
            return;
        for(BasePanel bp: this.myPanels())
            bp.selectionChanged(aModel);
    }
    
    public void modelAdded(BaseModel newModel){
        for(BasePanel bp : this.myPanels())
            bp.modelAdded(newModel);
    }
    public void modelChanged(BaseModel newModel){
        for(BasePanel bp : this.myPanels())
            bp.modelChanged(newModel);
    }
    public void modelRemoved(BaseModel newModel){
        for(BasePanel bp : this.myPanels())
            bp.modelRemoved(newModel);
    }
    
    protected void refresh(){
        for(BasePanel p : myPanels())
            p.refresh();
    }
    
}
