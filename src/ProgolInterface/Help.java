package ProgolInterface;

import myawt.GridBag;
import java.awt.*;
import java.awt.event.*;

public class Help extends Frame
implements ActionListener, ItemListener {
  
  private Button quit,next,previous;
  private Panel buttons;
  private List menu;
  private TextArea text;
  private GridBagLayout gridbag = new GridBagLayout();
  private GridLayout grid = new GridLayout(1,2,20,40);

  private int item = 0;
  private static String[] menuitems = {

"Introduction",
"    Progol",
"    The Interface",
"The Type Definition Panel",
"    Adding a new Type",
"    Deleting a Type",
"    Viewing the elements of a Type",
"    Adding an element to a Type",
"    Removing an element from a Type",
"The Mode Definition Panel",
"    Adding a new Mode",
"    Deleting a Mode",
"    Viewing the arguments of a Mode",
"    Changing an argument of a Mode",
"The Clause Definition Panel",
"    Viewing the definition of a predicate",
"    Adding a ground unit clause",
"    Adding a non-ground/non-unit clause",
"    Adding positive or negative examples",
"    Deleting a clause",
"The Progol Execution panel",
"    Runnning Progol on the current session",
"    The Progol output",
"The File Menu",
"    Starting a new session",
"    Loading a session",
"    Saving a session",
"    Quitting the interface"};
  
  private static String[] textitems = {

"Welcome to the Progol Interface.\n\n"
,
"Some stuff about Progol."
,
"The Progol interface is a Graphical User Interface for the Progol ILP " +
"algorithm."
,
"The Type Definition Panel \n\n" +
"The Type definition panel allows one to define the types used in the " +
"Progol session.\n\n"+
"A type is a predicate of arity 1, and its elements are defined by the " +
"ground unit clauses of that predicate. \n\n" +
"Types are necessary for the parametrisation of Mode declarations. The " +
"types defined on the Type definition panel are used in later stages of " +
"the Progol Interface."
,
"To add a new type, simply enter the name of the new type in the \"New " + 
"Type name\" box, and then click on [Add Type]. \n\n" +
"(Note that the type name must start with a lower case letter)."
,
"To remove a type, first click on the type name in the list of \"Defined " +
"Types\", and then click on [Delete Type]."
,
"To view the elements of a type, simply click on the type name in the "+
"list of \"Defined Types\", and the elements will appear in the " +
"\"Elements of the Type\" list."
,
"To add an element to a type, simply enter the name of the new element "+
"in the \"New Element\" box, and then click on [Add Element]. \n\n"+
"(Note that the element name must start with a lower case letter)."
,
"To remove an element from a type, first click on the type name in the "+
"list of \"Defined Types\".  The elements of that type will appear in the "+
"other list.  Then click on the element to be removed, and click the "+
"[Delete Element] button to remove it."
,
"The Mode Definition Panel\n\n" +
"The Mode definition panel allows one to define the modes used in the " +
"Progol session.\n\n"+
"A mode declaration is used by Progol in its generalisation procedure. " +
"Progol builds a \"bottom\" clause by saturating an example (a clause " +
"that corresponds to a head mode declaration) using the type " +
"information given in the head and body mode declarations. \n\n" + 
"(For a full explanation of mode declarations and their use, see " +
"Sam Roberts' Progol manual."
,
"To add a new mode, first enter the predicate name of the mode in the " +
"\"New Mode name\" box, and select the correct arity from the Arity " +
"selection choice menu. You must also select the intended position for " +
"that predicate (Head or Body).  Once you have made all your selections, " +
"click the [Add Mode] button.\n\n" +
"(Note that the mode predicate name must start with a lower case letter)." +
"\n\nAll the arguments in the new mode are initially set at \"+any\". " +
"See the section below on \"Changing the argument of a Mode\"."
, 
"To remove a mode, first click on the mode name in the list of \"Modes\", " + 
"and then click on [Delete Mode]."
,
"To view the arguments of a mode, simply click on the mode name in the "+
"list of \"Modes\", and the arguments will appear in the \"arguments\" " +
"list."
,
"To replace a Mode argument with a new Mode argument, first select the " +
"argument in the \"Arguments\" list.  Choose the new Type and I/O " +
"direction for the new argument from the choices offered, and then click " +
"[Replace selected argument]"
,
"The Clause Definition Panel\n\n" +
"The clause panel allows one to define the clauses used in the Progol " +
"session.  Ground unit clauses can be added, using type information if " +
"it exists.  Non-unit or non-ground clauses can be typed in by hand."
,
"To view all the clauses that define a particular predicate, simply click " +
"on the predicate name in the list of \"Predicates\", and the clauses " +
"will appear in the \"Clauses\" list."
,
"To add a new clause for a particular predicate, first select that " +
"predicate from the \"Predicates\" list.  Then click on the [Add Clause] " +
"button.  A new dialog window will appear. \n\nIf the predicate has a mode " +
"declaration, then the arguments of the clause will have types associated " +
"with them, and the dialog will take advantage of this by using choice " +
"selections for the entries in the clause. \n\nHowever, if no such type " +
"information is available, then the user is presented with boxes to " +
"fill by hand. \n\nOnce all the entries in the clause are filled " +
"satisfactorily, the click [okay].\n\nClick [cancel] at any time " +
"to abort the clause definition."
,
"To add a new non-ground or non-unit clause for a particular predicate, " +
"first select that predicate from the \"Predicates\" list.  Then click " +
"on the [Add non-unit Clause] button.  A new dialog window will appear.\n\n" +
"Type the new clause in the text window shown, and click [okay] to finish." +
"\n\nClick [cancel] at any time to abort the clause definition."
,
"To add a positive or negative example, select the predicate for which " +
"there is a head mode declaration, and then follow the procedure for " +
"adding a ground unit clause. (see above)."
,
"To delete a clause for a particular predicate, " +
"first select that predicate from the \"Predicates\" list.  Then select " +
"the appropriate clause from the \"Clauses\" list. Click [Delete Clause] " +
"to remove the clause."
,
"The Progol Execution Panel\n\n" +
"This panel allows the user to run the Progol algorithm on the current " + 
"session.  The output of the generalisation procedure is shown in an " +
"editable text window, and can be saved to file." 
,
"To run Progol on the current session, click on [Run Progol].  The " +
"generalistaion procedure can take a while, so please be patient and wait " +
"for it to stop."
,
"Progol's output is displayed in the \"Output\" window.  One can edit the " +
"output by clicking in this window and typing. \n\n Output can be saved " +
"to file by clicking on the [Save Output] button.  \n\n Clear the output " +
"window by clicking on the [Clear Output] button."
,
"The File Menu\n\n By using the file menu, one can save, load and start " +
"new sessions."
,
"To start a clean session, select \"New Session\" from the file menu."
,
"To load a session, choose \"Load Session\" from the file menu, and " +
"select the appropriate file from the file dialog presented to you."
,
"To save a session, choose \"Save Session\" from the file menu, and " +
"select the appropriate filename from the file dialog presented to you."
,
"To quit the interface, choose \"Quit\" from the file menu."
};

  public Help() {
    super("Progol Interface Help");
    this.setFont(new Font("Helvetica", Font.PLAIN, 12));
    text = new TextArea("",20,60,TextArea.SCROLLBARS_NONE);
    text.setEditable(false);

    menu = new List(20);
    menu.addItemListener(this);
    for (int i=0; i<menuitems.length; i++) { menu.addItem(menuitems[i]); }

    quit = new Button("Quit");
    quit.addActionListener(this);
    next = new Button("Next");
    next.addActionListener(this);
    previous = new Button("Previous");
    previous.addActionListener(this);
    previous.setEnabled(false);

    buttons = new Panel();
    buttons.setLayout(grid);
    buttons.add(previous);
    buttons.add(next);

    this.setLayout(gridbag);

    GridBag.constrain(this, 
		      new Label("Topics:                                                     "),
		      0, 0, 1, 1,
		      GridBagConstraints.BOTH, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 1.0, 10, 10, 0, 10);
    GridBag.constrain(this, menu, 0, 1, 1, 1,
		      GridBagConstraints.BOTH, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 1.0, 0, 10, 10, 10);
    GridBag.constrain(this, buttons, 0, 2, 1, 1,
		      GridBagConstraints.HORIZONTAL, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 0.0, 10, 10, 10, 10);
    GridBag.constrain(this, text, 1, 0, 1, 2,
		      GridBagConstraints.BOTH, 
		      GridBagConstraints.NORTHWEST,
		      1.0, 1.0, 10, 10, 10, 10);
    GridBag.constrain(this, quit, 1, 2, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.NORTHEAST,
		      1.0, 0.0, 10, 10, 10, 10);
    this.pack();
    showText();
  }

  private final void showText() {
    menu.select(item);
    menu.makeVisible(item);
    text.setText("\n" + textitems[item]);
  }    
  
  public final void actionPerformed(ActionEvent event) {
    if (event.getSource() == next) {
      item++;
      showText();
      previous.setEnabled(true);
      if (item == menuitems.length-1) {
	next.setEnabled(false);
      }
    }
    else if (event.getSource() == previous) {
      item--;
      showText();
      next.setEnabled(true);
      if (item == 0) {
	previous.setEnabled(false);
      }
    }
    else if (event.getSource() == quit) {
      this.setVisible(false);
      this.dispose();
    } 
  }

  public final void itemStateChanged(ItemEvent event) {
    if (event.getStateChange() == ItemEvent.SELECTED) {
      item = menu.getSelectedIndex();
      showText();
      if (item == menuitems.length-1) {
	next.setEnabled(false);
	previous.setEnabled(true);
      }
      else if (item == 0) {
	next.setEnabled(true);
	previous.setEnabled(false);
      }
      else {
	next.setEnabled(true);
	previous.setEnabled(true);
      }
    }
  }
  
  public final static void main(String[] args) {
      Help help = new Help();
      help.setVisible(true);
  }
}
