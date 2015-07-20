/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Definitions;

import ILP.SelfMadeEngine.Basiques.Clause;
import ILP.SelfMadeEngine.Basiques.Predicats;
import ILP.SelfMadeEngine.Basiques.Type;
import ILP.SelfMadeEngine.Basiques.Types;
import java.util.ArrayList;

/**
 *
 * @author Aurélien Vialon
 */
public class ClauseDefinition extends Clause<Types>
{
    private Predicats associes;
    
    public ClauseDefinition(String s) 
    {
        super(s);
    }  
    public ClauseDefinition(String s, boolean a) 
    {
        super(s, a);
    }  
    @Override
    public ArrayList<Types> ParseClauseConditions(String s)
    {
       ArrayList<Types> l = new ArrayList<>();
/*
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
       }      */
       return l;
    }
    
    @Override
    public String toString()
    {
        String s = this.nom +"(" ;
        int i = this.size() - 1;
        
        while(i > -1) 
        {
            Types ty =  this.get(i);
            int ii = ty.size()- 1;
            
            while(ii > -1)
            {
                s+= ty.get(ii).toString();
                
                if(ii > 0)
                {
                    s+="\\";
                }
                ii--;
            }

            if(i > 0)
                s+=",";
            i--;
        }
        s+= ")";
        return s;
    }

    public void ajt(int i, Type t) 
    {
        if( i >= this.size() )
        {
            this.add(new Types(t));
        }
        else
        {
            this.get(i).add(t);
        }
    }
    
    public Predicats donneAssociation()
    {
        return this.associes;
    }
}
