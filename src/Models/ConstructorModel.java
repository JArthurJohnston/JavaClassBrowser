/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Types.ScopeType;

/**
 *
 * @author Arthur
 */
public class ConstructorModel extends MethodModel{
    
    public ConstructorModel(ClassModel parent){
        this.project = parent.getProject();
        this.parent = parent;
        this.name = parent.name();
        this.scope = ScopeType.PUBLIC;
    }
}
