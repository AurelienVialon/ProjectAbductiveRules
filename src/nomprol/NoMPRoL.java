/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomprol;

import ILP.SelfMadeEngine.Connaissances.BaseDeConnaissances;


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
        BaseDeConnaissances bc = new BaseDeConnaissances("/home/aurelien/Bureau/Projet/famille.pl");
        
        FenetrePrincipale f = new FenetrePrincipale();
        f.setVisible(true);             
    }    
}
