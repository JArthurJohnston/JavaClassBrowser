/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIShells;

import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 * a class for any shared methods for all the UI stuff
 * 
 * @author Arthur
 */
public class BaseUIModel extends javax.swing.JFrame{
    
    /**
     * a method for filling list models.
     * I used a traditional for-loop in leu of a for-each loop
     * to make the method a bit more dynamic. this way, you can pass
     * in a list containing any object you want, and it will work.
     * @param list
     * @param listModel 
     */
    public void fillListModel(ArrayList list, DefaultListModel listModel){
        for(int i=0; i < list.size(); i++){
            listModel.addElement(list.get(i));
        }
    }
}
