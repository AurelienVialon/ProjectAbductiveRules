/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import MVC.communications.Communication;
import MVC.communications.Message;
import MVC.communications.*;

/**
 *
 * @author Aurélien Vialon
 */
public abstract class Controleur 
{  
    protected Modele m;
    
    public Controleur()
    {
        super();
    }     
    //Méthodes d'initialisation des modèles et vue.
    public Vue Init ( )
    {
        this.m = this.initModele();
        Vue v = this.initVue();
        
        m.ajouter_lien(v);
        
        return v;
    }
    
    public void Reprise ()
    {
        this.m.retour();
    }
    public void Fonctionnement ()
    {
        this.m.Lancement();
    }
    public void PasseMessage(Message msg)
    {
        MVC.communications.Communication(msg.destinataire(this.m));
    }
    
    protected abstract Modele initModele();    
    protected abstract Vue initVue( ); 
}
