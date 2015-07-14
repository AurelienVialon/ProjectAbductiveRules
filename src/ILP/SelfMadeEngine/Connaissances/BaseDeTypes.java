/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.SelfMadeEngine.Basiques.Atome;
import ILP.SelfMadeEngine.Basiques.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Aurélien Vialon
 */
public class BaseDeTypes extends HashMap<String, ArrayList<Atome>>
{
    public void ajt(Type t)
    {
        this.put(t.Type, null);
    }
    public void ajt(Atome a)
    {
        if(!this.containsKey(a.Type))
        {
          this.ajt(new Type(a.Type));
        }
        this.get(a.Type).add(a);
    }
}
