package ProgolInterface;

import java.awt.*;
import java.io.*;
import java.util.*;
import PrologParse.*;

/**
 * A class that represents a Progol mode declaration.
 * The Class stores the mode type, predicate name and arity
 * and a Vector of mode arguments, of type ModeArg.
 * @author Rupert Parson
 * @version 2.0
 * @see ModeArg
 */
public class Mode {

  /**
   * A head mode.
   */
  static final int HEAD = 0;
  /**
   * A body mode.
   */
  static final int BODY = 1;

  private static String[] typeCode   = {"modeh", "modeb"};
  private static String[] typeString = {"head", "body"};
  private int modeType;
  private String recall;
  private String predicate;
  private int arity;
  private Vector arguments;

  /**
   * Constructs a Mode of the specified mode type, predicate and arity.
   * Since arguments are not specified, it fills them in with "+any"'s. 
   * @param mt The mode type.
   * @param pred The predicate of the mode (without arity).
   * @param arity The arity of the mode.
   */
  public Mode(int mt, String recall, String pred, int arity) {
    this.modeType = mt;
    this.recall = recall;
    this.predicate = pred;
    this.arity = arity;
    this.arguments = new Vector(arity);
    for (int i=0; i<arity; i++) { 
      this.arguments.addElement(new ModeArg("any", ModeArg.INPUT));
    }
  }

  /**
   * Constructs a Mode from a clause representation.
   * @param clause A Clause representation of the mode.
   */
  public Mode(Clause cl) {
    Enumeration e1 = cl.bodyTerms();
    Term t1, t2;
    while (e1.hasMoreElements()) {
      t1 = (Term) e1.nextElement();
      if (t1.name().equals("modeh")) { this.modeType = HEAD; }
      else if (t1.name().equals("modeb")) { this.modeType = BODY; }
      
      t2 = (Term) t1.subterms().firstElement();
      recall = t2.name();
      
      t2 = (Term) t1.subterms().elementAt(1);
      predicate = t2.name();

      this.arity = t2.subterms().size();
      this.arguments = new Vector(arity,0);

      Enumeration e2 = t2.subterms().elements();
      
      while (e2.hasMoreElements()) {
	t1 = (Term) e2.nextElement();
	this.addArgument(new ModeArg(t1));
      }
    } 
  }

  /**
   * @return the arity of the mode.
   */
  public final int arity() { return arity; }

  /**
   * @return the predicate symbol of the mode.
   */
  public final String predicateSymbol() { return (predicate + "/" + arity); }

  /**
   * @return the predicate (without arity) of the mode.
   */
  public final String predicate() { return predicate; }
  
  /**
   * @return the mode type of the mode.
   */
  public final String modeType() {
    return typeString[modeType];
  }

  /**
   * @return true, if the mode is a head mode.
   */
  public final boolean isHead() {
    return (modeType == HEAD);
  }

  /**
   * @return a Vector of the mode arguments.
   */
  public final Vector arguments() {
    return arguments;
  }

  private final void addArgument(ModeArg ma) {
    arguments.addElement(ma);
  }

  /**
   * Puts the specified ModeArg at the specified position.
   */
  public final void setArgumentAt(ModeArg ma, int pos) {
    arguments.setElementAt(ma,pos);
  }

  /**
   * @return the ModeArg at the specified position.
   */
  public final ModeArg ArgumentAt(int pos) {
    return (ModeArg) arguments.elementAt(pos);
  }

  /**
   * Places a List representation of the arguments of the mode in
   * the given java.awt.List.
   */
  public final void listArgs(java.awt.List list) {
    ModeArg ma;
    list.removeAll();
    Enumeration e = arguments.elements();
    while (e.hasMoreElements()) {
      ma = (ModeArg) e.nextElement();
      list.addItem(ma.toString());
    }
  }

  /**
   * @return a string representation of the Mode.
   */
  public final String toString() {    
    String s, r;
    
    s = ":- " + typeCode[modeType] + "(" + recall + ", " + predicate;

    Enumeration e = arguments.elements();
    
    if (e.hasMoreElements()) {
      r = ((ModeArg) e.nextElement()).toString();
      s = s + "(" + r;

      while (e.hasMoreElements()) {
	r = ((ModeArg) e.nextElement()).toString();
	s = s + ", " + r;
      }
      s = s + ")";
    }
    return s + ")?";
  }
}

