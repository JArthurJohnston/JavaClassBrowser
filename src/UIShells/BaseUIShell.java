/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Arthur
 */
public class BaseUIShell extends javax.swing.JFrame {
    private BaseUIShell model;
    
    public BaseUIShell shell(){
        return this;
    }
    public void fillListModel(ArrayList newList, DefaultListModel myList){
        for(int i=0; i<newList.size(); i++){
            myList.addElement(newList.get(i));
        }
    }
}
