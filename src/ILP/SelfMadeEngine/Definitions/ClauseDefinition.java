/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Definitions;

import ILP.SelfMadeEngine.Basiques.Clause;
import ILP.SelfMadeEngine.Basiques.Type;
import ILP.SelfMadeEngine.Basiques.Types;
import java.util.ArrayList;

/**
 *
 * @author Aurélien Vialon
 */
public class ClauseDefinition extends Clause<Type>
{
    public ClauseDefinition(String s) 
    {
        super(s);
    }  
    public ClauseDefinition(String s, boolean a) 
    {
        super(s, a);
    }  
    @Override
    public ArrayList<Type> ParseClauseConditions(String s)
    {
       Types l = new Types();

       String conditions = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
       
       if(conditions.length() > 0 )
       {          
            conditions = conditions.replaceAll(" ", "");
       
            int i = conditions.indexOf(",");
       
            while(i != -1)
            {
                String condtemp = conditions.substring(0,i);
          
                l.add(new Type(condtemp));
          
                conditions = conditions.substring(conditions.indexOf(condtemp) + condtemp.length() + 1);
          
                i = conditions.indexOf(",");
            }
            l.add( new Type (conditions)); 
       }      
       return l;
    }
}
