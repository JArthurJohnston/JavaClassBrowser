/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LanguageBase.Parsers.Stacks;

import Exceptions.BaseException;
import java.util.LinkedList;

/**
 *
 * @author arthur
 */
public class BracketStack extends LinkedList<String>{
        private static final String[] openers = new String[]{"(","{","{{"};
        private static final String[] closers = new String[]{")","}","}}"};
        private String lastSymbol;

        public boolean isLastSymbol(String symbol) {
            if (lastSymbol == null)
                return false;
            return lastSymbol.compareTo(symbol) == 0;
        }

        public boolean peek(String symbol) {
            if (this.isEmpty())
                return false;
            return this.getLast().compareTo(symbol) == 0;
        }
        
        /**
         * the fact that a java linkedList cant iterate in 
         * reverse is fucking stupid.
         * @return 
         */
        public boolean isSingleStatementBlock(){
            for(int i = this.size()-1; i >=0; i--)
                if(this.get(i).compareTo("{") ==0)
                    return false;
                else if(this.get(i).compareTo("{{")==0)
                    return true;
            return false;
        }
        
        public void processSymbol(String symbol) throws StackException{
            if(isOpener(symbol))
                this.open(symbol);
            else if(isCloser(symbol))
                this.close(symbol);
            else
                throw new StackException(symbol);
        }
        
        public void open(String openSymbol) throws StackException {
            if (this.isValidSymbol(openSymbol)) {
                this.add(openSymbol);
                lastSymbol = openSymbol;
            } else
                throw new StackException(openSymbol);
        }

        public void close(String closeSymbol) throws StackException {
            if (this.isValidSymbol(closeSymbol))
                if (matchSymbol(closeSymbol)) {
                    lastSymbol = closeSymbol;
                    this.removeLast();
                } else
                    throw new StackException(closeSymbol);
        }

        private boolean matchSymbol(String symbol) {
            if (this.isEmpty())
                return false;
            if (symbol.compareTo("}") == 0)
                return this.getLast().compareTo("{") == 0;
            if (symbol.compareTo("}}") == 0)
                return this.getLast().compareTo("{{") == 0;
            if (symbol.compareTo(")") == 0)
                return this.getLast().compareTo("(") == 0;
            return false;
        }

        private boolean isOpen() {
            return this.isOpenBracket() || this.isOpenParen();
        }

        public boolean isOpenParen() {
            if (this.isEmpty())
                return false;
            return this.getLast().compareTo("(") == 0;
        }

        public boolean isOpenBracket() {
            if (this.isEmpty())
                return false;
            return this.getLast().compareTo("{") == 0
                    || this.getLast().compareTo("{{") == 0;
        }

        private boolean isValidSymbol(String symbol) {
            return isOpener(symbol) || isCloser(symbol);
        }
        
        private boolean isOpener(String symbol){
            return isSymbolInArray(openers, symbol);
        }
        
        private boolean isCloser(String symbol){
            return isSymbolInArray(closers, symbol);
        }
        
        private boolean isSymbolInArray(String[] array, String symbol){
            for (String validSymbol : array)
                if (symbol.compareTo(validSymbol) == 0)
                    return true;
            return false;
        }
        
        
    public class StackException extends BaseException {
        public StackException(String c){
            super("Stack error with char: " + c);
        }
        public StackException(char c){
            super("Stack error with char: " + c);
        }
    }
    
}
