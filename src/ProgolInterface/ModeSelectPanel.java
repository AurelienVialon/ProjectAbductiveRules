package ProgolInterface;

import ILP.Engine.ILPMemory;
import ILP.Engine.ModeArg;
import ILP.Engine.Mode;
import ILP.ILPManager;
import myawt.GridBag;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/** 
 * A Panel to allow one to add modes and define the arguments.
 * Part of the Progol Interface package.
 * @author Rupert Parson
 * @version 2.0
 * @see ProgolInterface
 */
class ModeSelectPanel extends Panel 
implements ActionListener, ItemListener 
{
  private ILPMemory mem;
  private Mode currentmode;
  private java.awt.List modes, arguments;
  private TextField newmode, modetype;	
  private Panel lbtnpanel, rbtnpanel;
  private Panel lpanel, rpanel;
  private Button addmode, delmode, replacearg;
  private Choice arity, typechoice;
  private CheckboxGroup hbcheck;
  private Checkbox head, body;
//  private CheckboxGroup recallcheck;
//  private Checkbox starcheck, onecheck;
  private CheckboxGroup iocheck;
  private Checkbox incheck, outcheck, constcheck;

  private GridBagLayout gridbag = new GridBagLayout();
  private GridLayout hgrid = new GridLayout(1,2,20,40);

  /**
   * The constructor.
   * The parent ProgolInterface is passed to the panel
   * as a session so that the panel knows what to display
   * in its various Lists.
   * @param session  The ProgolInterface session.
   */
  public ModeSelectPanel(ILPManager man) 
  {
    this.mem = man.getILPMemory();

    currentmode = null;

    newmode = new TextField(15);
    newmode.addActionListener(this);
    modetype = new TextField(5);
    modetype.setEditable(false);
    
    addmode = new Button("Add Mode");
    addmode.addActionListener(this);
    delmode = new Button("Delete Mode");
    delmode.addActionListener(this);
    lbtnpanel = new Panel();
    lbtnpanel.setLayout(hgrid);
    lbtnpanel.add(addmode);
    lbtnpanel.add(delmode);
    
    replacearg = new Button("Replace selected argument");
    replacearg.addActionListener(this);
    
    arity = new Choice();
    arity.addItem("0");
    arity.addItem("1");
    arity.addItem("2");
    arity.addItem("3");
    arity.addItem("4");
    arity.addItem("5");
    arity.addItem("6");
    arity.addItem("7");
    arity.addItem("8");
    arity.addItem("9");

    this.typechoice = new Choice();

    modes = new java.awt.List(12,false);
    modes.addItemListener(this);

    arguments = new java.awt.List(12,false);
    arguments.addItemListener(this);

    hbcheck = new CheckboxGroup();
    head = new Checkbox("Head", hbcheck, true);
    body = new Checkbox("Body", hbcheck, false);

//  recallcheck = new CheckboxGroup();
//  starcheck = new CheckBox("*", recallcheck, false);
//  onecheck = new CheckBox("1", recallcheck, true);
    
    iocheck = new CheckboxGroup();
    incheck = new Checkbox("Input", iocheck, true);
    outcheck = new Checkbox("Output", iocheck, false);
    constcheck = new Checkbox("Constant", iocheck, false);   

    lpanel = new Panel();
    lpanel.setLayout(gridbag);
    GridBag.constrain(lpanel, new Label("Modes :"), 0, 0, 2, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 0, 0, 0);
    GridBag.constrain(lpanel, modes, 0, 1, 2, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 1.0, 0, 0, 10, 0);
    GridBag.constrain(lpanel, new Label("New Mode name :"), 0, 2, 2, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 0, 0, 0);
    GridBag.constrain(lpanel, newmode, 0, 3, 2, 1, 
		      GridBagConstraints.HORIZONTAL,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 0, 0, 8, 0);
    GridBag.constrain(lpanel, new Label("New Mode arity :"), 0, 4, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.WEST,
		      1.0, 0.0, 10, 0, 5, 0);
    GridBag.constrain(lpanel, arity, 1, 4, 1, 1, 
		      GridBagConstraints.NONE,
		      GridBagConstraints.WEST, 
		      1.0, 0.0, 10, 0, 5, 0);
    GridBag.constrain(lpanel, head, 0, 5, 1, 1, 
		      GridBagConstraints.NONE, 
		      GridBagConstraints.CENTER, 
		      1.0, 0.0, 10, 0, 10, 0); 
    GridBag.constrain(lpanel, body, 1, 5, 1, 1, 
		      GridBagConstraints.NONE, 
		      GridBagConstraints.WEST, 
		      1.0, 0.0, 10, 0, 10, 0);
    GridBag.constrain(lpanel, lbtnpanel, 0, 6, 2, 1, 
		      GridBagConstraints.HORIZONTAL,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 10, 0, 10, 0);

    rpanel = new Panel();
    rpanel.setLayout(gridbag);
    GridBag.constrain(rpanel, new Label("Arguments :"), 0, 0, 2, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 0, 0, 0);
    GridBag.constrain(rpanel, arguments, 0, 1, 2, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 1.0, 0, 0, 10, 0);
    GridBag.constrain(rpanel, new Label("Mode Position :"), 0, 2, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.WEST,
		      1.0, 0.0, 10, 0, 0, 0);
    GridBag.constrain(rpanel, modetype, 1, 2, 1, 1, 
		      GridBagConstraints.NONE, 
		      GridBagConstraints.WEST, 
		      1.0, 0.0, 10, 0, 0, 0);
    GridBag.constrain(rpanel, new Label("New Argument Type :"), 0, 3, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.WEST,
		      1.0, 0.0, 10, 0, 0, 0);
    GridBag.constrain(rpanel, typechoice, 1, 3, 1, 1, 
		      GridBagConstraints.NONE, 
		      GridBagConstraints.WEST, 
		      1.0, 0.0, 10, 0, 10, 0);
    GridBag.constrain(rpanel, new Label("New I/O Direction :"), 0, 4, 1, 3,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.WEST,
		      1.0, 0.0, 0, 0, 0, 0);
    GridBag.constrain(rpanel, incheck, 1, 4, 1, 1, 
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 0, 0, 0, 0); 
    GridBag.constrain(rpanel, outcheck, 1, 5, 1, 1, 
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 0, 0, 0, 0); 
    GridBag.constrain(rpanel, constcheck, 1, 6, 1, 1, 
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 0, 0, 0, 0); 
    GridBag.constrain(rpanel, replacearg, 0, 7, 2, 1, 
		      GridBagConstraints.HORIZONTAL,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 10, 0, 10, 0);

    this.setLayout(gridbag);
    GridBag.constrain(this, lpanel, 0, 0, 1, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 1.0, 0, 10, 0, 10); 
    GridBag.constrain(this, rpanel, 1, 0, 1, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHEAST, 
		      1.0, 1.0, 0, 10, 0, 10); 

    this.update();
  }
     

  /**
   * Update the Panel.
   * Ensures that the information displayed is the same as
   * the information stored in the ProgolInterface session.
   */
  public final void update() 
  {
    modetype.setText("");
    newmode.setEditable(true);
    lpanel.setEnabled(true);
    lbtnpanel.setEnabled(true);
    rpanel.setEnabled(false);
    replacearg.setEnabled(false);
    delmode.setEnabled(false);

    arguments.removeAll();

    typechoice.removeAll();
    typechoice.addItem("any");
    typechoice.addItem("int");
    typechoice.addItem("float");
    String s;
    Enumeration e = mem.getTypes().definitions().keys(); 
    while (e.hasMoreElements()) {
      s = (String) e.nextElement();
      s = s.substring(0,s.lastIndexOf("/"));
      typechoice.addItem(s);
    }

    modes.removeAll();
    for (e =mem.getModes().elements();
	 e.hasMoreElements();
	 modes.addItem(((Mode) e.nextElement()).predicateSymbol())) {}
  }


  /**
   * Handle Button press events.
   */
  public final void actionPerformed(ActionEvent event) 
  {
    String arg;
    int md;

    if (event.getSource() == delmode) {
      if ((md = modes.getSelectedIndex()) != -1) {
	mem.getModes().removeModeAt(md);
	modes.delItem(md);
	modetype.setText("");
	arguments.removeAll();
	rpanel.setEnabled(false);
	replacearg.setEnabled(false);
	delmode.setEnabled(false);
      }
    }
    else if (event.getSource() == addmode || event.getSource() == newmode) {
      String pred = newmode.getText();
      if (pred.length() > 0) {
	String symbol = pred + "/" + arity.getSelectedItem();
	modes.addItem(symbol);
	if (head.getState()) {
	  body.setState(true);
	  head.setEnabled(false);
	  currentmode = new Mode(Mode.HEAD, "1", pred, 
				 arity.getSelectedIndex());
	}
	else {
	  currentmode = new Mode(Mode.BODY, "*",
				 pred, arity.getSelectedIndex());
	}
	mem.getModes().addMode(currentmode);
	mem.getClauses().addDefinition(symbol);
	newmode.setText("");
      }
      newmode.requestFocus();
    }
    else if (event.getSource() == replacearg) {
      replacearg.setEnabled(false);
      int io;
      if (incheck.getState()) { io = ModeArg.INPUT; }
      else if (outcheck.getState()) { io = ModeArg.OUTPUT; }
      else { io = ModeArg.CONSTANT; }
      ModeArg ma = new ModeArg(typechoice.getSelectedItem(), io);
      currentmode.setArgumentAt(ma,arguments.getSelectedIndex());
      arguments.replaceItem(ma.toString(),arguments.getSelectedIndex());
    }
  }


  /**
   * Handle List selection events.
   */
  public final void itemStateChanged(ItemEvent event) {
    java.awt.List selected = (java.awt.List) event.getItemSelectable();

    if (event.getStateChange() == ItemEvent.SELECTED) {
      if (selected == arguments) {
	replacearg.setEnabled(true);
      }
      else if (selected == modes) {
	currentmode = 
	  ((Mode) mem.getModes().elementAt(selected.getSelectedIndex())); 
	currentmode.listArgs(arguments);
	modetype.setText(currentmode.modeType());
	replacearg.setEnabled(false);
	delmode.setEnabled(true);
	if (currentmode.arity() != 0) {
	  rpanel.setEnabled(true);
	}
      }
    }
    else if (event.getStateChange() == ItemEvent.DESELECTED) {
      if (selected == modes) {
	modetype.setText("");
	arguments.removeAll();
	rpanel.setEnabled(false);
	replacearg.setEnabled(false);
	currentmode = null;
      }
    }
  }
}
