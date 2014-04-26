/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells.Dialogs;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author arthur
 */
public class BaseDialogue extends javax.swing.JDialog  {
    
    public BaseDialogue (java.awt.Frame parent, boolean modal){
        super(parent, modal);
    }
    
    
    protected DocumentListener setUpDocListener(){
        return new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                onDocumentChanged();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                onDocumentChanged();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        };
    }
    
    protected void onDocumentChanged(){}
}
