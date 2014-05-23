/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UIPanels.TreePanels.Nodes;

import Exceptions.AlreadyExistsException;
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
    private PackageNode node;
    
    public PackageNodeTest() {
    }
    
    @Before
    public void setUp() {
        treeHash = new HashMap();
    }
    
    @After
    public void tearDown() {
        treeHash = null;
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
        ProjectModel aProject = this.setUpProject();
        PackageModel aPackage = aProject.getPackageList().getFirst();
        node = new PackageNode(aPackage, treeHash);
        this.verifyNodeSize(1);
        assertEquals(aPackage, node.getModel());
    }
    
    @Test
    public void testRoot(){
        node = new PackageNode(this.setUpProject().getAllPackage(), treeHash);
        this.verifyNodeSize(7);
        //5 packages + the default package + the all package
    }
    
    @Test
    public void testRemove(){
        this.setUpProject();
        PackageModel aModel = project.findPackage("some package");
        PackageModel packageToBeRemoved = project.findPackage("some other package");
        node = new PackageNode(aModel, treeHash);
        PackageNode removedNode = node.addNode(new PackageNode(packageToBeRemoved, treeHash));
        this.verifyNodeSize(2);
        node.remove(removedNode);
    }
    
    @Test
    public void testAdd(){
        node = new PackageNode(this.setUpProject().getAllPackage(), treeHash);
        PackageNode aNode = null;
        try {
            aNode = new PackageNode(project.getDefaultPackage()
                    .addPackage(new PackageModel("some random package")), treeHash);
            this.verifyNodeSize(8);
        } catch (AlreadyExistsException ex) {
            fail(ex.getMessage());
        }
        aNode.remove(treeHash);
        this.verifyNodeSize(7);
    }
    
}
