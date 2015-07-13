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
    FenetrePrincipale f;
    public ILPtoASP ilasp;
    
    public ILPManager ( FenetrePrincipale  f )
    {
        super();
        this.f = f;
    }
    
    public void Stop ()
    {
        ((ILPEngine)this.m).destroy();
    }
    
    @Override
    protected Modele initModele() 
    {
        ILPEngine e = new ILPEngine();
        return e;
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
    public ILPMemory getMemory ()
    {
        return ((ILPEngine)this.m).mem;
    }
}
