/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.SelfMadeEngine.Basiques.Predicat;
import ILP.SelfMadeEngine.Basiques.Predicats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;


/**
 *
 * @author Aurélien Vialon
 * 
 */
public class BaseDePredicats extends HashMap<String, Predicats> 
{  
    public BaseDePredicats() 
    {
       super();
    } 
    public void ajt(Predicat p)
    {
        if(!this.containsKey(p.obtNom()))
        {
            this.put(p.obtNom(), new Predicats());
        } 
        this.get(p.obtNom()).add(p);
    }
    public void ajt(Predicats p)
    {
        for(Predicat pr : p)
        {
            this.ajt(pr);
        }
    }
    public void ajt(String nom, String p)
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
            Predicats lpr = new Predicats();
            lpr.add(npr);
            this.put(nom, lpr); 
        }
    }
    
    public Set<Entry<String, Predicats>> getAllKeys ()
    {
       return this.entrySet();
    }
    
    public ArrayList<Predicat> getList()
    {
        Predicats l = new Predicats();
                
                
        for(Entry<String, Predicats> i : this.entrySet()) 
        {
            l.addAll(((ArrayList<Predicat>)i.getValue()));
        }
        return l;
    }
    public String[] getTabPrefix ()
    {
        String[] l = new String[this.size()];
        
        int n = 0;
                    
        for(Entry<String, Predicats> i : this.entrySet()) 
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
            l[n] = p.obtCorps().toString();
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
            l[n] = p.obtPredicat();
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
            l.add(p.obtPredicat());
        }       
        return l;
    }
    //Fonction qui renvoie tous les prédicats contenus en mémoire, sans les séparer selon leurs préfixes :
    public ArrayList<String> getListPredicat()
    {
        ArrayList<String> l = new ArrayList<>();
        
        for(Entry<String, Predicats> i : this.entrySet()) 
        {
            for ( Predicat p : i.getValue() )
            {
                l.add(p.obtPredicat());
            }
        }    
        return l;
    }
    @Override
    public String toString ()
    {
        String ret = "";
        
        for(Entry<String, Predicats> i : this.entrySet()) 
        {
            for( Predicat p : ((ArrayList<Predicat>)i.getValue()))
            {
                ret = ret + p.obtPredicat() + "\n";
            }
        }
        return ret;
    }
    
    public void vider ()
    {
        this.clear();
    }
}
