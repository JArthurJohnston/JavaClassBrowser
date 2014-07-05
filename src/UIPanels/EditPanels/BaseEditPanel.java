/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.EditPanels;

/**
 *
 * @author arthur
 */
public abstract class BaseEditPanel  extends javax.swing.JPanel {
    
    protected abstract void applyChanges();
    protected abstract void revertChanges();
}
