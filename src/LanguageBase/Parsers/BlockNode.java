/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class BlockNode {
    private Parser tree;
    private LinkedList<StatementNode> statements;
    
    private BlockNode(){
        statements = new LinkedList();
    }
    
    public BlockNode(Parser tree){
        this();
        this.tree = tree;
    }
    
    public Parser getTree(){
        return tree;
    }
    
    public LinkedList<StatementNode> getStatements(){
        return statements;
    }
    
}
