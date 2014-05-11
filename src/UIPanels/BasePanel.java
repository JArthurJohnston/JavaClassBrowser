/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import MainBase.SortedList;
import Models.BaseModel;
import UIModels.BrowserUIController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;

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
    
    protected void clearPanels(){
        for(BasePanel bp : this.myPanels())
            bp.clear();
    }
    
    protected void clear(){
        //subclass responsibility
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
