/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Exceptions.AlreadyExistsException;
import Models.BaseModel;
import Models.PackageModel;
import Models.ProjectModel;
import java.util.HashMap;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author arthur
 */
public class PackageNodeTest extends BaseNodeTest{
    
    private ProjectModel project;
    private HashMap<BaseModel, ModelNode> testHash;
    private PackageNode node;
    
    public PackageNodeTest() {
    }
    
    @Before
    public void setUp() {
        testHash = new HashMap();
        node = new PackageNode(this.setUpProject().getAllPackage(), testHash);
    }
    
    @After
    public void tearDown() {
        testHash = null;
        node = null;
    }
    
    @Override
    protected PackageNode node(){
        return node;
    }
    
    private ProjectModel setUpProject(){
        project = new ProjectModel("a project");
        try {
            project.addPackage(new PackageModel("a package"));
            PackageModel aPackage = project.addPackage(new PackageModel("another package"));
            project.addPackage(new PackageModel("and another package"));
            aPackage.addPackage(new PackageModel("some package"));
            aPackage.addPackage(new PackageModel("some other package"));
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        return project;
    }
    

    @Test
    public void testInit(){
        this.verifyNodeSize(6);
    }
    
}
