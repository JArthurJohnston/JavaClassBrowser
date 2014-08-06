/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.EditPanels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author arthur
 */
public class SourceEditPanel extends BaseEditPanel {
    private int sourceEditCursorPosition;
    private StyleContext sc = StyleContext.getDefaultStyleContext();
    private DefaultStyledDocument doc = new DefaultStyledDocument(sc);
    private Style sourceStyle;
    private Style defaultStyle;
    
    private boolean isDeleting;

    /**
     * Creates new form SourceEditPanel
     */
    public SourceEditPanel() {
        initComponents();
        this.isDeleting = false;
        this.sourceEditPane.setDocument(doc);
        this.defaultStyle = this.setUpDefaultStyle();
        this.sourceStyle = this.setUpSourceStyle();
        try {
            doc.insertString(0, "just some test text.", null);
            doc.setCharacterAttributes(0, 5, sourceStyle, false);
        } catch (BadLocationException ex) {
            System.err.println("Exception when constructing doc" + ex);
            System.exit(1);
        }
        this.setUpInputAndActionMaps();
    }
    
    private Style setUpSourceStyle(){
        Style ss = sc.addStyle("JavaReservedWords", null);
        ss.addAttribute(StyleConstants.Foreground, Color.blue);
        ss.addAttribute(StyleConstants.Bold, true);
        return ss;
    }
    
    private Style setUpDefaultStyle(){
        return sc.addStyle("default", null);
    }
    
    private void setUpInputAndActionMaps(){
        this.addInputMap(KeyStroke.getKeyStroke("SPACE"),
                colorCode(), "colorCode");
        this.addInputMap(KeyStroke.getKeyStroke("BACK_SPACE"),
                blackenCode(), "deleteCode");
        
    }
    
    private void addInputMap(KeyStroke stroke, Action a, String label){
        this.sourceEditPane.getInputMap()
                .put(stroke, label);
        this.sourceEditPane.getActionMap().put(label, a);
    }
    
    private Action colorCode(){
        return new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                colorCodeSegment();
            }
        };
    }
    
    private Action blackenCode(){
        return new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                blackenCodeOnDelete();
            }
        };
    }
    
    private void colorCodeSegment(){
        this.colorText(this.sourceEditCursorPosition, 
                this.sourceEditPane.getCaretPosition(), sourceStyle);
        this.sourceEditCursorPosition = this.sourceEditPane.getCaretPosition();
    }
    
    private void colorText(int start, int end, Style style){
        doc.setCharacterAttributes(start, end, style, false);
    }
    
    private void blackenCodeOnDelete(){
        if(!isDeleting){
            isDeleting = true;
            int position = this.sourceEditPane.getCaretPosition();
            while(!this.isDelimeter(--position));
            this.colorText(sourceEditCursorPosition, 
                    this.sourceEditPane.getCaretPosition(), sourceStyle);
            isDeleting = false;
        }
    }
    
    private void applyStylesToSource(){
        for(int i=0; i < sourceEditPane.getCaretPosition(); i++){
            
        }
    }
    
    /*
    how about this, whenever the user types a delemiter, it triggers a method that will
    loop through the whole string and colors certain words. hitting the backspace will kill any currently
    running loops.
    might want to do this in a seperate thread or process.
    */
    
    private boolean isDelimeter(int index){
        if(index <=0)
            return false;
        char c = this.sourceEditPane.getText().charAt(index);
        return c == ' ' ||
                c == '.' ||
                c == '(' ||
                c == ')' ||
                c == '{' ||
                c == '}';
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        sourceEditPane = new javax.swing.JTextPane();

        setLayout(new java.awt.BorderLayout());

        jScrollPane2.setViewportView(sourceEditPane);

        add(jScrollPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane sourceEditPane;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void applyChanges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void revertChanges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}