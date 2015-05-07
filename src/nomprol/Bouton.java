/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomprol;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author Aurelien Vialon
 */
public class Bouton extends JButton
{
    
    public Bouton ()
    {
        super();
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.setForeground(Color.gray);
    }
    public Bouton (String t)
    {
        super(t);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.setForeground(Color.gray);
    }
}
