/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase;

import java.util.LinkedList;
import java.util.List;

/**
 * UsefulList, more dynamic list.
 * UsefulList will return itself when added to or removed from.
 * allowing the user to chain such calls together.
 * it will also implement methods to add objects in alphabetic order.
 * 
 * @author arthur
 * @param <T>
 */
public class UsefulList<T> extends LinkedList<T>{
    
    /**
     * allows one to chain addElm() calls together.
     * 
     * @param elm
     * @return UsefulList this
     */
    public UsefulList addElm(T elm){
        if(super.add(elm))
            return this;
        return null;
    }
    
    public UsefulList addElements(List<T> elements){
        if(super.addAll(elements))
            return this;
        return null;
    }
    
    public UsefulList addArray(T[] tArray){
        for(T t : tArray)
            this.addElm(t);
        return this;
    }
    
}
