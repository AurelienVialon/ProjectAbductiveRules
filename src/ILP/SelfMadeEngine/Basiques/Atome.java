/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Basiques;

/**
 *
 * @author Aurélien Vialon
 */
public class Atome
{
    public String nom;
    public Types t;
    
    public Atome (String s)
    {
        this.nom = s;
        this.t = new Types();
    }
    public Atome (String s, Type t)
    {
        this(s);
        this.ajtType(t);
    }
    public void ajtType(Type t)
    {
        this.t.ajt(t);
    }
}
