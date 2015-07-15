/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.SelfMadeEngine.Basiques.Atome;
import ILP.SelfMadeEngine.Basiques.Atomes;
import ILP.SelfMadeEngine.Basiques.Type;
import java.util.HashMap;

/**
 *
 * @author Aurélien Vialon
 */
public class BaseDeTypes extends HashMap<String, Atomes>
{
    public void ajt(String s)
    {
        if(!this.containsKey(s))
        {
           this.put(s, null);         
        }
    }
    public void ajt(Type t)
    {
        if(!this.containsKey(t.Type))
        {
           this.put(t.Type, new Atomes());         
        }
    }
    public void ajt(Atome a)
    {
        for (Type t : a.t)
        {
            if(!this.containsKey(t.Type))
            {
                Type nt = new Type(t.Type);
                this.ajt(nt);
                t = nt;               
            }
            this.get(t.Type).add(a);           
        }
    }
    
    public Atomes donneAtomesType (String t)
    {
        return this.get(t);
    }
    
    public void vider()
    {
        this.clear();
    }
}
