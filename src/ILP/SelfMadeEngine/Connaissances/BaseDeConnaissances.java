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
import ILP.SelfMadeEngine.Definitions.ClauseDefinition;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

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
    public BaseDeConnaissances (String f)
    {
        this();
        this.lectureFichier(f, true);
    }
    
    public void ajtPredicat(Predicat p)
    {
        this.bp.ajt(p);
    }
    public void ajtPredicat(Predicats p)
    {
        this.bp.ajt(p);
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
            cl.ajtInstance(c);
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
    public final void lectureFichier(String f, boolean ecrasement)
    {
        if(ecrasement)
        {
            this.vider();
        }
               
        try
        {
            InputStream ips=new FileInputStream(f); 
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String ligne;
            while ((ligne=br.readLine())!=null)
            {
                System.out.println(ligne);
                
                if(!ligne.startsWith("%"))
                {     
                    if(ligne.contains("modeh(") || ligne.contains("modeb("))
                    {
                      //Cas des modes déclarations, on ne les prends pas en considération.  
                    }
                    else if(ligne.contains("(") && ligne.contains(")"))
                    {
                        //Cas des Typages, des définitions de clauses et aussi de prédicats.                      
                        if(!ligne.contains(",") && !ligne.contains(":-"))
                        {
                            //Cas du typage
                            String t = ligne.substring(0, ligne.indexOf("("));
                            String at = ligne.substring(ligne.indexOf("(") + 1, ligne.indexOf(")"));
                            
                            Atome a = this.nouveauAtome(at);
                            
                            //A changer !
                            Type ty = new Type(t);
                            
                            a.ajtType(ty);
                        }
                        else if(!ligne.contains(":-"))
                        {
                           //Cas de la définition de clauses. 
                            this.ajtClause(new ClauseInstanciee(ligne, this));
                        }
                        else if(!ligne.startsWith(":-"))
                        {
                            //Cas de la définition de prédicats.
                            this.bp.nouveau(ligne);
                        }
                    }
                    else
                    {
                        //Cas par défaut, à gérer.
                    }
                }
            }
            br.close(); 
        }		
        catch (Exception e)
        {
                System.out.println(e.toString());
        }
    }
    
    
    public boolean AnalyseType (String s)
    {
        return this.bo.existeType(s);
    }
    public boolean AnalyseAtome (String s)
    {
        return this.bo.existeAtome(s);
    }
}