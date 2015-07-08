package ProgolInterface;

import ILP.Engine.ILPMemory;
import ILP.ILPManager;
import myawt.GridBag;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import PrologParse.*;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/** 
 * A Panel to allow one to add types and their elements.
 * Part of the Progol Interface package.
 * @author Rupert Parson
 * @version 2.0
 * @see ProgolInterface
 */
class TypeSelectPanel extends Panel implements ActionListener 
{
  private ILPMemory mem; 
  private JPanel lbtnpanel, rbtnpanel;
  private JPanel lpanel, rpanel;
  private JTextField newtype, newelt;
  private Button addtype, deltype, addelt, delelt;
  private JList types, elements;

  private GridBagLayout gridbag = new GridBagLayout();
  private GridLayout grid = new GridLayout(1,2,20,40);

  /**
   * The constructor.
   * The parent ProgolInterface is passed to the panel
   * as a session so that the panel knows what to display
   * in its various Lists.
   * @param session  The ProgolInterface session.
   */
  public TypeSelectPanel(ILPManager man) 
  {
    this.mem = man.getILPMemory();
    
    types = new JList();
    types.setBorder(BorderFactory.createLineBorder(Color.gray));
    types.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent mouseEvent)
            {
               ChangementElement(); 
            }
        });
    
    JScrollPane spt = new JScrollPane(types, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    elements = new JList();
    elements.setBorder(BorderFactory.createLineBorder(Color.gray));

    JScrollPane spe = new JScrollPane(elements, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    newtype = new JTextField();
    newtype.addActionListener(this);
    newelt = new JTextField();
    newelt.addActionListener(this);

    addtype = new Button("Add Type");
    addtype.addActionListener(this);
    deltype = new Button("Delete Type");
    deltype.addActionListener(this);
    lbtnpanel = new JPanel();
    lbtnpanel.setLayout(grid);
    lbtnpanel.add(addtype);
    lbtnpanel.add(deltype);
    lbtnpanel.setBackground(Color.white);

    addelt = new Button("Add Element");
    addelt.addActionListener(this);
    delelt = new Button("Delete Element");
    delelt.addActionListener(this);
    rbtnpanel = new JPanel();
    rbtnpanel.setLayout(grid);
    rbtnpanel.add(addelt);
    rbtnpanel.add(delelt);
    rbtnpanel.setBackground(Color.white);

    lpanel = new JPanel();
    lpanel.setBackground(Color.white);
    lpanel.setLayout(gridbag);
    GridBag.constrain(lpanel, new Label("Defined Types :"), 0, 0, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 0, 0, 0);
    GridBag.constrain(lpanel, spt, 0, 1, 1, 1,
		      GridBagConstraints.BOTH, 
		      GridBagConstraints.NORTHWEST, 
		      1.0, 1.0, 0, 0, 10, 0);
    GridBag.constrain(lpanel, new Label("New Type name :"), 0, 2, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 0, 0, 0);
    GridBag.constrain(lpanel, newtype, 0, 3, 1, 1,
		      GridBagConstraints.HORIZONTAL,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 0, 0, 10, 0);
    GridBag.constrain(lpanel, lbtnpanel, 0, 4, 1, 1, 
		      GridBagConstraints.HORIZONTAL,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 10, 0, 10, 0);

    rpanel = new JPanel();
    rpanel.setBackground(Color.white);
    rpanel.setLayout(gridbag);
    GridBag.constrain(rpanel, new Label("Elements of the Type :"), 0, 0, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 0, 0, 0);
    GridBag.constrain(rpanel, spe, 0, 1, 1, 1, 
		      GridBagConstraints.BOTH, 
		      GridBagConstraints.NORTHWEST, 
		      1.0, 1.0, 0, 0, 10, 0);
    GridBag.constrain(rpanel, new Label("New Element :"), 0, 2, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 0, 0, 0);
    GridBag.constrain(rpanel, newelt, 0, 3, 1, 1, 
		      GridBagConstraints.HORIZONTAL,
		      GridBagConstraints.NORTHWEST, 
		      1.0, 0.0, 0, 0, 10, 0);
    GridBag.constrain(rpanel, rbtnpanel, 0, 4, 1, 1, 
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
    newtype.setText("");
    newelt.setText("");
    newelt.setEditable(false);
    newtype.setEditable(true);
    elements.removeAll();
    types.removeAll();
    
    Vector<String> s = new Vector<>();
    s.add("any/1");
    s.add("int/1");
    s.add("float/1");
    
    for (Enumeration e = mem.getTypes().definitions().keys();
	 e.hasMoreElements();
	 s.add((String) e.nextElement())) {}
    
    types.setListData(s);
    
    this.ChangementElement();
  }

  /**
   * Handle Button press events.
   */
  @Override
  public final void actionPerformed(ActionEvent event) 
  {
    Vector<String> type,elt;

    type = new Vector<>();
    elt = new Vector<>();
    
    if (event.getSource() == deltype && (types.getSelectedIndex() > 2)) 
    {
      if (type.add((String)types.getSelectedValue()))
      {
	elements.removeAll();
	mem.getTypes().removeDefinition(type.firstElement());
        this.update();
      } 
      newelt.setEditable(false);
    }
    else if (event.getSource() == delelt) 
    {
      if (elements.getSelectedValue() != null) 
      {
        elt.add((String)elements.getSelectedValue());
        type.clear();
	type.add((String)types.getSelectedValue());
	Enumeration e = mem.getTypes().definition(type.firstElement()).elements();
        
	while (e.hasMoreElements()) 
        {
	  Clause c = (Clause) e.nextElement();
	  String s = ((Term) c.head().subterms().firstElement()).name();
	  if (elt.firstElement().equals(s)) 
          {              
	    mem.getTypes().removeElement(c);
            break;
	  }
	}
        this.ChangementElement();
      }
    }
    else if (event.getSource() == addtype || event.getSource() == newtype)
    {    
      String temp = newtype.getText();
      
      if (temp.length() > 0) 
      {
	if (!temp.endsWith("/1")) 
        {
          temp += "/1"; 
	}       
        mem.getTypes().addDefinition(temp);
        this.update();
        
	newtype.setText("");
      }
      newtype.requestFocus();
    }
    else if (event.getSource() == addelt || event.getSource() == newelt) 
    {
         Enumeration e = 
	    mem.getTypes().
	    definition((String)types.getSelectedValue()).elements();
	  while (e.hasMoreElements()) 
          {
	    Clause c = (Clause) e.nextElement();
            elt.add(((Term) c.head().subterms().firstElement()).toString());
	  }     
      elt.add(newelt.getText());
      if (newelt.getText().length() > 0) 
      {
        elements.setListData(elt);
	String s = (String)types.getSelectedValue();
	s = s.substring(0,s.lastIndexOf("/")) + "(" + newelt.getText() + ").";
	mem.getTypes().addElement(new Clause(s));
	newelt.setText("");
      }
      newelt.requestFocus();
    }
  }

  public void ChangementElement() 
  {
	elements.removeAll();
	if (types.getSelectedIndex() > 2) 
        {
	  newelt.setEditable(true);
	  Enumeration e = 
	    mem.getTypes().
	    definition((String)types.getSelectedValue()).elements();
          Vector<String> s = new Vector<>();
	  while (e.hasMoreElements()) 
          {
	    Clause c = (Clause) e.nextElement();
            s.add(((Term) c.head().subterms().firstElement()).toString());
	  }
          elements.setListData(s);
	} 
	else 
        {
          Vector <String> s = new Vector<>(); 
          s.add("*(Progol Built-in)*");
          
	  elements.setListData(s);
	  newelt.setEditable(false);
	}
  }
}
