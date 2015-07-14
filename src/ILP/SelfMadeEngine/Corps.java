/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine;

import ILP.SelfMadeEngine.Basiques.Clause;
import ILP.SelfMadeEngine.Basiques.Clauses;

/**
 *
 * @author Aurélien Vialon
 */
public class Corps extends Clauses
{
    public Corps (String s)
    {
        super();

        this.ajt(ParseCorps(s));
    }
    
    public static Clauses ParseCorps (String s)
    {
        Clauses c = new Clauses();
        int separateur;
        String temp = "";
        
        separateur = s.indexOf("),");

        while(!"".equals(s) && !".".equals(s))
        {
            if(separateur != -1)
            {
                temp = s.substring(separateur + 2); 
            }
            else
            {
                separateur = s.indexOf(")");
            
                if(separateur != -1 )
                {
                    temp = s.substring(0, separateur + 1);
                }
            }
        
            c.add(new Clause(temp));
            s = s.replace(temp, "");
        } 
        return c;
    }
}
