/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import Models.BaseModel;
import UIModels.BrowserUIModel;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Arthur
 */
public class BasePanel extends javax.swing.JPanel{
    
    protected BrowserUIModel model;
    
    public void setModel(BrowserUIModel aModel){
        this.model = aModel;
        this.setUpLists();
    }
    
    public void fillListModel(List list, DefaultListModel listModel){
        for(int i=0; i < list.size(); i++){
            listModel.addElement(list.get(i));
        }
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
    
    protected void setUpLists(){
        for(JList jl : this.myLists())
            this.setUpJList(jl);
    }
    
    protected void setUpJList(JList aList){
        aList.setModel(new DefaultListModel());
        aList.getSelectionModel().addListSelectionListener(this.setUpListener(aList));
    }
    
    protected void updateModel(JList aList){
        model.setSelected((BaseModel)aList.getSelectedValue());
        clearOtherLists(aList);
    }
    
    protected void clearOtherLists(JList aList){
        for(JList jl : this.myLists())
            if(jl != aList)
                jl.clearSelection();
    }
    
    protected LinkedList<JList> myLists(){
        return new LinkedList();
    }
    
}
