/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.SelfMadeEngine.Basiques.Clause;
import ILP.SelfMadeEngine.Basiques.Clauses;
import ILP.SelfMadeEngine.Definitions.ClauseDefinition;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Aurélien Vialon
 */
public class BaseDeClauses extends HashMap<String, Clause>
{
    public void ajt(Clause c)
    {
        if(!this.containsKey(c.donneNom()))
        {
           this.put(c.donneNom(), c);
        }
    }
    public void ajt(Clauses c)
    {
        for(Clause cl : c)
        {
            this.ajt(cl);
        }
    }
    
    public Clause donne(String s)
    {
        return this.get(s);
    }
    public Clauses donne()
    {
         Clauses c = new Clauses();
        
        for(Entry<String, Clause> i : this.entrySet()) 
        {
            c.ajt(i.getValue());
        }
        return c;       
    }
    
    public boolean existe(String s)
    {
        return this.containsKey(s);
    }
    
    public void vider()
    {
        this.clear();
    }
    
    @Override
    public String toString()
    {
        String s = "";
        
        for(Entry<String, Clause> i : this.entrySet()) 
        {
            s+= "\n\n" + i.getKey() + " => " + i.getValue().toString();
            
            if(i.getValue().Instances.size() > 0)
            {
                s+= "\n\t avec les " + i.getValue().Instances.size() + " instances suivantes : ";
                
                for(Clause c : i.getValue().Instances)
                {
                    s+= "\n\t\t\t" + c.toString();
                }
            }
            else
            {
                s+= "\n\t avec aucune instances reconnues.";
            }
            if(((ClauseDefinition)i.getValue()).donneAssociation() == null)
                s+= "\nAucun prédicat associé à cette clause.";
        }
        return s;
    }
}
