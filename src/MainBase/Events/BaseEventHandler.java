/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase.Events;

import MainBase.Events.ModelEvents.ModelEventListener;
import javax.swing.event.EventListenerList;

/**
 *
 * @author arthur
 */
public class BaseEventHandler {
    protected static EventListenerList listeners;
    
    protected BaseEventHandler(){
        
    }
    
    public static int size(){
        if(listeners == null)
            return 0;
        return listeners.getListenerCount();
    }
    
    public static boolean isEmpty(){
        return size() == 0;
    }
    
    protected static EventListenerList getList(){
        if(listeners == null)
            listeners = new EventListenerList();
        return listeners;
    }
    
    public static boolean contains(ModelEventListener listener){
        for(ModelEventListener l : getList().getListeners(ModelEventListener.class))
            if(l == listener)
                return true;
        return false;
    }
    
}
