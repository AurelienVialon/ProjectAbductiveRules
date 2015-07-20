/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.SelfMadeEngine.Basiques.Atome;
import ILP.SelfMadeEngine.Basiques.Type;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Aurélien Vialon
 */
public class BaseDeTypes extends HashMap<String, Type>
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
        if(!this.containsKey(t.nom))
        {
           this.put(t.nom, t);         
        }
    }
    public void ajt(Atome a)
    {
        for (Type t : a.t)
        {
            if(!this.containsKey(t.nom))
            {
                Type nt = new Type(t.nom);
                this.ajt(nt);
                t = nt;               
            }
            this.get(t.nom).ajt(a);           
        }
    }
    
    public Type donneType (String t)
    {
        return this.get(t);
    }
    
    public void vider()
    {
        this.clear();
    }
    
    @Override
    public String toString()
    {
        String s = "";
        
        for(Entry<String, Type> i : this.entrySet()) 
        {
            s+= "\n" + i.getKey();
            
            if(i.getValue().herite != null)
                s+= " => " + i.getValue().herite.nom;
        }
        return s;
    }
}
