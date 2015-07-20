/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.SelfMadeEngine.Basiques.Atome;
import ILP.SelfMadeEngine.Basiques.Atomes;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Aurélien Vialon
 */
public class BaseAtomes extends HashMap<String, Atome>
{
    public void ajt(Atome a)
    {
        if(!this.containsKey(a.nom))
        {
            this.put(a.nom, a);
        }
    }
    
    public Atome donne(String s)
    {
        return this.get(s);
    }
    public Atomes donne()
    {
        Atomes a = new Atomes();
        
        for(Entry<String, Atome> i : this.entrySet()) 
        {
            a.add(i.getValue());
        }    
        return a;
    }
    
    public void vider()
    {
        this.clear();
    }
    
    @Override
    public String toString()
    {
        String s = "";
        
        for(Entry<String, Atome> i : this.entrySet()) 
        {
            s+= "\n \"" + i.getKey() + "\" typé : " + i.getValue().t.toString();
        }
        return s;
    }
}
