/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase.Events;

import javax.swing.event.EventListenerList;

/**
 * 
 * @author arthur
 */
public class ModelEventHandler {
    private static EventListenerList listeners;
    
    private ModelEventHandler(){
        //so that it cannot be instantiated
    }
    
    public static void addModelListener(ModelEventListener l){
        getList().add(ModelEventListener.class, l);
    }
    public static void removeModelListener(ModelEventListener l){
        listeners.remove(ModelEventListener.class, l);
        if(isEmpty())
            listeners = null;
    }
    
    private static EventListenerList getList(){
        if(listeners == null)
            listeners = new EventListenerList();
        return listeners;
    }
    
    public static void fireEvent(BaseModelUpdatedEvent e){
        for(ModelEventListener l : getList().getListeners(ModelEventListener.class)){
            if(e.isAdd())
                l.modelAdded((ModelAddedEvent)e);
            if(e.isChange())
                l.modelChanged((ModelChangedEvent)e);
            if(e.isRemove())
                l.modelRemoved((ModelRemovedEvent)e);
        }
    }
    
    public static int size(){
        if(listeners == null)
            return 0;
        return listeners.getListenerCount();
    }
    
    public static boolean isEmpty(){
        return size() == 0;
    }
}
