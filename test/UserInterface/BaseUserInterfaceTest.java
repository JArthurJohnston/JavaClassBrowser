/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import Internal.BaseTest;
import java.awt.Component;
import java.awt.Container;
import static org.junit.Assert.fail;

/**
 *
 * @author arthur
 */
public class BaseUserInterfaceTest extends BaseTest {

    protected Component assertComponentExistsAndGet(Container view, String componentName) {
        for (Component comp : view.getComponents())
            if (comp.getName() != null && comp.getName().equals(componentName))
                return comp;
            else if (comp instanceof Container)
                return assertComponentExistsAndGet((Container) comp, componentName);
        fail("component not found");
        return null;
    }

}
