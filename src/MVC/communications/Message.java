/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.communications;
/**
 *
 * @author Aurélien Vialon
 */
public class Message 
{
    //Classe qui nous permettra de passer des messages entre les modeles et les vues.
    public String s;
    public Object o;
    
    public Object signature;
    public Object destinataire;
    
    public long temps_creation;
    
    public Message ( )
    {
        this.s = "";
        this.signature = null;
        this.o = null;
        
        this.temps_creation = System.nanoTime();       
    }
     public Message ( Object ob )
    {
        this.s = "";
        this.destinataire = null;
        this.signature = null;
        this.o = ob;
        
        this.temps_creation = System.nanoTime();       
    }
    public Message ( String s )
    {
        this.s = s;
        this.signature = null;
        this.o = null;
        
        this.temps_creation = System.nanoTime();       
    }
    public Message ( String s, Object sign, boolean signature )
    {
        this.s = s;
        this.signature = sign;
        this.o = null;
        
        this.temps_creation = System.nanoTime();      
    }
    
    public Message ( String s, Object o )
    {
        this.s = s;//Le contenu écrit du message.
        this.o = o;//Si l'on veut faire passer un objet en même temps.
        
        this.temps_creation = System.nanoTime();     
    }
    public Message ( String s, Object o, Object sign )
    {
        this.s = s;//Le contenu écrit du message.
        this.o = o;//Si l'on veut faire passer un objet en même temps.
        this.signature = sign;//Pour signer le message.
        
        this.temps_creation = System.nanoTime();       
        System.out.println("Temps de création du message : " + this.temps_creation);
    }
    
    public Message destinataire(Object dst)
    {
        this.destinataire = dst;
        return this;
    }
    
    public String code() 
    {
        return s;
    }
        
    public Object objet() 
    {
        return o;
    }
    
    @Override
    public String toString() 
    {
        return s;
    }
}
