/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase;

import Exceptions.VeryVeryBadException;
import java.util.LinkedList;
import java.util.List;

/**
 * SortedList, more dynamic list.
 * SortedList will return itself when added to or removed from.
 allowing the user to chain such calls together.
 it will also implement methods to add objects in alphabetic order.
 * 
 * @author arthur
 * @param <T>
 */
public class SortedList<T> extends LinkedList<T>{
    
    /**
     * allows one to chain addElm() calls together.
     * 
     * @param elm
     * @return SortedList this
     */
    public SortedList addElm(T elm){
        if(super.add(elm))
            return this;
        return null;
    }
    
    public SortedList addElements(List<T> elements){
        if(super.addAll(elements))
            return this;
        return null;
    }
    
    public SortedList addArray(T[] tArray){
        for(T t : tArray)
            this.addElm(t);
        return this;
    }
    
    public T onlyElement() throws VeryVeryBadException{
        if(this.isEmpty())
            throw new VeryVeryBadException(this, "Collection is empty");
        if(this.size() == 1)
            return this.getFirst();
        
        throw new VeryVeryBadException(this, "More than one element");
        //replace me with a more reasonable exception please
    }
    
}