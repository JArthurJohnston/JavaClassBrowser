/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Types;

import java.util.LinkedList;

/**
 *
 * @author Arthur
 */
public enum ScopeType {
    NONE, PUBLIC, PRIVATE, PROTECTED;
    
    public static LinkedList<String> getStringValues(){
        LinkedList<String> aList = new LinkedList();
        for(ScopeType s : ScopeType.values())
            aList.add(s.toString());
        return aList;
    }
}
