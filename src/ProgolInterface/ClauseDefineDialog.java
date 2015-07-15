package ProgolInterface;

import ILP.Engine.ILPMemory;
import ILP.Engine.ModeArg;
import ILP.Engine.Mode;
import myawt.GridBag;
import PrologParse.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JFrame;
import nomprol.FenetrePrincipale;

/** 
 * A dialog to  user-define a clause.
 * If there is a mode declaration for the predicate Symbol given,
 * then the types of the mode declaration are used to provide
 * a simple user interface based on the user-defined types.
 * Otherwise the user can type in the arguments.
 * If no predicate symbol is given, then the user can
 * type the whole clause into a TextField.
 * @author Rupert Parson
 * @version 2.0
 * @see ProgolInterface
 */ 
class ClauseDefineDialog extends Dialog implements ActionListener 
{
  private FenetrePrincipale f;
  private ILPMemory mem; 
  
  private Button okay, cancel;
  private Checkbox positive, negative;
  private CheckboxGroup cbg;
  private GridBagLayout gridbag = new GridBagLayout();
  private GridLayout grid = new GridLayout(1,2,20,40);
  private Component clausedef;
  private boolean cancelled;
  private Mode mode;
  private int arity;
  private Component[] argcomps;

  /**
   * Creates a ClauseDefineDialog for the given predicate symbol
   * based on the modes and types in the given ProgolInterface session.
   * @param session The Progol Interface session.
   * @param symbol The predicate symbol of the clause to be defined.
   */
  public ClauseDefineDialog(FenetrePrincipale f,  String symbol, ILPMemory m) 
  {
    super((JFrame) f, true);

    this.f = f;
    this.mem = m;
    
    this.setLocation(new Point(f.getLocation().x+150,
			       f.getLocation().y+200));
    this.setLayout(gridbag);
    cancelled = false;

    okay = new Button("Okay");
    okay.addActionListener(this);
    cancel = new Button("Cancel");
    cancel.addActionListener(this);
    Panel p = new Panel();
    p.setLayout(grid);
    p.add(okay);
    p.add(cancel);

    if (symbol.equals("false/0"))
      {
	okay.setEnabled(false);
	clausedef = new TextArea("You can add negative examples\n" +
				 "by adding a clause for a predicate\n" +
				 "for which there is a head mode.",
				 4,30,TextArea.SCROLLBARS_NONE);
	((TextArea) clausedef).setEditable(false);
      }
    else if (!symbol.equals("")) {
      clausedef = new Panel();
      ((Panel) clausedef).setLayout(gridbag); 
      
      mode = getModeFor(symbol);
      arity = mode.arity();
      argcomps = new Component[arity];
      TextField t = new TextField(mode.predicate()+"(");
      t.setEditable(false);

      GridBag.constrain(((Panel) clausedef), t, 0, 0, 1, 1,
			GridBagConstraints.NONE, 
			GridBagConstraints.EAST,
			1.0, 0.0, 10, 10, 0, 0);
      
      for (int i=0; i<arity; i++) {
	String type = ((ModeArg) mode.arguments().elementAt(i)).type();
	if (type.equals("any")) {
	  argcomps[i] = new TextField("_", 10);
	}
	else if (type.equals("int")) {
	  argcomps[i] = new TextField("0", 3);
	}
	else if (type.equals("float")) {
	  argcomps[i] = new TextField("0.0", 4);
	}
	else {
	  argcomps[i] = new Choice();
	  Enumeration e = mem.getTypes().definition(type +"/1").elements();
	  while (e.hasMoreElements()) {
	    Clause c = (Clause) e.nextElement();
	    String s = 
	      ((Term) c.head().subterms().firstElement()).toString();
	    ((Choice) argcomps[i]).addItem(s);
	  }
	}
      	GridBag.constrain(((Panel) clausedef), argcomps[i], i+1, 0, 1, 1,
			  GridBagConstraints.NONE, 
			  GridBagConstraints.WEST,
			  1.0, 0.0, 10, 0, 0, 0);      
      }

      t = new TextField(").");
      t.setEditable(false);
      GridBag.constrain(((Panel) clausedef), t, arity+1, 0, 1, 1,
			GridBagConstraints.NONE, 
			GridBagConstraints.CENTER,
			1.0, 0.0, 10, 0, 0, 10);
      if (mode.isHead()) {
	cbg = new CheckboxGroup();
	positive = new Checkbox("Positive Example", cbg, true);
	negative = new Checkbox("Negative Example", cbg, false);
	GridBag.constrain(((Panel) clausedef), positive, 0, 1, arity+2, 1,
			  GridBagConstraints.NONE, 
			  GridBagConstraints.CENTER,
			  1.0, 0.0, 10, 0, 0, 0);      	  
	GridBag.constrain(((Panel) clausedef), negative, 0, 2, arity+2, 1,
			  GridBagConstraints.NONE, 
			  GridBagConstraints.CENTER,
			  1.0, 0.0, 0, 0, 0, 0);      	  
      }
    }
    else {
      clausedef = new TextField(40);
      ((TextField) clausedef).setEditable(true);
    }

    GridBag.constrain(this, new Label("New Clause Definition:"), 0, 0, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.CENTER,
		      1.0, 0.0, 15, 15, 5, 15);
    GridBag.constrain(this, clausedef, 0, 1, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.CENTER,
		      1.0, 0.0, 5, 15, 15, 15);
    GridBag.constrain(this, p, 0, 2, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.CENTER,
		      1.0, 0.0, 20, 20, 20, 20);

    this.pack();
  }

  private Mode getModeFor(String symbol) 
  {
    if (mem.getModes().hasModeFor(symbol)) 
    {
      return mem.getModes().getModeFor(symbol);    
    }
    else 
    {
      String name = symbol.substring(0,symbol.lastIndexOf("/"));
      int arity = 
	Integer.parseInt(symbol.substring(symbol.lastIndexOf("/")+1));
      return new Mode(Mode.BODY, "*", name, arity);
    }
  }

  /**
   * @return the clause defined by the user as a String.
   */
  public final String getClause() 
  {
    if (clausedef instanceof Panel) {
      String result;
      if (mode.isHead() && negative.getState()) { result = " :- "; }
      else { result = ""; }
      result = result + mode.predicate() + "(";
      for (int i=0; i<arity; i++) {
	if (argcomps[i] instanceof TextField) {
	  result = result + ((TextField) argcomps[i]).getText();
	}
	else {
	  result = result + ((Choice) argcomps[i]).getSelectedItem();
	}
	if (i<(arity-1)) { result = result + ", "; }
      }
      result = result + ").";
      return result;
    }
    else {
      return ((TextField) clausedef).getText();
    }
  }

  /**
   * @return true if the cancel button has been pressed.
   */
  public final boolean cancelled() { return cancelled; }

  /**
   * Handle button press events.
   */
  public final void actionPerformed(ActionEvent event) 
  {
    if (event.getSource() == okay) {
      this.setVisible(false);
      this.dispose();
    } 
    else if (event.getSource() == cancel) {
      cancelled = true;
      this.setVisible(false);
      this.dispose();
    }       
  }
}
