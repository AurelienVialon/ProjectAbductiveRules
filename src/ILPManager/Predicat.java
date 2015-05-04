/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILPManager;

/**
 *
 * @author aurelien
 * 
 */
public final class Predicat
{
    private String Prefixe = "";
    private String Clauses = "";
    
    public Predicat(String p)
    {
       //Pour gérer les Prédicats sans clauses.
       super(); 
       
       this.setPrefixe(p);
    }    
    public Predicat(String p, String C)
    {
       super(); 
       
       this.setPrefixe(p);
       this.addClauses(C);
    }    

    public void setPrefixe(String p)
    {
        this.Prefixe = p.replaceAll(" ", "");
    }
    public void addClauses(String C) 
    {
        this.Clauses = C.replaceAll(" ", "");
    }
    
    public String getPrefixe()
    {
       return this.Prefixe; 
    }
    public String getClauses()
    {
        return this.Clauses;
    }
    

    public String getPredicat()
    {
        String ret = this.Prefixe;
        
        if(!"".equals(this.Clauses))
        {
            ret = ret + ":-" + this.Clauses;
        }
        return ret;
    }
}
