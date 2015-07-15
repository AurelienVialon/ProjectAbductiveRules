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
public class Clause extends ArrayList<Type>
{
    public String nom;
 
    public Clause (String s)
    {
        this.nom = ParseClauseNom(s);
        this.addAll(ParseClauseConditions(s));
    }
    
    public static String ParseClauseNom (String s)
    {
       return s.substring(0, s.indexOf("("));
    }
    public static ArrayList ParseClauseConditions (String s)
    {
       ArrayList<Type> l = new ArrayList<>();
       
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
            l.add(new Type(conditions)); 
       }      
       return l;
    }
}
