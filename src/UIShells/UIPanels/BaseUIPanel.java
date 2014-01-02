/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells.UIPanels;

import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class BaseUIPanel extends javax.swing.JPanel{
    
    
    public void fillListModel(ArrayList newList, DefaultListModel myList){
        for(int i=0; i<newList.size(); i++){
            myList.addElement(newList.get(i));
        }
    }
    
}
