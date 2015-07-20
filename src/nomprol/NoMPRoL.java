/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomprol;

import ILP.SelfMadeEngine.InstanceLogique;


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
        InstanceLogique il = new InstanceLogique("/home/aurelien/Bureau/Projet/famille.pl");
        
        System.out.println("Analyse des données débutée...");
            il.Analyse();
        System.out.println("Analyse terminée.");
        System.out.println("Affichage des nouvelles données : ");
        System.out.print(il.bc.afficheBaseDeConnaissance());
        
        FenetrePrincipale f = new FenetrePrincipale();
        f.setVisible(true);             
    }    
}