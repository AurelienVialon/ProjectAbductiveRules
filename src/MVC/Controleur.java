/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Aurélien Vialon
 */
public abstract class Controleur implements ActionListener 
{  
    protected Modele m;
    
    public Controleur()
    {
        super();
    }
    
    @Override
    public abstract void actionPerformed(ActionEvent e);
 
       
    //Méthodes d'initialisation des modèles et vue.
    public Vue Init ()
    {
        this.m = this.initModele();
        Vue v = this.initVue();
        
        m.ajouter_lien(v);
        
        Thread t = new Thread(m);
        t.start();
        
        return v;
    }
    
    protected abstract Modele initModele();    
    protected abstract Vue initVue(); 
}
