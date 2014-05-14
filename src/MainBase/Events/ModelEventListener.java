/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase.Events;

import java.util.EventListener;

/**
 *
 * @author arthur
 */
public interface ModelEventListener extends EventListener{
    
    void modelAdded(ModelAddedEvent e);
    void modelRemoved(ModelRemovedEvent e);
    void modelChanged(ModelChangedEvent e);
    
}
