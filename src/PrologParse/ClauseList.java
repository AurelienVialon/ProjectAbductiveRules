package PrologParse;


import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;
import java.awt.List;

/**
 * A Class representing a Vector of Clauses.
 * The ClassList also keeps a Hashtable of Vectors that point 
 * to the definitions of any defined predicates, accessed by the 
 * appropriate predicate symbols.
 * @version 1.1
 * @author Rupert Parson
 * @see PrologParse.Clause
 * @see PrologParse.PrologParser
 */
public class ClauseList extends Vector 
{
  private Hashtable definitions;
  /**
   * Basic constructor.
   * Initializes the definitions hashtable.
   */
  public ClauseList() 
  {
    definitions = new Hashtable();
  }

  /**
   * Add a Clause to the ClauseList.
   * Also updates the definitions table.
   * @param cl A Clause.
   */
  public final void addElement(Clause cl) 
  {
    super.addElement(cl);
    String sym = cl.predicateSymbol();
    if (!definitions.containsKey(sym)) 
    {
      Vector v = new Vector(10,10);
      definitions.put(sym,v);
    }
    ((Vector) definitions.get(sym)).addElement(cl);
  }

  
  /**
   * Remove a Clause from the ClauseList.
   * Also updates the definitions table.
   * @param cl A Clause.
   */
  public final void removeElement(Clause cl) 
  {
    super.removeElement(cl);
    String sym = cl.predicateSymbol();
    ((Vector) definitions.get(sym)).removeElement(cl);
  }


  /**
   * Clears the ClauseList.
   * Also clears the definitions table.
   */
  public final void removeAll() 
  {
    super.removeAllElements();
    definitions.clear();
  }

  /**
   * @return the Hashtable of definitions in this ClauseList.
   */
  public final Hashtable definitions() { return definitions; }

  /**
   * @param sym A predicate symbol.
   * @return the definition of the given predicate symbol.
   */
  public final Vector definition(String sym) 
  {
    if (definitions.containsKey(sym)) 
    {
      return ((Vector) definitions.get(sym));
    } 
    else 
    {
      return (new Vector(0,0));
    }
  }
  
  /**
   * Fills the given GUI List with the String representaion 
   * of the definition of the given predicate symbol.
   * @param sym A predicate symbol.
   * @param list A java.awt.List.
   * @see java.awt.List
   */
  public final void listDefinition(String sym, List list) 
  {
    list.removeAll();
    for (Enumeration e = definition(sym).elements();
	 e.hasMoreElements();
	 list.addItem(((Clause) e.nextElement()).toString())) {}
  }    

  /**
   * Add an empty definition to the definition Hashtable.
   * @param sym A predicate symbol.
   */
  public final void addDefinition(String sym) {
    if (!definitions.containsKey(sym)) {
      Vector v = new Vector(10,10);
      definitions.put(sym,v);
    }
  }

  /**
   * Remove the clause at a specific position in the 
   * Vector of clauses that defines the given predicate symbol.
   * @param sym A predicate symbol.
   * @param pos The position in the Vector.
   */
  public final void removeDefinitionAt(String sym, int pos) {
    if (definitions.containsKey(sym)) {
      this.removeElement(((Vector) definitions.get(sym)).elementAt(pos));
      ((Vector) definitions.get(sym)).removeElementAt(pos);
    }     
  }

  /**
   * Remove the entire definition of the given predicate symbol.
   * @param sym A predicate symbol.
   */
  public final void removeDefinition(String sym) {
    if (definitions.containsKey(sym)) {
      Enumeration e = ((Vector) definitions.get(sym)).elements();
      while (e.hasMoreElements()) {
	this.removeElement(e.nextElement());
      }
      definitions.remove(sym);
    } 
  }
}
