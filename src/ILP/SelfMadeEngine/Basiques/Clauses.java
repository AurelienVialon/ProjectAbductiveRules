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
public class Clauses extends ArrayList<Clause> 
{
    public Clauses()
    {
        
    }
    public void ajt(Clause c)
    {
        this.add(c);
    }
    public void ajt(Clauses c)
    {
        for(Clause cl : c)
        {
            this.add(cl);
        }
    }
    public void viderClauses()
    {
        this.clear();
    }
}
