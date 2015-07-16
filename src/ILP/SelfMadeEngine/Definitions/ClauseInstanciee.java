/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Definitions;

import ILP.SelfMadeEngine.Basiques.Atome;
import ILP.SelfMadeEngine.Basiques.Atomes;
import ILP.SelfMadeEngine.Basiques.Clause;
import ILP.SelfMadeEngine.Connaissances.BaseDeConnaissances;
import java.util.ArrayList;

/**
 *
 * @author Aurélien Vialon
 */
public class ClauseInstanciee extends Clause<Atome>
{
    protected Clause definition;  
    
    public ClauseInstanciee(String s) 
    {
        super(s);
        this.definition = null;
    } 
    public ClauseInstanciee(String s, BaseDeConnaissances bc) 
    {
        super();
        this.nom = Clause.ParseClauseNom(s);
        this.addAll(this.ParseClauseConditions(s, bc));
        this.definition = null;
    }  
    public ClauseInstanciee(Clause c) 
    {
        super(c.donneNom());
        this.definition = c;
        c.ajtInstance(this);
    } 

    public ArrayList<Atome> ParseClauseConditions(String s, BaseDeConnaissances bc)
    {
       Atomes l = new Atomes();

       String conditions = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
       
       if(conditions.length() > 0 )
       {          
            conditions = conditions.replaceAll(" ", "");
       
            int i = conditions.indexOf(",");
       
            while(i != -1)
            {
                String condtemp = conditions.substring(0,i);
          
                l.add(bc.nouveauAtome(condtemp));
          
                conditions = conditions.substring(conditions.indexOf(condtemp) + condtemp.length() + 1);
          
                i = conditions.indexOf(",");
            }
            l.add(bc.nouveauAtome(conditions)); 
       }      
       return l;
    }

    @Override
    public ArrayList<Atome> ParseClauseConditions(String s) 
    {
       Atomes l = new Atomes();

       String conditions = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
       
       if(conditions.length() > 0 )
       {          
            conditions = conditions.replaceAll(" ", "");
       
            int i = conditions.indexOf(",");
       
            while(i != -1)
            {
                String condtemp = conditions.substring(0,i);
          
                l.add(new Atome(condtemp));
          
                conditions = conditions.substring(conditions.indexOf(condtemp) + condtemp.length() + 1);
          
                i = conditions.indexOf(",");
            }
            l.add(new Atome(conditions)); 
       }      
        return l;
    }
}
