package IA;


import MVC.Modele;
import MVC.communications.Message;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aurélien Vialon
 */
public abstract class Agent extends Modele
{
    protected int id;
    protected String nom;
    protected Stack<Message> boite_a_messages;
    
    public Agent ()
    {
        super();
        this.id = 0;
        this.nom = "";
    }
    public Agent (int i)
    {
        super();
        this.id = i;
        this.nom = "";
    }
    public Agent (int i, String n)
    {
        super();
        this.id = i;
        this.nom = n;
    }
    protected Message dernier_message ()
    {
        return this.boite_a_messages.pop();
    }
    public void Depose_message (Message m)
    {
        this.boite_a_messages.push(m);
    }
}
