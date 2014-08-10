/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Dialogs;

import Models.BaseModel;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author arthur
 */
public enum OpenDialog {

    NEW_METHOD(new Action() {

        @Override
        public Object getValue(String key) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void putValue(String key, Object value) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setEnabled(boolean b) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isEnabled() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    });

    private final Action dialog;

    private OpenDialog(Action dialog) {
        this.dialog = dialog;
    }

    public void openDialogUsing(BaseModel aModel) {

    }

    public class NewMethodAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
        }

    }
}
