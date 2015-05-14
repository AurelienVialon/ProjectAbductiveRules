/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomprol;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author Aurélien Vialon
 */
public class Bouton extends JButton
{
    
    public Bouton ()
    {
        super();
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.setForeground(Color.gray);
        
        this.initMouseActions();
    }
    public Bouton (String t)
    {
        super(t);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.setForeground(Color.gray);  
        
        this.initMouseActions();
    }
    
    protected final void initMouseActions ()
    {
        this.addMouseListener(new MouseListener() 
        { 
            @Override
            public void mouseClicked(MouseEvent e){}
            @Override
            public void mousePressed(MouseEvent e){}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e)
            {
                ((Bouton)e.getSource()).setBackground(Color.gray);
                ((Bouton)e.getSource()).setForeground(Color.white);
            }
            @Override
            public void mouseExited(MouseEvent e)
            {   
                ((Bouton)e.getSource()).setBackground(Color.white);
                ((Bouton)e.getSource()).setForeground(Color.gray);
            }});
    }
}
