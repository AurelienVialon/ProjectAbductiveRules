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
public abstract class Clause <T> extends ArrayList<T>
{
    public String nom;
    public Clauses Instances;
 
    public Clause ()
    {
        this.Instances = new Clauses();
    }
    public Clause (String s)
    {
        this.nom = ParseClauseNom(s);
        
        this.Instances = new Clauses();
        
        this.addAll(ParseClauseConditions(s));
    }
    public Clause (String s, boolean a)
    {
        this.nom = s;
        this.Instances = new Clauses();
    }
    
    public static String ParseClauseNom (String s)
    {
       return s.substring(0, s.indexOf("("));
    }
    
    public abstract ArrayList<T> ParseClauseConditions(String s);
    
    public void ajt( T t )
    {
        this.add(t);
    }

    public String donneNom()
    {
        return this.nom;
    }
    
    public void ajtInstance(Clause c)
    {
        this.Instances.add(c);
    }
}
