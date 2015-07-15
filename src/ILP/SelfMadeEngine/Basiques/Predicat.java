/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Basiques;

import ILP.SelfMadeEngine.Corps;
import ILP.SelfMadeEngine.Prefixe;

/**
 *
 * @author Aurélien Vialon
 * 
 */
public final class Predicat
{
    protected Prefixe p ;
    protected Corps c ;
    
    public Predicat(Prefixe p)
    {
       //Pour gérer les Prédicats sans clauses.
       super(); 
       this.p = p;
    } 
    public Predicat(String p, String c)
    {
       //Pour gérer les Prédicats sans clauses.
       super(); 
       this.p = new Prefixe(p);
       this.c = new Corps(c);
    }  
    public Predicat(Prefixe p, Clause c)
    {
       super(); 
       
       this.defPrefixe(p);
       this.ajtClause(c);
    } 
    public Predicat(Prefixe p, Clauses c)
    {
       super(); 
       
       this.defPrefixe(p);
       this.ajtClause(c);
    } 
    public Predicat(String p)
    {
       super(); 
       
       this.p = new Prefixe(ParsePredicatPrefixe(p));
       this.c = new Corps(ParsePredicatCorps(p));
    }  

    
    public static String ParsePredicatPrefixe (String s)
    {
       return s.substring(0, s.indexOf(":"));
    }
    public static String ParsePredicatCorps (String s)
    {
       return s.substring(s.indexOf(":-") + 2, s.indexOf("."));
    }
    
    public void defPrefixe(Prefixe p)
    {
        this.p = p;
    }
    public void ajtClause(Clauses c) 
    {
        this.c.ajt(c);
    }    
    public void ajtClause(Clause c) 
    {
        this.c.ajt(c);
    }
    
    public Prefixe obtPrefixe()
    {
       return this.p; 
    }
    public Corps obtCorps()
    {
        return this.c;
    }   
    public String obtNom()
    {
        return this.p.nom;
    }      

    public String obtPredicat()
    {
        String ret = this.p.toString();
        
        if(!"".equals(this.c))
        {
            ret = ret + ":-" + this.c.toString();
        }
        return ret;
    }
}