/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ASP;

import MVC.Controleur;
import MVC.Vue;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import myawt.GridBag;
import nomprol.Bouton;

/**
 *
 * @author Aurélien Vialon
 */
public class ASPDisplay extends Vue
{
    private JTextArea ta;
    
    private JButton runB;
    
    public ASPDisplay (Controleur c)
    {
        this.ta = new JTextArea("ASP_Result",24,80);
        this.ta.setEditable(false);
                
        JScrollPane sp = new JScrollPane(this.ta, 
                                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        this.ta.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        this.runB = new Bouton("Run");
        this.runB.setName("ASPRun");
        this.runB.addActionListener(c);
        
        this.setLayout(new GridBagLayout());
                this.setBackground(Color.white);
        
        //Area to add the differents components of the View.        
        GridBag.constrain(this, sp, 0, 1, 4, 1, 
                        GridBagConstraints.BOTH, 
                        GridBagConstraints.CENTER,
                        1.0, 1.0, 1, 1, 1, 1);
        GridBag.constrain(this, this.runB, 0, 2, 1, 1, 
                        GridBagConstraints.BOTH, 
                        GridBagConstraints.CENTER,
                        1.0, 0.2, 1, 1, 1, 1);           
    }

    @Override
    public void update(Observable o, Object ob) 
    {
        if(o instanceof ASPEngine)
        {
            this.ta.setText(ob.toString());
        }
    }
}
