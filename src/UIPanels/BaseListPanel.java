/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels;

import Models.BaseModel;
import UIModels.BrowserUIModel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arthur
 */
public class BaseListPanel extends BasePanel{
    
    @Override
    public void setModel(BrowserUIModel aModel){
        this.model = aModel;
    }
    
    protected DefaultTableModel tableModel(JTable aTable){
        return(DefaultTableModel)aTable.getModel();
    }
    
    protected ListSelectionListener rowSelectionListener(){
        return new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    updateModel();
                }
            }
        };
    }
    
    protected void updateModel(){
        this.model.setSelected(this.getSelected());
    }
    
    protected BaseModel getSelected(){
        return null;
    }
    
}
