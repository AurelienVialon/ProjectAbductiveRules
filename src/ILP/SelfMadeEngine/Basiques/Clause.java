/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Basiques;

import ILP.SelfMadeEngine.Definitions.ClauseInstanciee;
import ILP.SelfMadeEngine.Definitions.ClausesInstanciees;
import java.util.ArrayList;

/**
 *
 * @author Aurélien Vialon
 */
public abstract class Clause <T> extends ArrayList<T>
{
    public String nom;
    public ClausesInstanciees Instances;
    
    public Clause ()
    {
        this.Instances = new ClausesInstanciees();
    }
    public Clause (String s)
    {
        this.nom = ParseClauseNom(s);
               
        this.Instances = new ClausesInstanciees();
        
        this.addAll(ParseClauseConditions(s));
    }
    public Clause (String s, boolean a)
    {
        this.nom = s;
        this.Instances = new ClausesInstanciees();
    }
    
    public static String ParseClauseNom (String s)
    {
       return s.substring(0, s.indexOf("("));
    }
    
    public abstract ArrayList<T> ParseClauseConditions(String s);
    
    public void ajt( T t )
    {
        this.add(t);
    }
    
    public String donneNom()
    {
        return this.nom;
    }
    
    public void ajtInstance(ClauseInstanciee c)
    {
        this.Instances.add(c);
    }
    @Override
    public String toString()
    {
        String s = "\n\t" + this.nom +"(" ;
        int i = this.size() - 1;
        
        while(i > -1) 
        {
            s+= this.get(i).toString();
            
            if(i > 0)
                s+=",";
            i--;
        }
        s+= ")";
        return s;
    }
}
