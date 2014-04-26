/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIShells.Dialogs;

import Internal.BaseTest;
import MainBase.UsefulList;
import Models.ClassModel;
import Models.VariableModel;
import Types.ClassType;
import UIModels.Buffer.VariableModelBuffer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class AddVariableDialogueTest extends BaseTest{
    private AddVariableDialogue dialogue;
    
    public AddVariableDialogueTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        VariableModelBuffer buffer = 
                new VariableModelBuffer(
                        new VariableModel(ClassType.INSTANCE, new ClassModel("int"), "newVariable")
                                .setParent(new ClassModel("ParentClass")));
        dialogue = new AddVariableDialogue(new JFrame(), buffer);
    }
    
    @After
    public void tearDown() {
        dialogue = null;
    }
    
    /**
     * 1: name
     * 2: scope
     * 3: static
     * 4: final
     * 5: type
     * 
     * @return a UsefulList with the dialogues labels
     */
    private UsefulList<JLabel> getLabels(){
        return new UsefulList().addElm((JLabel)this.getVariableFromClass(dialogue, "nameLabel"))
                .addElm((JLabel)this.getVariableFromClass(dialogue, "scopeLabel"))
                .addElm((JLabel)this.getVariableFromClass(dialogue, "staticLabel"))
                .addElm((JLabel)this.getVariableFromClass(dialogue, "finalLabel"))
                .addElm((JLabel)this.getVariableFromClass(dialogue, "typeLabel"));
    }

    @Test
    public void testInitialFields() {
        UsefulList<JLabel> labels = this.getLabels();
        assertEquals("newVariable", labels.get(0).getText());
        assertEquals("NONE",        labels.get(1).getText());
        assertEquals("instance",    labels.get(2).getText());
        assertEquals("No",          labels.get(3).getText());
        assertEquals("int",         labels.get(4).getText());
    }
    
    @Test
    public void testSetTextChangesLabels(){
        JTextField varField = (JTextField)this.getVariableFromClass(dialogue, "newVarField");
        UsefulList<JLabel> labels = this.getLabels();
        varField.setText("private static char x;");
        assertEquals("x",       labels.get(0).getText());
        assertEquals("private", labels.get(1).getText());
        assertEquals("static",  labels.get(2).getText());
        assertEquals("No",      labels.get(3).getText());
        assertEquals("char",    labels.get(4).getText());
    }
    
    @Test
    public void testSaveAndClose(){
        fail("write me");
    }
    
}
