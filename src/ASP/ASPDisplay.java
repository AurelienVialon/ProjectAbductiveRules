/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ASP;

import MVC.Controleur;
import MVC.Vue;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import myawt.GridBag;
import nomprol.Bouton;
import nomprol.FenetrePrincipale;

/**
 *
 * @author Aurélien Vialon
 */
public class ASPDisplay extends Vue
{
    private JTextArea ta;
    private FenetrePrincipale f;
    
    private JButton runB;
    
    private JMenu menu;
    JMenuItem loadFile;
    
    public ASPDisplay (FenetrePrincipale f, Controleur c)
    {
        super(c);
        this.f = f;
        this.ta = new JTextArea("ASP_Result",24,80);
        this.ta.setEditable(false);
                
        JScrollPane sp = new JScrollPane(this.ta, 
                                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        this.ta.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        this.runB = new Bouton("Run");
        this.runB.setName("ASPRun");
        this.runB.addActionListener(this);
        
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
        
        this.menu = new JMenu("ASP");
        loadFile = new JMenuItem("Load pl file");
        loadFile.addActionListener(this);
        this.menu.add(loadFile);
        
        f.ajouterMenuBar(menu);
    }

    @Override
    public void update(Observable o, Object ob) 
    {
        if(o instanceof ASPEngine)
        {
            this.ta.setText(ob.toString());
        }
    }
    
    @Override
    public final void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == loadFile) 
        {
          FileDialog fl = new FileDialog(this.f, "Load Session", FileDialog.LOAD);
          fl.pack();
          fl.setVisible(true);
          String fn = fl.getFile();
          String fd = fl.getDirectory();
          if (fn != null) 
          {
            File fi = new File(fd,fn);
            if (fi.canRead()) 
            {
              
            }
          }
        }
        else if(e.getSource() == this.runB)
        {
            this.c.reprise();
        }
    }
}
