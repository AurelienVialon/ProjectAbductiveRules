package PrologParse;

import java.io.IOException;
import java.util.Vector;
import java.util.Enumeration;

/**
 * Class that represents a Prolog clause (with '?' or '.' at end). 
 * A Clause is represented by a single head Term, and a Vector of
 * Terms in the body, with a punctuation mark at the end.
 *
 * A Clause can be constructed from the head/body/punctuation 
 * combination, or from a vector of tokens, or finally from a 
 * prolog String representation of the Clause.
 * @author Rupert Parson.
 * @version 1.1
 * @see PrologParse.Term
 * @see PrologParse.PrologParser
 */
public class Clause {
  private Term head;
  private Vector body;
  private int punctuation;
  
  /**
   * For a Clause that ends in a full-stop.
   */
  public static final int ASSERT = 0;
  /**
   * For a Clause that ends in a question mark 
   * The Clause should therefore also be headless.
   */
  public static final int QUERY  = 1;

  /**
   * Standard Constructor.
   * Constructs a Clause from the specified components.
   * @param head The head Term of the Clause.
   * @param body A Vector of terms in the body of the clause.
   * @param punctuation The punctuation mark at the end of the Clause, 
   * either Clause.ASSERT or Clause.QUERY
   */
  public Clause(Term head, Vector body, int punctuation) {
    this.head = head;
    this.body = body;
    this.punctuation = punctuation;
  }

  /**  
   * Constructor that parses a vector of tokens to construct the Clause.
   * @param ts A Vector of Tokens, as produced by a PrologTokenizer.
   * @see PrologParse.Token
   * @see PrologParse.PrologTokenizer
   */
  public Clause(Vector ts) {
    Vector subtokens = new Vector(50,50);
    boolean in_body = false;
    int bracket_depth = 0;
    Enumeration e = ts.elements();
    Token t;
    this.body = new Vector(10,10);

    while (e.hasMoreElements()) {
      t = (Token) e.nextElement();
      if (bracket_depth == 0) {
	switch (t.type()) {

	case Token.OPEN: case Token.OPENSQ:
	  bracket_depth++;
	  subtokens.addElement(t);
	  break;

	case Token.IF:
	  this.head = new Term(subtokens);
	  subtokens.removeAllElements();
	  in_body = true;
	  break;

	case Token.COMMA:
	  this.body.addElement(new Term(subtokens));
	  subtokens.removeAllElements();
	  break;

	case Token.FSTOP:
	  if (in_body) { this.body.addElement(new Term(subtokens)); }
	  else { this.head = new Term(subtokens); }
	  this.punctuation = ASSERT;
	  subtokens.removeAllElements();
	  break;

	case Token.QMARK:
 	  if (in_body) { this.body.addElement(new Term(subtokens)); }
 	  else { this.head = new Term(subtokens); }
 	  this.punctuation = QUERY;
	  subtokens.removeAllElements();
	  break;

	default:
	  subtokens.addElement(t);
	}
      } else {
	switch (t.type()) {

	case Token.OPEN: case Token.OPENSQ:
	  bracket_depth++;
	  subtokens.addElement(t);
	  break;

	case Token.CLOSE: case Token.CLOSESQ:
	  bracket_depth--;
	  subtokens.addElement(t);
	  break;

	default:
	  subtokens.addElement(t);
	}
      }
    }
  }

  /**
   * Constructor that parses a String to construct the Clause.
   * @param clause A string representation of the Clause.
   */
  public Clause(String clause) { 
    PrologTokenizer ts = new PrologTokenizer(clause);
    Vector subtokens = new Vector(50,50);
    boolean in_body = false;
    int bracket_depth = 0;
    this.body = new Vector(10,10);
    
    try {
      Token t = ts.nextToken();
      while (t.type() != Token.EOF) {
	if (bracket_depth == 0) {
	  switch (t.type()) {

	  case Token.OPEN: case Token.OPENSQ:
	    bracket_depth++;
	    subtokens.addElement(t);
	    break;

	  case Token.IF:
	    this.head = new Term(subtokens);
	    subtokens.removeAllElements();
	    in_body = true;
	    break;

	  case Token.COMMA:
	    this.body.addElement(new Term(subtokens));
	    subtokens.removeAllElements();
	    break;

	  case Token.FSTOP:
	    if (in_body) { this.body.addElement(new Term(subtokens)); }
	    else { this.head = new Term(subtokens); }
	    this.punctuation = ASSERT;
	    subtokens.removeAllElements();
	    break;

	  case Token.QMARK:
	    if (in_body) { this.body.addElement(new Term(subtokens)); }
	    else { this.head = new Term(subtokens); }
	    this.punctuation = QUERY;
	    subtokens.removeAllElements();
	    break;

	  default:
	    subtokens.addElement(t);
	  }
	} else {
	  switch (t.type()) {

	  case Token.OPEN: case Token.OPENSQ:
	    bracket_depth++;
	    subtokens.addElement(t);
	    break;

	  case Token.CLOSE: case Token.CLOSESQ:
	    bracket_depth--;
	    subtokens.addElement(t);
	    break;

	  default:
	    subtokens.addElement(t);
	  }
	}
	t = ts.nextToken();
      }
    }
    catch (IOException ex) {}
  }

  /**
   * @return a string representation of the Clause.
   */
  public final String toString() {
    String out;
    Enumeration e = body.elements();
    if (head.name()=="false") {
      out = "";
    } else {
      out = head.toString();
      if (e.hasMoreElements()) { out = out + " "; }
    }
    if (e.hasMoreElements()) {
      out = out + ":- " + ((Term) e.nextElement()).toString();
      while (e.hasMoreElements()) {
	out = out + ", " + ((Term) e.nextElement()).toString();
      }
    }
    if (punctuation == ASSERT) { out = out + "."; }
    else { out = out + "?"; }

    return out;
  }

  /** 
   * @return the head of the clause
   */
  public final Term head() { return head; }
  /**
   * @return the predicate symbol for this Clause.
   */
  public final String predicateSymbol() { return head.symbol(); }
  /**
   * @return the body of this clause.
   */
  public final Vector body() { return body; }
  /**
   * @return an enumeration of the Terms in the body of this clause.
   */
  public final Enumeration bodyTerms() { return body.elements(); }
  /**
   * @return the punctuation at the end of this clause.
   */
  public final int punctuation() { return punctuation; }
}
