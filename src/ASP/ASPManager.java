/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ASP;

import MVC.Controleur;
import MVC.Modele;
import MVC.Vue;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
/**
 *
 * @author Aurélien Vialon
 */
public class ASPManager extends Controleur
{           
    public ASPManager()
    {
	    
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() instanceof JButton && "ASPRun".equals(((JButton)e.getSource()).getName()))
        {
            this.m.retour();
        }
    }

    @Override
    protected Modele initModele() 
    {
        return new ASP.ASPEngine("/home/aurelien/Bureau/DLV/dlv", "/home/aurelien/Bureau/DLV/famille.pl");
    }

    @Override
    protected Vue initVue() 
    {
        return new ASP.ASPDisplay(this);
    }
}
