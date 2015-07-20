/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Hypothèses;

/**
 *
 * @author Aurélien Vialon
 */
public class Hypothèse <T>
{
    public T valeur;
    private int compteur;
    
    public Hypothèse (T v)
    {
        this.valeur = v;
        this.compteur = 0;
    }
    
    public void plus()
    {
        this.compteur++;
    }
    public void moins ()
    {
        this.compteur--;
    }
    public int donneCompteur()
    {
        return this.compteur;
    }
}
