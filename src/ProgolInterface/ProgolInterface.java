package ProgolInterface;


import ILP.Engine.ILPMemory;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import myawt.InfoDialog;
import myawt.GridBag;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import nomprol.Bouton;
import nomprol.FenetrePrincipale;
import nomprol.Onglet;

/**
 * The Progol Interface Class - An interface for Progol! 
 * This class holds all the subsection panels, and keeps
 * a central record of the session types, modes, clauses etc.
 * @author Rupert Parson
 * @version 2.0
 * @see TypeSelectPanel
 * @see ModeSelectPanel
 * @see ClauseSelectPanel
 * @see ProgolExecPanel
 */
public class ProgolInterface extends Onglet implements ActionListener 
{ 
  public FenetrePrincipale f;  
  private ILPMemory mem;
  
  private JMenu ilpMenu, helpmenu;
  private JMenuItem newSession, loadSession, saveSession;
  private JMenuItem help, about;

  private JButton next, prev;  
  private JLabel label;
  private JPanel buttonpanel, cardpanel;
  
  private TypeSelectPanel typesPanel;
  private ModeSelectPanel modesPanel;
  private ClauseSelectPanel clausePanel;

  private CardLayout card = new CardLayout(10,10);
  private GridLayout grid = new GridLayout(1,2,20,40);
  private GridBagLayout gridbag = new GridBagLayout();

  private int panel = 1;
    
  private static String[] labels = {"",
				    "Type Definition",
				    "Mode Definition",
				    "Clause Definition",
				    "Progol Execution",
				    ""};
  /** 
   * Constructor to start the session.
   * The constructor adds in the various section panels, and 
   * adds buttons to flip between them.  All the mode and clause
   * storage objects are initialised to be empty.
     * @param f main frame of the JAVA program
   */
  public ProgolInterface(FenetrePrincipale f) 
  {
    super();
    
    this.setName("Progol");
    
    this.f =f;
    this.mem = f.ILP_Manager.getILPMemory();
    
    this.setFont(new Font("Helvetica", Font.PLAIN, 12));
    this.setBackground(Color.white);

    typesPanel = new TypeSelectPanel(this.f.ILP_Manager);
    modesPanel = new ModeSelectPanel(this.f.ILP_Manager);
    clausePanel = new ClauseSelectPanel(this.f);

    cardpanel = new JPanel();
    cardpanel.setBackground(Color.white);
    cardpanel.setLayout(card);
    cardpanel.add("North",typesPanel);
    cardpanel.add("North",modesPanel);
    cardpanel.add("North",clausePanel);
    cardpanel.add("North",f.ILP_Display);

    buttonpanel = new JPanel();
    buttonpanel.setBackground(Color.white);
    buttonpanel.setLayout(grid);
    
    next = new Bouton("");
    prev = new Bouton("");
    
    next.addActionListener(this);
    prev.addActionListener(this);  
    
    label = new JLabel();
    
    label.setAlignmentX(CENTER_ALIGNMENT);
    label.setAlignmentY(CENTER_ALIGNMENT);
    
    buttonpanel.add(prev);
    buttonpanel.add(label);
    buttonpanel.add(next);
    label();

    newSession = new JMenuItem("New Session");
    newSession.addActionListener(this);
    loadSession = new JMenuItem("Load Session");
    loadSession.addActionListener(this);
    saveSession = new JMenuItem("Save Session");
    saveSession.addActionListener(this);

    this.ilpMenu = new JMenu ("ILP");
        this.ilpMenu.add(newSession);
        this.ilpMenu.add(loadSession);
        this.ilpMenu.add(saveSession);
    this.f.ajouterMenuBar(ilpMenu);
    /*
    help = new JMenuItem("Help");
    help.addActionListener(this);
    about = new JMenuItem("About");
    about.addActionListener(this);
    helpmenu = new JMenu("Help");
    helpmenu.add(help);
    helpmenu.add(about);

    f.ajouterMenuBar(helpmenu);
        */
      
    this.setLayout(gridbag);
    
    GridBag.constrain(this, cardpanel, 0, 0, 1, 1, 
	      GridBagConstraints.BOTH, 
	      GridBagConstraints.CENTER,
	      1.0, 1.0, 0, 0, 0, 0);
    GridBag.constrain(this, buttonpanel, 0, 1, 1, 1, 
	      GridBagConstraints.HORIZONTAL, 
	      GridBagConstraints.CENTER,
	      1.0, 0.0, 10, 10, 10, 10);
  }
  /**
   * Update all the panels in this session.
   */
  public final void updateAll() 
  {
    typesPanel.update();
    modesPanel.update();
    clausePanel.update();
  }

  /**
   * Relabel the buttons
   */
  private final void label() 
  {
    prev.setLabel("<<  " + labels[panel-1]);
    label.setText(labels[panel]);
    next.setLabel(labels[panel+1] + "  >>");
    
    if (panel == 1) {
      prev.setEnabled(false);
      next.setEnabled(true);
    }
    else if (panel == labels.length-2) {
      prev.setEnabled(true);
      next.setEnabled(false);
    }
    else {
      prev.setEnabled(true);
      next.setEnabled(true);
    }
  }
  /**
   * Event handling for buttons and menus.
     * @param event
   */
  @Override
  public final void actionPerformed(ActionEvent event) 
  {
    if (event.getSource() == next) {
      panel++;
      label();
      card.next(cardpanel);
      updateAll();
    }
    else if (event.getSource() == prev) {
      panel--;
      label();
      card.previous(cardpanel);
      updateAll();
    }
    else if (event.getSource() == newSession) 
    {
      this.mem.Init();
        
      panel = 1;
      label();
      this.f.ILP_Part.setSelectedComponent(this);
      card.first(cardpanel);
      updateAll();
    }
    else if (event.getSource() == loadSession) 
    {
      FileDialog fl = new FileDialog(this.f, "Load Session", FileDialog.LOAD);
      fl.pack();
      fl.setVisible(true);
      String fn = fl.getFile();
      String fd = fl.getDirectory();
      if (fn != null) {
	File fi = new File(fd,fn);
	if (fi.canRead()) 
        {
	  this.mem.Load(fd+fn);
	}
      }
      updateAll();
    }
    else if (event.getSource() == saveSession) 
    {
      FileDialog fs = new FileDialog(this.f, "Save Session", FileDialog.SAVE);
      fs.pack();
      fs.setVisible(true);
      String fn = fs.getFile();
      String fd = fs.getDirectory();
      if (fn != null) {
	File fi = new File(fd,fn);
	if (fi.canWrite()) 
        {
	  this.mem.Save(fd+fn);
	}
      }
    }
    else if (event.getSource() == help) 
    {
      Help help = new Help();
      help.setVisible(true);
    }
    else if (event.getSource() == about)
    {
      InfoDialog info = new InfoDialog(this.f,"About Progol Inteface", 
				       "Progol Interface Version 2.0",
			    "Copyright (c) 1997 Rupert Parson");
      info.setVisible(true);
    }
  }
}
