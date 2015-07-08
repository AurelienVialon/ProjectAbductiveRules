/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP;


import ASP.ASPManager;
import ILP.Engine.ILPEngine;
import ILP.Engine.ILPMemory;
import Interfaces.ILPtoASP;
import MVC.Controleur;
import MVC.Modele;
import MVC.Vue;
import nomprol.FenetrePrincipale;

/**
 *
 * @author Aurélien Valon
 * 
 */
public class ILPManager extends Controleur
{ 
    private final FenetrePrincipale f;
    
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
        
        this.initModele();
    }
    
    public void UpdateResults()
    {
        this.f.resetIPLResults();

        this.f.ILP_Predicats_Results.setListData(((ILPEngine)this.m).predicats.getTabPrefix());
        this.f.ILP_Predicats_Results.setSelectedIndex(0);
        
        String[] tab = ((ILPEngine)this.m).getResult(this.f.ILP_Predicats_Results.getSelectedValue());
        
        this.f.ILP_Clauses_Results.setText("");
        
        for(String s : tab )
        {
            this.f.ILP_Clauses_Results.append(s + "\n"); 
        }
    }
    public void ChangeResults()
    {       
        String[] tab = ((ILPEngine)this.m).getResult(this.f.ILP_Predicats_Results.getSelectedValue());
        
        this.f.ILP_Clauses_Results.setText("");
        
        for(String s : tab )
        {
            this.f.ILP_Clauses_Results.append(s + "\n"); 
        }
    }
    
    public void Stop ()
    {
        ((ILPEngine)this.m).destroy();
    }
    
    @Override
    public String toString()
    {
        return ((ILPEngine)this.m).predicats.toString();
    }

    @Override
    protected Modele initModele() 
    {
        return new ILPEngine();
    }

    @Override
    protected Vue initVue () 
    {
        return new ILPDisplay(this.f);
    }
    public void setInterface (ASPManager i)
    {
        //Define the interface between ILP and ASP
        this.ilasp = new ILPtoASP(i);
    }
    
    public ILPMemory getILPMemory ()
    {
       return ((ILPEngine)this.m).getMemory();
    }
}
