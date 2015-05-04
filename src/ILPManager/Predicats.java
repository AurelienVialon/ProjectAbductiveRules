/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILPManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;


/**
 *
 * @author aurelien
 */
public class Predicats extends HashMap<String, ArrayList<Predicat>> 
{  
    public Predicats() 
    {
       super();
    } 
    
    public void add(String nom, String p)
    {
        ArrayList<Predicat> pr = this.get(nom);
        Predicat npr = null;
        
        if ( p.contains(":-"))
        {
            int index = p.indexOf(":-");
            npr = new Predicat(p.substring(0, index), p.substring(index + 2));
        }
        else
        {
            npr = new Predicat(p.substring(0));
        }
        
        if(pr !=  null)
        {
            pr.add(npr);
        }
        else
        {
            ArrayList<Predicat> lpr = new ArrayList<>();
            lpr.add(npr);
            this.put(nom, lpr); 
        }
    }
    
    public Set<Entry<String, ArrayList<Predicat>>> getAllKeys ()
    {
       return this.entrySet();
    }
    
    public ArrayList<Predicat> getList()
    {
        ArrayList<Predicat> l = new ArrayList<>();
                
                
        for(Entry<String, ArrayList<Predicat>> i : this.entrySet()) 
        {
            l.addAll(((ArrayList<Predicat>)i.getValue()));
        }
        return l;
    }
    public String[] getTabPrefix ()
    {
        String[] l = new String[this.size()];
        
        int n = 0;
                    
        for(Entry<String, ArrayList<Predicat>> i : this.entrySet()) 
        {
            l[n] = i.getKey();
            n++;
        }
        return l;
    }
    public String[] getTabClauses(String pr)
    {
        int n = 0;
                    
        ArrayList<Predicat> pt = this.get(pr);
        
        String[] l = new String[pt.size()];
        
        for ( Predicat p : pt )
        {
            l[n] = p.getClauses();
        }
        
        return l;
    }
    public String[] getTabPredicat(String pr)
    {
        int n = 0;
                    
        ArrayList<Predicat> pt = this.get(pr);
        
        String[] l = new String[pt.size()];
        
        for ( Predicat p : pt )
        {
            l[n] = p.getPredicat();
            n++;
        }       
        return l;
    }
    public ArrayList<String> getListPredicat(String pr)
    {            
        ArrayList<Predicat> pt = this.get(pr);
        
        ArrayList<String> l = new ArrayList<>();
        
        for ( Predicat p : pt )
        {
            l.add(p.getPredicat());
        }       
        return l;
    }
    
    //Fonction qui renvoie tous les prédicats contenus en mémoire, sans les séparer seln leurs préfixes :
    public ArrayList<String> getListPredicat()
    {
        ArrayList<String> l = new ArrayList<>();
        
        for(Entry<String, ArrayList<Predicat>> i : this.entrySet()) 
        {
            for ( Predicat p : i.getValue() )
            {
                l.add(p.getPredicat());
            }
        }    
        return l;
    }
    @Override
    public String toString ()
    {
        String ret = "";
        
        for(Entry<String, ArrayList<Predicat>> i : this.entrySet()) 
        {
            for( Predicat p : ((ArrayList<Predicat>)i.getValue()))
            {
                ret = ret + p.getPredicat() + "\n";
            }
        }
        return ret;
    }
}
