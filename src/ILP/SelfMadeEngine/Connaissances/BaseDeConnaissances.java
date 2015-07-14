/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.SelfMadeEngine.Connaissances;

import ILP.Predicat;
import ILP.SelfMadeEngine.Basiques.Atome;
import ILP.SelfMadeEngine.Basiques.Type;
import PrologParse.Clause;

/**
 *
 * @author Aurélien Vialon
 */
public class BaseDeConnaissances 
{
    protected BaseDePredicats p;
    protected BaseDeTypes bt;
    protected BaseDeClauses bc;
    
    public BaseDeConnaissances ()
    {
        
    }
    
    public void ajtPredicat(Predicat p)
    {
        
    }
    public void ajtType(Type t)
    {
        this.bt.ajt(t);
    }
    public void ajtAtome(Atome a)
    {
        this.bt.ajt(a);
    }
    public void ajtClause(Clause c)
    {
        
    }
}
