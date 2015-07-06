package ProgolInterface;



import ILP.ILPManager;
import java.awt.*;
import java.io.*;
import java.util.Enumeration;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import myawt.InfoDialog;
import myawt.GridBag;
import PrologParse.*;
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
  public ILPManager pm;
    
  public ModeList modes;
  protected ClauseList options;
  public ClauseList types;
  public ClauseList clauses;

  private JMenu ilpMenu, helpmenu;
  private JMenuItem newSession, loadSession, saveSession;
  private JMenuItem help, about;

  private JButton next, prev;  
  private JLabel label;
  private JPanel buttonpanel, cardpanel;
  
  private TypeSelectPanel typesPanel;
  private ModeSelectPanel modesPanel;
  private ClauseSelectPanel clausePanel;
  private ProgolExecPanel progolPanel;

  private CardLayout card = new CardLayout(10,10);
  private GridLayout grid = new GridLayout(1,2,20,40);
  private GridBagLayout gridbag = new GridBagLayout();

  private int panel = 1;
  
  private String ProgolPath = "/home/aurelien/Bureau/Progol/source/progol";//"/usr/jc/bin/progol";
  private String fileName;

    public String getFileName() 
    {
        return fileName;
    }
  
    public String getProgolPath() 
    {
        return ProgolPath;
    }

    public void setProgolPath(String progol_path) 
    {
        this.ProgolPath = progol_path;
    }
  
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
   */
  public ProgolInterface(FenetrePrincipale f) 
  {
    super();
    
    this.setName("Progol");
    
    this.f =f;
    this.pm = new ILPManager(this.f);
        
    this.setFont(new Font("Helvetica", Font.PLAIN, 12));
    this.setBackground(Color.white);
    
    modes = new ModeList();
    types = new ClauseList();
    options = new ClauseList();
    clauses = new ClauseList();

    typesPanel = new TypeSelectPanel(this);
    modesPanel = new ModeSelectPanel(this);
    clausePanel = new ClauseSelectPanel(this);
    progolPanel = new ProgolExecPanel(this);

    cardpanel = new JPanel();
    cardpanel.setBackground(Color.white);
    cardpanel.setLayout(card);
    cardpanel.add("North",typesPanel);
    cardpanel.add("North",modesPanel);
    cardpanel.add("North",clausePanel);
    cardpanel.add("North",progolPanel);

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

    public ProgolExecPanel getProgolPanel() {
        return progolPanel;
    }

    public ModeList getModes() {
        return modes;
    }

    public void setModes(ModeList modes) {
        this.modes = modes;
    }

    public ClauseList getOptions() {
        return options;
    }

    public void setOptions(ClauseList options) {
        this.options = options;
    }

    public ClauseList getTypes() {
        return types;
    }

    public void setTypes(ClauseList types) {
        this.types = types;
    }

    public ClauseList getClauses() {
        return clauses;
    }

    public void setClauses(ClauseList clauses) {
        this.clauses = clauses;
    }

  /**
   * Update all the panels in this session.
   */
  public final void updateAll() {
    typesPanel.update();
    modesPanel.update();
    clausePanel.update();
  }

  /**
   * Relabel the buttons
   */
  private final void label() {
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
   * Save the session in the given filename.
   * The file is written in the following order:
   * Options, 
   * Modes,
   * Types,
   * Other Clauses (examples and background knowledge.).
   * @param filename The file into which the session is written.
   */
  public final boolean saveSession(String filename) 
  {
    try {
      Enumeration enume;
      FileWriter ws = 
	new FileWriter(filename);
      enume = modes.elements();
      while (enume.hasMoreElements()) 
      {
	ws.write(((Mode) enume.nextElement()).toString() + "\n");
      }
      enume = types.elements();
      while (enume.hasMoreElements()) 
      {
	ws.write(((Clause) enume.nextElement()).toString() + "\n");
      }
      enume = clauses.elements();
      while (enume.hasMoreElements()) 
      {
	ws.write(((Clause) enume.nextElement()).toString() + "\n");
      }
      enume = options.elements();
      while (enume.hasMoreElements()) 
      {
	ws.write(((Clause) enume.nextElement()).toString() + "\n");
      }
      ws.close();
    }
    catch(IOException e) { System.out.println("Whoops: " + e.toString()); }

    return true;
  }

  /**
   * Load a session from the given filename.
   * The file is first scanned for QUERY type clauses - i.e.
   * modes and settings.  Then the file is scanned for types
   * and general clauses, the distinction being made on the basis
   * of the mode declarations (and other more obvious factors).
   * @param filename The file from which the session is loaded.
   */
  public final boolean loadSession(String filename) 
  {
    this.newSession();
    ClauseList file;
    Clause c;
    Mode m;
    Term t;
    Enumeration e1,e2,e3;
    String s;
    try {
      BufferedReader rs = new BufferedReader(new FileReader(filename));
      file = new PrologParser(rs);

      e1 = file.elements();
      while (e1.hasMoreElements()) {
	c = (Clause) e1.nextElement();

	if (c.punctuation() == Clause.QUERY) {
	  e2 = c.body().elements();
	  while (e2.hasMoreElements()) {
	    t = (Term) e2.nextElement();
	    s = " :- " + t.toString() + "?";
	    if (t.symbol().equals("modeh/2") || t.symbol().equals("modeb/2")) {
	      m = new Mode(new Clause(s));
	      clauses.addDefinition(m.predicateSymbol());
	      e3 = m.arguments().elements();
	      while (e3.hasMoreElements()) {
		String tp = ((ModeArg) e3.nextElement()).type();
		if (!tp.equals("any") &&
		    !tp.equals("int") && 
		    !tp.equals("float")) {
		  types.addDefinition(tp + "/1");
		}
	      }
	      modes.addMode(m);
	    }
	    else {
	      options.addElement(new Clause(s));
	    }
	  }			       
	}
      }

      e1 = file.elements();
      while (e1.hasMoreElements()) {
	c = (Clause) e1.nextElement();

	if (c.punctuation() == Clause.ASSERT) {
	  if ((c.head().functionArity() == 1) && 
	      !modes.hasModeFor(c.head().symbol()) &&
	      c.body().size() ==0) {
	    types.addElement(c);
	  }
	  else {
	    clauses.addElement(c);
	  }
	}
      }
      rs.close();
      this.fileName = filename;
    }
    catch(IOException e) { System.out.println("Whoops: " + e.toString()); }
    updateAll();
    return true;
  } 

   public void newSession()
   {
      panel = 1;
      label();
      this.f.ILP_Part.setSelectedComponent(this);
      card.first(cardpanel);
      modes.removeAll();
      types.removeAll();
      clauses.removeAll();
      options.removeAll();
      updateAll();
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
        this.newSession();
    }
    else if (event.getSource() == loadSession) {
      FileDialog fl = new FileDialog(this.f, "Load Session", FileDialog.LOAD);
      fl.pack();
      fl.setVisible(true);
      String fn = fl.getFile();
      String fd = fl.getDirectory();
      if (fn != null) {
	File fi = new File(fd,fn);
	if (fi.canRead()) {
	  loadSession(fd+fn);
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
	if (fi.canWrite()) {
	  saveSession(fd+fn);
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
  
  /**
   * Create a new ProgolInterface and show it.
   * A single command line parameter may be given, which should
   * be a filename for a session (or prolog) file.
   */  
  /*public final static void main(String[] args) {
    ProgolInterface session = new ProgolInterface("Progol Interface");
    if (args.length == 1) {
      File f = new File(args[0]);
      if (f.canRead()) {
	session.loadSession(args[0]);
      }
    }
    session.pack();
    session.setVisible(true);
  }*/
}
