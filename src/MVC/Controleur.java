/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;
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
        
        Thread t = new Thread(m);
        t.start();
        
        return v;
    }
    
    public void reprise ()
    {
        this.m.retour();
    }
    
    protected abstract Modele initModele();    
    protected abstract Vue initVue( ); 
}
