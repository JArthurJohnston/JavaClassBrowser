/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers;

/**
 *
 * @author arthur
 */
public class BaseParseTree {
    protected String source;
    
    protected String source(){
        return source;
    }
    
    protected String getSegment(int startIndex, int endIndex){
        return new String(this.source().substring(startIndex, endIndex));
    }

    protected int skipWhiteSpaces(int index) {
        while (this.source().charAt(++index) == ' '
                && index < this.source().length());
        return index;
    }
    
}
