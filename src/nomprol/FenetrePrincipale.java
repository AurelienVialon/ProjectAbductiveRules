/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomprol;


import ASP.ASPManager;
import MVC.Controleur;
import ProgolInterface.ProgolInterface;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import myawt.GridBag;

/**
 *
 * @author Aurélien Vialon
 */
public class FenetrePrincipale extends JFrame
{
      private final JMenuBar menubar;
      private final JMenu actionmenu;
      
      private final JMenuItem quitter;
      
      private final JTabbedPane onglets;
      public final JTabbedPane ILP_Part;
      public final JTabbedPane ASP_Part;
      
      public final JComponent ILP_Engine;
      private final JPanel ILP_Results;
      public JList<String> ILP_Predicats_Results = null;
      public JTextArea ILP_Clauses_Results = null;
      
      private final JComponent ASP_Content;
      private final JComponent ASP_Results;
      
      private final JComponent Probabilistic_Engine;

    public FenetrePrincipale()
    {
        //Zone de définition de la fenêtre : 
        this.setName("NoMPRoL");
        this.setTitle(this.getName());
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        
        //Defintion de la fenêtre :
        this.setBackground(Color.white);
        
        //Définition du menu :
        this.menubar = new JMenuBar();
        
        //Définition du menu "Action" :
        this.actionmenu = new JMenu("Actions");
                
        this.quitter = new JMenuItem("Quit");
        this.quitter.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                System.exit(0);
            }
        });
        this.actionmenu.add(this.quitter);
        this.menubar.add(actionmenu);
        
        //Définition du gestionnaire d'onglets : 
        this.onglets = new JTabbedPane();
        this.onglets.setBackground(Color.white);
        this.onglets.setSize(WIDTH, 50);
        
        //Définition de l'onglet de gestion d'ILP :        
        this.ILP_Part = new JTabbedPane();
        this.ILP_Part.setName("ILP");
        this.ILP_Part.setBackground(Color.white);
        
        this.ILP_Predicats_Results = new JList ();
        this.ILP_Predicats_Results.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        JScrollPane sp1 = new JScrollPane(this.ILP_Predicats_Results, 
                                            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        this.ILP_Clauses_Results = new JTextArea ("");  
        this.ILP_Clauses_Results.setEditable(false);
        this.ILP_Clauses_Results.setBackground(Color.white);
        this.ILP_Clauses_Results.setBorder(BorderFactory.createLineBorder(Color.gray));

        JScrollPane sp2 = new JScrollPane(this.ILP_Clauses_Results, 
                                            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        this.ILP_Engine = new ProgolInterface(this);
        this.ILP_Engine.setBackground(Color.white);
        this.ILP_Part.add(this.ILP_Engine);
                
        ((ProgolInterface)this.ILP_Engine).loadSession("/home/aurelien/Bureau/Projet/famille.pl");
        //((ProgolInterface)this.ILP_Engine).loadSession("/home/aurelien/Bureau/Progol/examples4.2/animals.pl");
        //((ProgolInterface)this.ILP_Engine).loadSession("/home/aurelien/Bureau/Progol/examples4.2/chess.pl");
        
        this.ILP_Results = new JPanel();
        this.ILP_Results.setName("Results");
        
        this.ILP_Results.setLayout(new GridBagLayout());
        this.ILP_Results.setBackground(Color.white);
        
        JPanel p1 = new JPanel();
        p1.setBackground(Color.white);
                
        JPanel p2 = new JPanel();
        p2.setBackground(Color.white);
        
        p1.setLayout(new GridBagLayout());
        p2.setLayout(new GridBagLayout());
        
        GridBag.constrain(p1, new JLabel("Predicats :"), 0, 0, 2, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 0, 0, 0);
        GridBag.constrain(p1, sp1, 0, 1, 2, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 1.0, 0, 0, 10, 0);

        
        GridBag.constrain(p2, new JLabel("Rules :"), 0, 0, 2, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 0, 0, 0);
        GridBag.constrain(p2, sp2, 0, 1, 2, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 1.0, 0, 0, 10, 0);
        
        GridBag.constrain(this.ILP_Results, p1, 0, 0, 1, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 1.0, 0, 10, 0, 10); 
        GridBag.constrain(this.ILP_Results, p2, 1, 0, 1, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHEAST, 
		      1.0, 1.0, 0, 10, 0, 10); 
    
        this.ILP_Part.add(this.ILP_Results);
        
        //Définition de l'onglet de gestion de DLV : 
        this.ASP_Part = new JTabbedPane();
        
        this.ASP_Content = new Onglet();
            this.ASP_Content.setName("Input");
            this.ASP_Content.setLayout(new GridBagLayout());
        this.ASP_Results = new Onglet();
            this.ASP_Results.setName("Results");
            this.ASP_Results.setLayout(new GridBagLayout());
        
        this.ASP_Part.setName("ASP");
        
        //Zone de définition du MVC !
  
        Controleur ASP_Manager = new ASPManager();   
        GridBag.constrain(this.ASP_Results, ASP_Manager.Init(), 1, 0, 1, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHEAST, 
		      1.0, 1.0, 0, 10, 0, 10);
        
        this.ASP_Part.add(this.ASP_Content);
        this.ASP_Part.add(this.ASP_Results);       
        
        //Définition de l'onglet de gestion de PROGOL : 
        this.Probabilistic_Engine = new Onglet();
        this.Probabilistic_Engine.setName("Probabilities");
        
        //Ajout des onglets :
        this.ajouterOnglet(this.ILP_Part);       
        this.ajouterOnglet(this.ASP_Part);
        this.ajouterOnglet(this.Probabilistic_Engine);
      
        this.setJMenuBar(menubar);
        
        this.add(onglets);
        
        this.setSize(640, 480);
    }

    public void ajouterMenuBar ( JMenu m )
    {
        this.menubar.add(m);
    }
    public void ajouterAction ( JMenuItem mi )
    {
        this.actionmenu.add(mi);
    }
    
    //Les méthodes de gestion d'ajout d'onglets :
    public final void ajouterOnglet ( JComponent p )
    {
        this.onglets.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>" + p.getName() + "</body></html>", p);
    }    
    public void resetIPLResults ()
    {
        this.ILP_Predicats_Results.removeAll();
        this.ILP_Clauses_Results.removeAll();
    }
}
