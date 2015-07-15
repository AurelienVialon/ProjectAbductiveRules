/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.SelfMadeEngine.Basiques.Atome;
import ILP.SelfMadeEngine.Basiques.Clause;
import ILP.SelfMadeEngine.Basiques.Clauses;
import ILP.SelfMadeEngine.Basiques.Predicat;
import ILP.SelfMadeEngine.Basiques.Predicats;
import ILP.SelfMadeEngine.Basiques.Type;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        this.bc.ajt(c);
    }
    public void ajtClause(Clauses c)
    {
        this.bc.ajt(c);
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
        String chaine ="";
        
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
                chaine+=ligne+"\n";
                
                if(!ligne.startsWith("%"))
                {     
                    if(ligne.contains("modeh(")||ligne.contains("modeb("))
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
                            
                            Atome a;
                            
                            if(!this.bo.existeAtome(at))
                            {
                                a = new Atome(at);
                            }
                            else
                            {
                                a = this.bo.donneAtome(at);
                            }
                            a.ajtType(new Type(t));
                            this.bo.ajt(a);
                        }
                        else if(!ligne.contains(":-"))
                        {
                           //Cas de la définition de clauses. 
                            this.bc.ajt(new Clause(ligne));
                        }
                        else if(!ligne.startsWith(":-"))
                        {
                            //Cas de la définition de prédicats.
                            this.bp.ajt(new Predicat(ligne));
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
}