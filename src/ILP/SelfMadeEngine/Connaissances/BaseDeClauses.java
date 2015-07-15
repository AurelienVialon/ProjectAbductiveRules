/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.SelfMadeEngine.Basiques.Clause;
import ILP.SelfMadeEngine.Basiques.Clauses;
import java.util.HashMap;

/**
 *
 * @author Aurélien Vialon
 */
public class BaseDeClauses extends HashMap<String, Clauses>
{
    public void ajt(Clause c)
    {
        if(!this.containsKey(c.nom))
        {
           this.put(c.nom, new Clauses());
        }
        this.get(c.nom).add(c);
    }
    public void ajt(Clauses c)
    {
        for(Clause cl : c)
        {
            this.ajt(cl);
        }
    }
    
    public Clauses donne (String s)
    {
        return this.get(s);
    }
    
    public boolean existe(String s)
    {
        return this.containsKey(s);
    }
    
    public void vider()
    {
        this.clear();
    }
}
