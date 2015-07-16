/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine;

import ILP.SelfMadeEngine.Basiques.Clause;
import ILP.SelfMadeEngine.Basiques.Variable;
import ILP.SelfMadeEngine.Basiques.Variables;
import java.util.ArrayList;

/**
 *
 * @author Auréline Vialon
 */
public class Prefixe extends Clause<Variable>
{
    public Prefixe(String p)
    {
        super(p);
    }

    @Override
   public ArrayList<Variable> ParseClauseConditions(String s)
    {
       Variables l = new Variables();

       String conditions = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
       
       if(conditions.length() > 0 )
       {          
            conditions = conditions.replaceAll(" ", "");
       
            int i = conditions.indexOf(",");
       
            while(i != -1)
            {
                String condtemp = conditions.substring(0,i);
          
                l.add(new Variable(condtemp));
          
                conditions = conditions.substring(conditions.indexOf(condtemp) + condtemp.length() + 1);
          
                i = conditions.indexOf(",");
            }
            l.add(new Variable(conditions)); 
       }      
       return l;
    }
}
