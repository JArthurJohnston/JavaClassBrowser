/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Types;

/**
 *
 * @author arthur
 */
public enum SyntaxCharacters {
    OPEN_CURLY_BRACKET('{'), CLOSE_CURLY_BRACKET('}'), OPEN_PAREN('('),
    CLOSE_PAREN(')'), PERIOD('.') ,OPEN_BRACKET('['), CLOSE_BRACKET(']'), 
    SEMICOLON(';'), COLON(':'), COMMA(','), PLUS('+'), HYPHEN('-'), 
    ASTERISX('*'), AMPERSAND('&'), PIPE('|'), EXCLAMATION('!');
    
    final Character character;
    
    SyntaxCharacters(final char c){
        this.character = c;
    }
    
    public Character getSyntaxCharacter(){
        return this.character;
    }
}
