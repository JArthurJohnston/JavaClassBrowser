/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import java.util.ArrayList;

/**
 *
 * @author arthur
 */
public class MethodSignature {
        private final String name;
        private final ArrayList<ClassModel> arguments;
        
        public MethodSignature(String name){
            this.arguments = new ArrayList();
            this.name = name;
        }
        
        public MethodSignature(MethodModel aMethod){
            this(aMethod.name());
            this.initArgs(aMethod);
        }
        
        private void initArgs(MethodModel aMethod){
            for(VariableModel var : aMethod.arguments())
                arguments.add(var.getObjectType());
        }
        
        public String name(){
            return this.name;
        }
        
        public ArrayList<ClassModel> arguments(){
            return arguments;
        }
        
        public boolean equals(MethodSignature anotherMethod){
            if(!this.name.equals(anotherMethod.name()))
                return false;
            if(this.arguments.size() != anotherMethod.arguments().size())
                return false;
            for(int i=0;i< arguments.size();i++){
                if(arguments.get(i) != anotherMethod.arguments().get(i))
                    return false;
            }
            return true;
        }
}
