/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

/**
 *
 * @author arthur
 */
public class AbstractMethodModel extends MethodModel{
    
    @Override
    public String getMethodBody(){
        return"{}";
    }
    
    @Override
    public String getSource(){
        return "";
    }
    
    @Override
    public String getSignarureString(){
        return this.scopeString() + 
                "abstract"+
                    this.returnType.toString().toLowerCase() +" "+ 
                        this.name();
    }
    
    @Override
    public boolean isAbstract(){
        return true;
    }
    
}
