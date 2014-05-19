/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase.Events.ModelEvents;

import MainBase.Events.BaseListener;

/**
 *
 * @author arthur
 */
public interface ModelEventListener extends BaseListener{
    
    void modelAdded(ModelAddedEvent e);
    void modelRemoved(ModelRemovedEvent e);
    void modelChanged(ModelChangedEvent e);
    
}
