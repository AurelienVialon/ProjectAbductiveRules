/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ASP;

import ILP.ILPManager;
import MVC.Modele;
import MVC.communications.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aurélien Vialon
 */
public class ASPEngine extends Modele
{
    private Process p;
    
    private String cheminASP;
    private String cheminFichier;
    
    private Message m;
    
    public ASPEngine()
    {
        super();
        
        this.cheminASP = "";
        this.cheminFichier = "";
        this.m = new Message();
    }
    public ASPEngine(String c)
    {
        super();
        
        this.cheminASP = c;
        this.cheminFichier = "/home/aurelien/Bureau/Projet/famille_ASP.pl";
        this.m = new Message();
    }
    
    @Override
    public void Reinit() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() 
    {
        Runtime now = Runtime.getRuntime();
         
        try 
        {
          while(true)
            {
              m.s = "";
              String st = this.cheminASP + " " + cheminFichier;
                p = now.exec(this.cheminASP + " " + cheminFichier);

                InputStream s = p.getInputStream();
                InputStream e = p.getErrorStream();
                
                BufferedReader in
                        = new BufferedReader(new InputStreamReader(s));

                String line = "";
        
                for (;;) 
                {
                    try 
                    {
                        line = in.readLine();
                        if (line == null) break;

                        if(line.contains("{"))
                        {
                           ArrayList<String> l = this.ParseLine(line); 
                           
                           for( String sl : l)
                           {
                                m.s += sl + "\n";
                           }
                        }
                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(ILPManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                if (e != null)
                {
                    in = new BufferedReader(new InputStreamReader(e));
                    for (;;) 
                    {
                        try 
                        {
                            line = in.readLine();
                            if (line == null) break;

                            m.s += line + "\n";
                        } 
                        catch (IOException ex) 
                        {
                            Logger.getLogger(ILPManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                }
                this.Maj();

                this.pause();
            }
        }
        catch (IOException e) 
        {
            System.out.println("Error: " + e);
        }
    }

    private ArrayList<String> ParseLine (String l)
    {
        ArrayList<String> as = new ArrayList<>();
        
        l = l.substring(l.indexOf("{") + 1);
        
        String s = "";
        
        while(!"".equals(l))
        {
          s = l.substring(0, l.indexOf("),") + 1);
          
          if ("".equals(s))
              s = l.substring(0, l.indexOf("}"));
          
          s = s.replaceAll(" ", "");
          as.add(s);
          l = l.substring(l.indexOf(s) + s.length() + 1);
        } 
        return as;
    }
    @Override
    protected void Maj() 
    {
        this.notifie(m);
    }
}
