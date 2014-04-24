/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Types;

import java.util.EnumSet;

/**
 *
 * @author Arthur
 */
public enum ScopeType {
    NONE, PUBLIC, PRIVATE, PROTECTED;
    
    public boolean contains(Object o){
        return EnumSet.allOf(ScopeType.class).contains(o);
    }
}
