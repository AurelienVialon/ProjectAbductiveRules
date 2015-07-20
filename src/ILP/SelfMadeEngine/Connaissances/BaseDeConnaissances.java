/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.SelfMadeEngine.Basiques.Atome;
import ILP.SelfMadeEngine.Basiques.Clause;
import ILP.SelfMadeEngine.Definitions.ClauseInstanciee;
import ILP.SelfMadeEngine.Basiques.Clauses;
import ILP.SelfMadeEngine.Basiques.Predicat;
import ILP.SelfMadeEngine.Basiques.Predicats;
import ILP.SelfMadeEngine.Basiques.Type;
import ILP.SelfMadeEngine.Basiques.Types;
import ILP.SelfMadeEngine.Definitions.ClauseDefinition;

/**
 *
 * @author Aurélien Vialon
 */
public class BaseDeConnaissances 
{
    protected BaseDePredicats bp;
    protected BaseObjets bo;
    protected BaseDeClauses bc;
    
    public BaseDeConnaissances ()
    {
        this.bc = new BaseDeClauses();
        this.bp = new BaseDePredicats();
        this.bo = new BaseObjets();
    }

    public void ajtPredicat(Predicat p)
    {
        this.bp.ajt(p);
    }
    public void ajtPredicat(String nom, Predicats p)
    {
        this.bp.ajt(nom, p);
    }
    public void ajtClause(Clause c)
    {
        Clause cl = this.bc.donne(c.donneNom());
        if(cl == null)
        {    
            cl = new ClauseDefinition(c.donneNom(), true);
           
            //Prendre en charge les types  ici.
           
            this.bc.ajt(cl);      
        }
        if(c instanceof ClauseInstanciee)
        {
            cl.ajtInstance((ClauseInstanciee)c);
        }
    }
    
    //Fonction de création des objets logiques, de façon à éviter les doublons.
    public Atome nouveauAtome(String s)
    {
        Atome a = this.bo.donneAtome(s);
        
        if(a == null)
        {
            a = new Atome(s);
            this.bo.ajt(a);
        }
        return a;
    } 
    public Type nouveauType(String s)
    {
        Type t;
        
        t = this.bo.bt.donneType(s);
        
        if(t == null)
        {
            t = new Type(s);
            this.bo.bt.ajt(t);
        }     
        return t;
    }
    public Predicat nouveauPredicat(String s)
    {
        return this.bp.nouveau(s);
    }
    
    public void ajtClause(Clauses c)
    {
        for ( Clause cl : c)
        {
            this.bc.ajt(cl);
        }
    }
    
    public void viderClauses()
    {
        this.bc.vider();
    }
    public void viderPredicats()
    {
        this.bp.vider();
    }
    public void viderObjets ()
    {
        this.bo.vider();
    }
    public void vider()
    {
        this.viderClauses();
        this.viderPredicats();
        this.viderObjets();
    }
   
    
    public Clauses donneClauses()
    {
        return this.bc.donne();
    }
    public Types donneTypes()
    {
       return this.bo.donneTypes();
    }
    
    public boolean AnalyseType (String s)
    {
        return this.bo.existeType(s);
    }
    public boolean AnalyseAtome (String s)
    {
        return this.bo.existeAtome(s);
    }
    
    public int nombreTypes()
    {
        return this.bo.bt.size();
    }
    public int nombreAtomes()
    {
        return this.bo.ba.size();
    }  
    public int nombreClauses()
    {
        return this.bc.size();
    }
    public int nombrePredicats()
    {
        return this.bp.size();
    } 
    
    public String afficheBaseDeConnaissance()
    {
        String s = "";
                    
        s += "\n\n" + this.nombreTypes() + " types en mémoire : " + this.afficheTypes();
        s += "\n\n" + this.nombreAtomes() + " atomes en mémoire : " + this.afficheAtomes();
        s += "\n\n" + this.nombreClauses() + " clauses en mémoire : " + this.afficheClauses();
        s += "\n\n" + this.nombrePredicats() + " prédicats en mémoire : " + this.affichePredicats();
        
        return s;
    }
    public String afficheTypes()
    {
        return this.bo.bt.toString();
    }
    public String afficheAtomes()
    {
        return this.bo.ba.toString();
    }
    public String afficheClauses()
    {
        return this.bc.toString();
    }
    public String affichePredicats()
    {
        return this.bp.toString();
    }
}