/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.SelfMadeEngine.Basiques.Atome;
import ILP.SelfMadeEngine.Basiques.Atomes;
import ILP.SelfMadeEngine.Basiques.Type;
import ILP.SelfMadeEngine.Basiques.Types;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Aurélien Vialon
 */
public class BaseObjets 
{
    BaseDeTypes bt;
    BaseAtomes ba;
    
    public BaseObjets ()
    {
        this.ba = new BaseAtomes();
        this.bt = new BaseDeTypes();
    }
    
    public void ajt(String type)
    {
        this.bt.ajt(type);
    }
    public void ajt(Type type)
    {
        this.bt.ajt(type);
    }
    
    public void ajt(Atome a)
    {
        this.bt.ajt(a);
        this.ba.ajt(a);
    }
    
    public Types donneTypes()
    {
        Set types = this.bt.keySet();
        Types ret = new Types();
        Iterator it = types.iterator();
        
        while(it.hasNext())
        {
            ret.ajt((Type)it.next());
        }
        return ret;
    }
    
    public Atomes donneAtomesType (String s)
    {
        return this.bt.donneAtomesType(s);
    }
    public Atome donneAtome (String s)
    {
        return this.ba.donne(s);
    }
    public Atomes donneAtomes ()
    {
        return this.ba.donne();
    }

    public boolean existeType (String s)
    {
        return this.bt.containsKey(s);
    }
    public boolean existeAtome (String s)
    {
        return this.ba.containsKey(s);
    }
    public void vider()
    {
        this.ba.vider();
        this.bt.vider();
    }
}
