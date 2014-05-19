/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainBase.Events;

import java.util.EventObject;

/**
 *
 * @author arthur
 */
public class BaseEvent extends EventObject{

    public BaseEvent(Object source) {
        super(source);
    }
    
}
