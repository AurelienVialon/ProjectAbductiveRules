package PrologParse;


import java.util.Vector;
import java.util.Enumeration;

/**
 * A class that represents a Term in prolog.
 * A Term is represented by a type int, a string that represents 
 * the name/text of the term, and a Vector of subterms.
 * @author Rupert Parson
 * @version 1.1
 * @see PrologParse.Clause
 */
public class Term extends Object {
  public static final int FUNC = 0;
  public static final int VAR   = 1;
  public static final int NUM   = 2;
  public static final int OP    = 3;
  public static final int LIST  = 4;
  public static final int CUT   = 5;

  private static final int DEFAULT  = 0;
  private static final int LISTHEAD = 1;
  private static final int LISTTAIL = 2;
  private static final int LISTREST = 3;
  private static final int SUBLIST  = 4;
  private static final int FUNCBODY = 5;
  private static final int SUBEXPR  = 6;

  private String name;
  private Vector subterms;
  private int type;
  private int precedence;

  /**
   * Most General constructor.
   * @param name The text part of the Term.
   * @param type The type of the Term (variable, function symbol, etc).
   * @param subterms The subterms of the Term.
   */
  public Term(String name, int type, Vector subterms, int precedence) {
    this.name = name;
    this.type = type;
    this.subterms = subterms;
    this.precedence = precedence;
  }
  
  /**
   * Constructor for constants or variables.
   * Constructs a Term with no subterms of the specified type.
   * @param name The text part of the Term.
   * @param type The type of the Term (variable, function symbol, etc).
   */
  public Term(String name, int type) {
    this.name = name;
    this.type = type;
    this.subterms = new Vector(0,0);
    this.precedence = 0;
  }

  /**
   * Constructor for functions (atoms).
   * Constructs a Term with specified subterms of type Term.FUNC.
   * @param name The text part of the Term.
   * @param subterms The subterms of the Term.
   */
  public Term(String name, Vector Subterms) {
    this.name = name;
    this.type = FUNC;
    this.subterms = subterms;
    this.precedence = 0;
  }  

