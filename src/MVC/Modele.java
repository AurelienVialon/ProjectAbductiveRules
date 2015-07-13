/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import MVC.communications.Message;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
/**
 *
 * @author Aurélien Vialon
 */
public abstract class Modele extends Observable implements Runnable
{       
    protected boolean continuer;  // pour quitter le thread
    protected boolean en_pause;   // pour pauser / dépauser
    
    protected Thread tache;
    
    public Modele ()
    {
        super();
    }
    public void pause ()
    {
        en_pause = true;
        
        synchronized(this)
        {
            try 
            {
                this.wait();
            } 
            catch (InterruptedException ex) 
            {
                
            }
        }
    }
   
    public void Lancement ()
    {
        if( this.tache == null )
        {
            tache = new Thread(this);
            tache.start();
        }
        else if (this.en_pause)
        {
            this.retour();
        }
    }
    public void Arret ()
    {
        tache.interrupt();
    }
    public void retour ()
    {
        if (en_pause)
        {
            en_pause = false;
        
            synchronized(this)
            {
                this.notify();
            } 
        }
    }
    //Méthode de notification de messages au observateurs.
    public void notifie()
    {
        this.setChanged();
        if ( this.countObservers() > 0)
        {
            this.notifyObservers();
        }
    }
    public void notifie(Object o)
    {
        this.setChanged();
        if ( this.countObservers() > 0)
        {
            this.notifyObservers(o);
        }
    }
    //Méthodes pour ajouter des liens entre le modele courant et des observateurs.
    public void ajouter_lien( Observer ob)
    {
        if(ob != null)
            this.addObserver(ob);
    }
    
    public void ajouter_lien (ArrayList <Observer> obs)
    {
        if(obs != null)
        {
            for (Observer ob : obs) 
            {
                this.addObserver(ob);
            }   
        }
    }
    
    //Méthodes pour enlever des liens entre le modele courant et ses observateurs.
    public void couper_lien (Observer ob)          
    {
        this.deleteObserver(ob);
    }
    public void couper_liens ()
    {
        this.deleteObservers();
    }

    public abstract void Reinit();
    
    // Pour les demandes de mise à jour.
    protected abstract void Maj ();   
    protected abstract void Maj (Object o, Message m);
    
    public void ChangeContinuer (boolean b) { continuer = b; }
    public void ChangePause (boolean b) { en_pause = b; }
    
    public boolean estEnPause () { return en_pause; }
}
