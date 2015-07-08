package ProgolInterface;

import ILP.Engine.ILPMemory;
import ILP.ILPManager;
import myawt.GridBag;
import PrologParse.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import nomprol.FenetrePrincipale;

/** 
 * A Panel to allow one to add and view Clauses by predicate symbol.
 * Part of the Progol Interface package.
 * @author Rupert Parson
 * @version 2.0
 * @see ProgolInterface
 */
class ClauseSelectPanel extends JPanel implements ActionListener, ItemListener 
{
  private FenetrePrincipale f;
  private final ILPMemory mem;
  private final java.awt.List predicates, clauses;
  private final JButton add, delete, manual;
  private final JPanel buttonpanel;
  private final GridBagLayout gridbag = new GridBagLayout();
  private final GridLayout grid = new GridLayout(1,2,20,40);

  /**
   * The constructor.
   * The parent ProgolInterface is passed to the panel
   * as a session so that the panel knows what to display
   * in its various Lists.
   * @param session  The ProgolInterface session.
   */
  public ClauseSelectPanel(FenetrePrincipale fen) 
  {
    this.f = fen;
    this.mem = this.f.ILP_Manager.getILPMemory();
    this.setBackground(Color.white);
    
    predicates = new java.awt.List(15,false);
    predicates.addItemListener(this);
    clauses = new java.awt.List(13,false);
    clauses.addItemListener(this);

    manual = new JButton("Add non-unit Clause");
    manual.addActionListener(this);
    manual.setEnabled(true);

    add = new JButton("Add Clause");
    add.addActionListener(this);
    delete = new JButton("Delete Clause");
    delete.addActionListener(this);

    buttonpanel = new JPanel();
    buttonpanel.setLayout(grid);
    buttonpanel.add(add);
    buttonpanel.add(delete);

    this.setLayout(gridbag);
    GridBag.constrain(this, new Label("Predicates :"), 0, 0, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 10, 0, 10);
    GridBag.constrain(this, predicates, 0, 1, 1, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 1.0, 0, 10, 10, 10);
    GridBag.constrain(this, manual, 0, 2, 1, 1, 
		      GridBagConstraints.HORIZONTAL,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 10, 10, 10, 10);
    GridBag.constrain(this, new Label("Clauses :"), 1, 0, 1, 1, 
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 10, 0, 10);
    GridBag.constrain(this, clauses, 1, 1, 1, 1, 
		      GridBagConstraints.BOTH,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 1.0, 0, 10, 10, 10);
    GridBag.constrain(this, buttonpanel, 1, 2, 1, 1, 
		      GridBagConstraints.HORIZONTAL,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 10, 10, 10, 10);

    this.update();
  }

  /**
   * Update the Panel.
   * Ensures that the information displayed is the same as
   * the information stored in the ProgolInterface session.
   */
  public final void update() {
    add.setEnabled(false);
    delete.setEnabled(false);
    predicates.removeAll();
    clauses.removeAll();
    for (Enumeration e = mem.getClauses().definitions().keys();
	 e.hasMoreElements();
	 predicates.addItem((String) e.nextElement())) {}
  }

  /**
   * Handle Button press events.
   */
  public final void actionPerformed(ActionEvent event) {
    if (event.getSource() == manual || event.getSource() == add) {
      String s;
      if (event.getSource() == manual) { s = ""; }
      else { s = predicates.getSelectedItem(); }
      ClauseDefineDialog cdd = new ClauseDefineDialog(f, mem, s);
      cdd.setVisible(true);;
      if (!cdd.cancelled()) {
	String cl = cdd.getClause();
	Clause cls = new Clause(cl);
	mem.getClauses().addElement(cls);
	if (event.getSource() == manual) { update(); }
	else { clauses.addItem(cl);}
      }
    }
    else if (event.getSource() == delete) {
      int pos = clauses.getSelectedIndex();
      mem.getClauses().removeDefinitionAt(predicates.getSelectedItem(), pos);
      clauses.delItem(pos);
      delete.setEnabled(false);
    }
  }

  /**
   * Handle List selection events.
   */
  public final void itemStateChanged(ItemEvent event) {
    java.awt.List selected = (java.awt.List) event.getItemSelectable();
    if (event.getStateChange() == ItemEvent.SELECTED) {
      if (selected == predicates) {
	mem.getClauses().listDefinition(predicates.getSelectedItem(), clauses);
	add.setEnabled(true);
	delete.setEnabled(false);
      }
      else if (selected == clauses) {
	delete.setEnabled(true);
      }
    }
    else if (event.getStateChange() == ItemEvent.DESELECTED) {
      if (selected == predicates) {
	clauses.removeAll();
	add.setEnabled(false);
	delete.setEnabled(false);
      }
      else if (selected == clauses) {
	delete.setEnabled(false);	
      }
    }
  }
}
