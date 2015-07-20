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
public class Type
{
    public String nom;
    public Type herite;
    
    private Atomes duType;
    
    
    public Type (String s)
    {
      this.nom = s.replaceAll(" ", "");  
      this.duType = new Atomes();
      this.herite = null;
    }
    public Atomes donneduType()
    {
        return this.duType;
    }
    public void ajt(Atome a)
    {
        this.duType.add(a);
    }
    
    
    public String toString()
    {
        return this.nom;
    }
}