  /**
   * Constructs a Term by parsing some tokens.
   * @param ts A Vector of Tokens, as produced by a PrologTokenizer.
   * @see PrologParse.PrologTokenizer
   * @see PrologParse.Token
   */
  public Term(Vector ts) {
    
    Vector subtokens = new Vector(20,20);
    Enumeration e = ts.elements();
    Token t;

    int state = DEFAULT;
    int last_state = DEFAULT;
    int bracket_depth = 0;

    if (!e.hasMoreElements()) {
      this.name = "false";
      this.type = FUNC;
      this.subterms = new Vector(0,0);
      this.precedence = 0;
    }
    else {
      this.name = "";

      while (e.hasMoreElements()) {
	t = (Token) e.nextElement();      
	
	switch (state) {
	  
	case DEFAULT:
	  switch (t.type()) {
	    
	  case Token.VAR:
	    this.name = t.text();
	    if (!e.hasMoreElements()) { this.type = VAR; }
	    else { // This should be handled by Operator parsing
	      while (e.hasMoreElements()) {
		t = (Token) e.nextElement();      
		this.name = this.name + " " + t.text();
	      }
	      this.type = OP;
	    } 
	    this.precedence = 0;
	    this.subterms = new Vector(0,0);
	    break;
	    
	  case Token.NUM:
	    this.name = t.text();
	    if (!e.hasMoreElements()) { this.type = NUM; }
	    else { // This should be handled by Operator parsing
	      while (e.hasMoreElements()) {
		t = (Token) e.nextElement();      
		this.name = this.name + " " + t.text();
	      }
	      this.type = OP;
	    }
	    this.precedence = 0;
	    this.subterms = new Vector(0,0);
	    break;
	    
	  case Token.PLING:
	    this.type = CUT;
	    this.name = t.text();
	    this.precedence = 0;
	    this.subterms = new Vector(0,0);
	    break;
	    
	    // This whole case should be handled by Operator parsing
	  case Token.MINUS: case Token.PLUS: 
	  case Token.TIMES: case Token.HASH: 
	    this.name = t.text();
	    this.type = OP;
	    this.subterms = new Vector(2,1);
	    if (e.hasMoreElements()) {
	      while (e.hasMoreElements()) {
		subtokens.addElement((Token) e.nextElement());
	      }
	      if (((Token) subtokens.firstElement()).type() == Token.OPEN &&
		  ((Token) subtokens.lastElement()).type() == Token.CLOSE) {
		subtokens.removeElementAt(0);
		subtokens.removeElementAt(subtokens.size()-1);
	      }
	      this.subterms.addElement(new Term(subtokens));
	    }
	    this.precedence = 15;
	    break;
	    
	  case Token.OPENSQ:
	    this.type = LIST;
	    this.precedence = 0;
	    this.subterms = new Vector(2,0);
	    state = LISTHEAD;
	    break;
	    
	  case Token.OPEN: 
	    this.type = FUNC;
	    this.precedence = 0;
	    this.subterms = new Vector(10,10);
	    state = FUNCBODY;
	    break;
	    
	  case Token.FNSYM:
	    this.type = FUNC;
	    this.precedence = 0;
	    this.name = t.text();
	    this.subterms = new Vector(0,0);	    
	    break;
	  }
	  
	  break;
	  	  
	  /* ************************* */
	  
	case LISTHEAD:
	  switch (t.type()) {
	    
	  case Token.OPENSQ:
	    subtokens.addElement(t);
	    state = SUBLIST;
	    last_state = LISTHEAD;
 	    bracket_depth = 1;
	    break;
	    
	  case Token.CLOSESQ:
	    this.name = "";
	    if (subtokens.size() != 0) {
	      this.subterms.addElement(new Term(subtokens));
	      subtokens.removeAllElements();
	      subtokens.addElement(new Token("[", Token.OPENSQ));
	      subtokens.addElement(t);
	      this.subterms.addElement(new Term(subtokens));
	      subtokens.removeAllElements();
	      state = DEFAULT;
	    }
	    break;
	    
	  case Token.COMMA:
	    this.subterms.addElement(new Term(subtokens));
	    this.name = ",";
	    subtokens.removeAllElements();
	    subtokens.addElement(new Token("[", Token.OPENSQ));
	    state = LISTTAIL;
	    break;
	    
	  case Token.VBAR:
	    this.subterms.addElement(new Term(subtokens));
	    this.name = "|";
	    subtokens.removeAllElements();
	    state = LISTREST;
	    break;
	    
	  default:
	    subtokens.addElement(t);
	    break;
	  }
	  break;
	  
	  /* ************************* */
	  
	case SUBLIST:
	  switch (t.type()) {
	    
	  case Token.OPENSQ:
	    bracket_depth++;
	    subtokens.addElement(t);
	    break;
	    
	  case Token.CLOSESQ:
	    bracket_depth--;
	    subtokens.addElement(t);
	    if (bracket_depth == 0) {
	      state = last_state;
	    }
	    break;
	    
	  default:
	    subtokens.addElement(t);
	    break;
	  }
	  break;
	  
	  /* ************************* */
	  
	case LISTTAIL:
	  switch (t.type()) {
	    
	  case Token.OPENSQ:
	    subtokens.addElement(t);
	    bracket_depth = 1;
	    state = SUBLIST;
	    last_state = LISTTAIL;
	    break;
	    
	  case Token.CLOSESQ:
	    subtokens.addElement(t);
	    this.subterms.addElement(new Term(subtokens));
	    subtokens.removeAllElements();
	    state = DEFAULT;
	    break;
	    
	  default:
	    subtokens.addElement(t);
	    break;
	  }
	  break;
	  
	  /* ************************* */
	  
	case LISTREST:
	  switch (t.type()) {
	    
	  case Token.OPENSQ:
	    subtokens.addElement(t);
	    bracket_depth = 1;
	    state = SUBLIST;
	    last_state = LISTREST;
	    break;
	    
	  case Token.CLOSESQ:
	    this.subterms.addElement(new Term(subtokens));
	    subtokens.removeAllElements();
	    state = DEFAULT;
	    break;
	    
	  default:
	    subtokens.addElement(t);
	    break;
	  }
	  break;
	  
	  /* ************************* */
	  
	case FUNCBODY:
	  switch (t.type()) {
	    
	  case Token.OPEN:
	    subtokens.addElement(t);
	    bracket_depth = 1;
	    state = SUBEXPR;
	    last_state = FUNCBODY;
	    break;
	    
	  case Token.CLOSE:
	    this.subterms.addElement(new Term(subtokens));
	    subtokens.removeAllElements();
	    state = DEFAULT;
	    break;
	    
	  case Token.OPENSQ:
	    subtokens.addElement(t);
	    bracket_depth = 1;
	    state = SUBLIST;
	    last_state = FUNCBODY;
	    break;
	    
	  case Token.COMMA:
	    this.subterms.addElement(new Term(subtokens));
	    subtokens.removeAllElements();
	    break;
	    
	  default:
	    subtokens.addElement(t);
	    break;
	  }
	  break;
	  
	  /* ************************* */
	  
	case SUBEXPR:
	  switch (t.type()) {
	    
	  case Token.OPEN:
	    bracket_depth++;
	    subtokens.addElement(t);
	    break;
	    
	  case Token.CLOSE:
	    bracket_depth--;
	    subtokens.addElement(t);
	    if (bracket_depth == 0) {
	      state = last_state;
	    }
	    break;
	    
	  default:
	    subtokens.addElement(t);
	    break;
	  }
	  break;
	}
      }
    }
  }
    
  /**
   * @return true, iff the Term is a variable.
   */
  public final boolean isVariable() { return (type == VAR); }

  /**
   * @return true, iff the Term is a list.
   */
  public final boolean isList() { return (type == LIST); }

  /**
   * @return the subterms of the Term.
   */
  public final Vector subterms() { return subterms; }

  /**
   * @return the name String of the Term.
   */
  public final String name() { return name; }

  /**
   * @return the precedence of the Term.
   */
  public final int precedence() { return precedence; }

  /**
   * @return the arity of the Term.
   */
  public final int functionArity() { return subterms.size(); }

  /**
   * @return the predicate symbol for this Term.
   */
  public final String symbol() {
    if (type == FUNC) { return (name + "/" + subterms.size()); }
    else { return name; }
  }

  /**
   * @return a String representation of the Term.
   */
  public final String toString() {
    String out;

    if (this.isList()) {
      out = "[";

      if (subterms.size() == 0) { out ="[]"; }
      else {	
	out = "[" + ((Term) subterms.elementAt(0)).toString();
	out = out + name;
	Term t = (Term) subterms.elementAt(1);

	if (t.isList()) {
	  out = out + t.toString().substring(1);
	}
	else {
	  out = out + t.toString() + "]";
	}
      }
    }
    else {
      out = name;
      Enumeration e = subterms.elements();
      if (e.hasMoreElements()) {
	out = out + "(" + ((Term) e.nextElement()).toString();
	while (e.hasMoreElements()) {
	  out = out + ", " + ((Term) e.nextElement()).toString();
	}
	out = out + ")";
      }
    }
    return out;
  }
}
