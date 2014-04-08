/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIPanels;

import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class BasePanel extends javax.swing.JPanel{
    
    public void fillListModel(List list, DefaultListModel listModel){
        for(int i=0; i < list.size(); i++){
            listModel.addElement(list.get(i));
        }
    }
    
}
