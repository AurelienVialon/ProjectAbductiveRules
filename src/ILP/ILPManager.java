/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP;


import Interfaces.ILPtoASP;
import MVC.Controleur;
import MVC.Modele;
import MVC.Vue;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import nomprol.FenetrePrincipale;

/**
 *
 * @author Aurélien Valon
 * 
 */
public class ILPManager extends Controleur
{ 
    private final FenetrePrincipale f;
    public Predicats predicats;
    
    public ILPtoASP ilasp;
    
    public ILPManager ( FenetrePrincipale  f )
    {
        this.f = f;
        this.f.ILP_Predicats_Results.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent mouseEvent)
            {
               ChangeResults(); 
            }
        });
    }
    
    public void ParseResults( BufferedReader in, JTextArea output )
    {
        String memory = "";
        String line = "";
        String lineTemp = "";
        
        predicats = new Predicats();
    
        for (;;) 
        {
            try 
            {
                line = in.readLine();
                if (line == null) break;
                output.append(line + "\n");
                
                if(line.contains("[Total number of clauses"))
                {
                    lineTemp = line.replace("[Total number of clauses =", "");
                    
                    lineTemp = lineTemp.substring(0, lineTemp.indexOf("]"));
                    lineTemp = lineTemp.replaceAll(" ", "");
                    
                    int it = Integer.parseInt(lineTemp);
                    
                    int ind = memory.lastIndexOf("[Learning ") + "[Learning ".length(); 

                    String token = "/";
                                
                    if(ind < "[Learning ".length())
                    {
                        ind = memory.lastIndexOf("[Generalising ") + "[Generalising ".length(); 
                        token = "(";
                    }
                    
                    String temp1 = memory.substring(ind);
                    String nom_pred = temp1.substring(0, temp1.indexOf(token));
                    
                    ArrayList<String> ts = new ArrayList<>();
                    
                    for ( int i=0; i < it; i++ )
                    {
                      int index = temp1.lastIndexOf(nom_pred);  
                      lineTemp = temp1.substring(index);
                      temp1 = temp1.substring(0, index);
                      lineTemp = lineTemp.replaceAll("\n", "");
                      
                      ts.add(lineTemp);                   
                    }
                    
                    if(!ts.isEmpty())
                    {
                        for(int n=ts.size(); n > 0 ; n --)
                        {
                            this.predicats.add(nom_pred, ts.get(n - 1)); 
                        }
                    }
                    
                    line = in.readLine();//In order to remove the space line !
                    	  output.append(line + "\n");
                          
                    line = in.readLine();
                    	  output.append(line + "\n");
                    memory = "";
                } 
                else
                {
                    memory = memory + line + "\n";                         
                }
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ILPManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void UpdateResults()
    {
        this.f.resetIPLResults();

        this.f.ILP_Predicats_Results.setListData(this.predicats.getTabPrefix());
        this.f.ILP_Predicats_Results.setSelectedIndex(0);
        
        String[] tab = this.predicats.getTabPredicat(this.f.ILP_Predicats_Results.getSelectedValue());
        
        this.f.ILP_Clauses_Results.setText("");
        
        for(String s : tab )
        {
            this.f.ILP_Clauses_Results.append(s + "\n"); 
        }
    }
    public void ChangeResults()
    {       
        String[] tab = this.predicats.getTabPredicat(this.f.ILP_Predicats_Results.getSelectedValue());
        
        this.f.ILP_Clauses_Results.setText("");
        
        for(String s : tab )
        {
            this.f.ILP_Clauses_Results.append(s + "\n"); 
        }
    }
    
    @Override
    public String toString()
    {
        return this.predicats.toString();
    }

    @Override
    protected Modele initModele() 
    {
        return null;
    }

    @Override
    protected Vue initVue () 
    {
        return null;
    }
    public void setInterface (ILPtoASP i)
    {
        this.ilasp = i;
    }
}
