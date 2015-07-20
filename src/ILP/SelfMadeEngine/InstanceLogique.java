/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine;

import ILP.SelfMadeEngine.Basiques.Atome;
import ILP.SelfMadeEngine.Basiques.Clause;
import ILP.SelfMadeEngine.Basiques.Type;
import ILP.SelfMadeEngine.Basiques.Types;
import ILP.SelfMadeEngine.Connaissances.BaseDeConnaissances;
import ILP.SelfMadeEngine.Definitions.ClauseDefinition;
import ILP.SelfMadeEngine.Definitions.ClauseInstanciee;
import ILP.SelfMadeEngine.Hypothèses.Hypothèse;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aurelien
 */
public class InstanceLogique 
{
    public BaseDeConnaissances bc;
 
    public InstanceLogique (String s)
    {
        this.bc = new BaseDeConnaissances();
        this.lectureFichier(s, true);
    }
    public final void lectureFichier(String f, boolean ecrasement)
    {
        if(ecrasement)
        {
            this.bc.vider();
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
                            
                            Atome a = this.bc.nouveauAtome(at);
                            
                            Type ty = this.bc.nouveauType(t);
                            ty.ajt(a);
                            a.ajtType(ty);
                        }
                        else if(!ligne.contains(":-"))
                        {
                           //Cas de la définition de clauses. 
                            this.bc.ajtClause(new ClauseInstanciee(ligne, this.bc));
                        }
                        else if(!ligne.startsWith(":-"))
                        {
                            //Cas de la définition de prédicats.
                            
                            this.bc.nouveauPredicat(ligne);
                        }
                    }
                    else
                    {
                        //Cas par défaut, à gérer.
                    }
                }
            }
            
            System.out.println(" Après analyse du fichier, nous avons : ");
            System.out.print(this.bc.afficheBaseDeConnaissance());
            
            br.close(); 
        }		
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    } 
    
    public void Analyse()
    {
        this.AnalyseTypes();
        this.AnalyseTypageClauses();
        this.AnalyseClauses();
        this.AnalysePredicats();
    }
    
    public void AnalyseTypes ()
    {
        Types tempo = this.bc.donneTypes();
        Types types = new Types();
        
        //A FAIRE : Implémenter une méthode plus efficace de tri !
        for(Type y : tempo )
        {
            if(types.isEmpty())
            {
                types.add(y);
            }
            else
            {
               int ite = 0;
               for(Type e : types)
               {
                   if(e.donneduType().size() > y.donneduType().size())
                       break;
                    ite++;
               }
               types.add(ite, y);
            }
        }
        
        for(Type t : types)
        {
            HashMap<String, Hypothèse> l = new HashMap<>();
            
            int i = 0;
            for(Atome a : t.donneduType())
            {
                for(Type tt : a.t)
                {
                    if(tt != t)
                    {
                        Hypothèse h = l.get(tt.nom);
                        if( h == null )
                        {
                              h = new Hypothèse(tt);
                              l.put(tt.nom, h);
                        }
                        h.plus(); 
                        i++;
                    }
                }
            }
            
            for(Map.Entry<String, Hypothèse> it : l.entrySet()) 
            {
                 //A  changer pour mettre un pourcentage dynamique.
                 if( ((Type)it.getValue().valeur).herite != t && 
                         (float)it.getValue().donneCompteur()/(float)i > 0.5 )
                 {
                     t.herite = (Type)it.getValue().valeur;
                 }
            }
        }
    }
        
    public void AnalyseTypageClauses ()
    {
        for (Clause cl : this.bc.donneClauses())
        {
            if(cl.Instances.size() > 0 )
            {
                ArrayList<HashMap> l = new ArrayList<>();
                
                for(int i = 0; i < cl.Instances.get(0).size(); i++)
                {
                    l.add(new HashMap<String, Hypothèse>());
                }
                String s = cl.nom;
                
                for(ClauseInstanciee inst : cl.Instances)
                {
                   int i = 0;
                   for(Atome a : inst)
                   {
                       for(Type t : a.t)
                       {
                            Hypothèse h = (Hypothèse)l.get(i).get(t.nom);
                            if(h == null)
                            {
                               h = new Hypothèse(t); 
                               l.get(i).put(t.nom, h);
                            }
                            h.plus();
                        }
                       i ++ ;
                   }
                }  
                 int it = 0;
                for(HashMap<String, Hypothèse> la : l)
                {
                    ArrayList<Hypothèse> lh = new ArrayList<>();
                    for(Map.Entry<String, Hypothèse> i : la.entrySet()) 
                    {
                        if(lh.isEmpty())
                        {
                            lh.add(i.getValue());
                        }
                        else
                        {
                            if(i.getValue().donneCompteur() > lh.get(0).donneCompteur())
                            {
                                lh.clear();
                                lh.add(i.getValue());
                            }
                            else if(i.getValue().donneCompteur() == lh.get(0).donneCompteur())
                            {
                                lh.add(i.getValue());
                            }
                        }
                    }
                    
                    int i = lh.size() - 1;
                    while(i > -1)
                    {
                        if(((Type)lh.get(i).valeur).herite != null)
                        {
                            for(Hypothèse h1 : lh)
                            {
                                if(((Type)h1.valeur) == ((Type)lh.get(i).valeur).herite)
                                {
                                    lh.remove(i);
                                    i--;
                                    break;
                                }
                            }
                        }
                        i--;
                     }
                    
                    for(Hypothèse h : lh)
                        ((ClauseDefinition)cl).ajt(it, (Type)h.valeur);
                    it ++;
                }
            }
        }
    }
    
    public void AnalyseClauses()
    {
        //A FAIRE !
    }
    public void AnalysePredicats()
    {
        //A FAIRE !
    }
}
