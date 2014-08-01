/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

import Exceptions.DoesNotExistException;
import Models.BaseModel;

/**
 *
 * @author arthur
 */
public class SourceReference {
    private final int startIndex, endIndex;
    private final Parser tree;
    
    public SourceReference(Parser tree, final int start, final int end){
        this.startIndex = start;
        this.endIndex = end;
        this.tree = tree;
    }
    
    public Parser getTree(){
        return tree;
    }
    
    private String source(){
        return this.tree.source();
    }
    
    public String getSource(){
        return this.source().substring(this.startIndex, this.endIndex).trim();
    }
    
    public BaseModel getModel() throws DoesNotExistException {
        if(this.getTree().hasModel())
            return this.getTree().getProject().findClass(this.getSource());
        return null;
    }
}
