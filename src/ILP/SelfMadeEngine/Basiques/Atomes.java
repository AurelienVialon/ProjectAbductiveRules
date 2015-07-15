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
public class Atomes extends ArrayList<Atome>
{
    public void ajt(Atome a)
    {
        this.add(a);
    }
    
    public void vider()
    {
        this.clear();
    }
}
