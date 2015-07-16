/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Basiques;

import java.util.ArrayList;

/**
 *
 * @author Aurélien Vialon
 */
public class Types extends ArrayList<Type>
{
    public Types ()
    {
         super();      
    }
    public Types (Type t)
    {
        super();
        this.add(t);
    }
    public void ajt(Type t)
    {
        if(!this.contains(t))
            this.add(t);
    }
}
