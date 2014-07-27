/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.MockParsers;

import LanguageBase.Parsers.BlockNode;
import LanguageBase.Parsers.StatementNode;

/**
 *
 * @author arthur
 */
public class MockBlockNode extends BlockNode {
    String source;
    
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
    
}
