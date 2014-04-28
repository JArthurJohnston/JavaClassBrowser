/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Types;

import java.util.LinkedList;

/**
 * TESTING is only used to flag code thats being used for testing purposes
 *  any such code should be removed before launch
 * @author Arthur
 */
public enum ClassType {
    STATIC, INSTANCE;
    
    public static LinkedList<String> getStringValues(){
        LinkedList<String> aList = new LinkedList();
        for(ClassType c : ClassType.values())
            aList.add(c.toString());
        return aList;
    }
}
