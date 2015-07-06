/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ASP;

import MVC.Controleur;
import MVC.Modele;
import MVC.Vue;
import nomprol.FenetrePrincipale;
/**
 *
 * @author Aurélien Vialon
 */
public class ASPManager extends Controleur
{      
    FenetrePrincipale f;
    
    Modele m;
    
    public ASPManager( FenetrePrincipale f)
    {
	this.f = f;
    }

    @Override
    protected Modele initModele() 
    {
        this.m = new ASP.ASPEngine("/home/aurelien/Bureau/DLV/dlv");
        return m;
    }

    @Override
    protected Vue initVue () 
    {
        return new ASP.ASPDisplay(f, this);
    }
    
    public Modele donneModele ()
    {
        return this.m;
    }
}
