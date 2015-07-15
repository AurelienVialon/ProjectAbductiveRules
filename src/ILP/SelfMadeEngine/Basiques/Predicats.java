/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Basiques;

import java.util.ArrayList;

/**
 *
 * @author Aurélien Vialon
 */
public class Predicats extends ArrayList<Predicat>
{
    public void ajt(Predicat p)
    {
        this.add(p);
    }
    public void ajt(Predicats p)
    {
        for(Predicat pr : p)
        {
            this.ajt(pr);
        }
    }
    
    public void vider()
    {
        this.clear();
    }
}
