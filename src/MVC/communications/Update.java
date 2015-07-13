/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.communications;

/**
 *
 * @author Aurélien Vialon
 */
public class Update extends Message
{
    public String motif = "";
    
    Update(String Motif, Object pj)
    {
        super(pj);
        this.motif = Motif;
    }
}
