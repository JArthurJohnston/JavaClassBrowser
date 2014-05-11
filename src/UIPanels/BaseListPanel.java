/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels;

import Models.BaseModel;
import UIModels.BrowserUIController;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arthur
 */
public class BaseListPanel extends BasePanel {
    
    @Override
    public void setModel(BrowserUIController aController){
        this.controller = aController;
    }
    
    protected DefaultTableModel tableModel(JTable aTable){
        return(DefaultTableModel)aTable.getModel();
    }
    
    private ListSelectionListener rowSelectionListener(){
        return new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    updateModel();
                }
            }
        };
    }
    
    protected void setUpTable(){
        this.table().getSelectionModel()
                .addListSelectionListener(this.rowSelectionListener());
    }
    
    protected void updateModel(){
        this.controller.setSelected(this.getSelected());
    }
    
    
    protected JTable table(){
        return null;
    }
    
    
    public boolean isEmpty(){
        return this.getTableSize() == 0;
    }
    
    public int getTableSize(){
        return this.table().getRowCount();
    }
    
    public BaseModel getValueAt(int index){
        return ((CellModel)this.tableModel(this.table()).getValueAt(index, 0)).getBase();
    }
    
    @Override
    public BaseModel getSelected(){
        return this.getValueAt(this.table().getSelectedRow());
    }
    
    @Override
    public void clear(){
        this.tableModel(this.table()).setRowCount(0);
    }
    
    @Override
    public void modelRemoved(BaseModel aModel){
        if(!aModel.isMethod())
            return;
        this.removeModelRow(this.indexOf(aModel));
    }
    
    private void removeModelRow(int index){
        if(index >= 0)
            this.tableModel(this.table()).removeRow(index);
    }
    
    public boolean contains(BaseModel aModel){
        return this.indexOf(aModel) >= 0;
    }
    
    public int indexOf(BaseModel aModel){
        for(int i = 0; i< this.getTableSize(); i++)
            if(this.getValueAt(i) == aModel)
                return i;
        return -1;
    }
    
    
    
    /**
     * acts as a wrapper. 
     * so that a table's cell can contain both the string it needs to
     * display to the user, and the object to which that string belongs
     */
    protected class CellModel{
        private final BaseModel base;
        private final String label;
        
        public CellModel(String label, BaseModel base){
            this.label = label;
            this.base = base;
        }
        
        @Override
        public String toString(){
            return label;
        }
        public BaseModel getBase(){
            return base;
        }
    }
    
}
