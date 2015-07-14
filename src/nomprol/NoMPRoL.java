/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomprol;

import ILP.SelfMadeEngine.Basiques.Predicat;



/**
 *
 * @author Aurélien Vialon
 */
public class NoMPRoL 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Predicat p = new Predicat("Parent_of(Parent, Child):-father_of(Parent, Child).");

        FenetrePrincipale f = new FenetrePrincipale();
        f.setVisible(true);             
    }    
}
