/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import java.util.ArrayList;
/**
 *
 * @author Aurélien Vialon
 */
public abstract class MetaModele extends Modele
{
    protected ArrayList<Modele> lm;
    
    public MetaModele()
    {
        super();
        lm = new ArrayList<>();
    }

    @Override
    public void Reinit() 
    {
        for ( Modele m : lm )
        {
            m.Reinit();
        }
    }

    @Override
    public void Lancement() 
    {
        for (Modele m : lm )
        {
            m.Lancement();
        }
    }

    @Override
    public void Arret() 
    {
        for (Modele m : lm )
        {
            m.Arret();
        }
    }
    @Override
    public void retour()
    {
        for ( Modele m : lm )
        {
            m.retour();
        }
    }
}
