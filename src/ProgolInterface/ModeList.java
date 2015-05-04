package ProgolInterface;


import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;
import java.awt.List;

/**
 * A Class representing a Vector of Mode declarations.
 * <P> The ModeList also keeps a Hashtable of Vectors that point 
 * to modes refering to the same predicates, accessed by the 
 * appropriate predicate symbols, and a Vector of pointers to 
 * head mode declarations. </P>
 * <P> The ModeList has been written so as to allow for multiple 
 * modes for the same predicate symbol, and for multiple headmode
 * declarations.  Neither of these are desirable in the Progol 
 * Interface setting. </P> 
 * @version 2.1
 * @author Rupert Parson
 * @see Mode
 */
public class ModeList extends Vector {
  private Hashtable symbols;
  private Vector headmodes; 
  /**
   * Basic constructor.
   * Initializes the symbols hashtable and headmodes vector.
   */
  public ModeList() {
    symbols = new Hashtable();
    headmodes = new Vector(5,1);
  }

  /**
   * Add a Mode to the ModeList.
   * Also updates the symbols table and the headmodes Vector.
   * @param md A Mode.
   */
  public final void addMode(Mode md) {
    super.addElement(md);
    String sym = md.predicateSymbol();
    if (!symbols.containsKey(sym)) {
      Vector v = new Vector(5,1);
      symbols.put(sym,v);
    }
    ((Vector) symbols.get(sym)).addElement(md);
    if (md.isHead()) {
      headmodes.addElement(md);
    } 
  }
  
  /**
   * Clears the ModeList.
   * Also clears the symbols table, and the headmodes Vector.
   */
  public final void removeAll() {
    super.removeAllElements();
    headmodes.removeAllElements();
    symbols.clear();
  }

  /**
   * Remove a Mode from the ModeList.
   * Also updates the symbols table and the headmodes Vector.
   * @param md A Mode.
   */
  public final void removeMode(Mode md) {
    super.removeElement(md);
    String sym = md.predicateSymbol();
    Vector v = (Vector) symbols.get(sym);
    v.removeElement(md);
    if (v.size() == 0) {
      symbols.remove(sym);
    }
    if (md.isHead()) {
      headmodes.removeElement(md);
    }     
  }

  /**
   * Remove the mode at a specific position in the 
   * Vector of modes. 
   * @param pos The position in the Vector.
   */
  public final void removeModeAt(int pos) {
    Mode md = (Mode) this.elementAt(pos);
    this.removeMode(md);
  }

  /**
   * Remove all the modes for the given predicate symbol.
   * @param sym A predicate symbol.
   */
  public final void removeModes(String sym) {
    if (symbols.containsKey(sym)) {
      Enumeration e = ((Vector) symbols.get(sym)).elements();
      while (e.hasMoreElements()) {
	Mode m = (Mode) e.nextElement();
	if (m.isHead()) {
	  headmodes.removeElement(m);
	}
	this.removeMode(m);
      }
      symbols.remove(sym);
    } 
  }

  /**
   * @return the Hashtable of symbol/mode declarations in this ModeList.
   */
  public final Hashtable symbols() { return symbols; }

  /**
   * @param sym A predicate symbol.
   * @return true if the ModeList holds a mode declaration for the given
   * predicate symbol.
   */
  public final boolean hasModeFor(String sym) { 
    return (symbols.containsKey(sym));
  }
  
  /**
   * @param sym A predicate symbol.
   * @return The first mode declaration for this predicate symbol.
   */
  public final Mode getModeFor(String sym) {
    return (Mode) ((Vector) symbols.get(sym)).firstElement();
  }
}
