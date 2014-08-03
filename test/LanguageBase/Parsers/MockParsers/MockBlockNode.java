/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.MockParsers;

import LanguageBase.Parsers.BlockParser;
import LanguageBase.Parsers.Nodes.BlockNode;
import LanguageBase.Parsers.Nodes.StatementNode;
import LanguageBase.Parsers.Parser;
import Models.BaseModel;

/**
 *
 * @author arthur
 */
public class MockBlockNode extends BlockNode {
    String source;
    BaseModel model;
    
    public MockBlockNode(){
    }
    
    @Override
    public String source(){
        return source;
    }
    
    public StatementNode getStatement(String source){
        this.source = source;
        return new StatementNode(this, 0, source.length());
    }
    
    @Override
    public Parser getTree() {
        return new MockBlockParser();
    }
    
    public boolean hasModel(){
        return !(model == null);
    }
    
}
