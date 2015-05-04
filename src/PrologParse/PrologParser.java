package PrologParse;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;
import java.util.Enumeration;

/**
 * A ClauseList produced by parsing a piece of prolog.
 * The Parsing is conducted top-down.  The prolog is first tokenised
 * by a PrologTokeniser, and the token sequence is then split into
 * Clauses. The Clause class handles each subsequence of tokens by
 * splitting them further into term subsequences, which are then
 * passed to the Term class.
 * @author Rupert Parson
 * @version 1.1
 * @see PrologParse.ClauseList
 * @see PrologParse.Clause
 * @see PrologParse.Term
 */
public class PrologParser extends ClauseList {  

  /**
   * Construct the PrologParser from a reader of some prolog.
   * @param in A BufferedReader of some prolog.
   */
  public PrologParser(BufferedReader in) throws IOException {
    Vector tokens = new Vector(100,100);
    PrologTokenizer pt = new PrologTokenizer(in);   

    Token t = pt.nextToken();
    while (t.type() != Token.EOF) {
      switch (t.type()) {
      case Token.FSTOP:
	tokens.addElement(t);
	this.addElement(new Clause(tokens));
	tokens.removeAllElements();
	break;
      case Token.QMARK:
	tokens.addElement(t);
	this.addElement(new Clause(tokens));
	tokens.removeAllElements();
	break;
      default:
	tokens.addElement(t);
      }
      t = pt.nextToken();    
    }
  }

  public static void main(String[] args) { 
    if (args.length ==1) {
      try {
	ClauseList clauses = 
	  new PrologParser(new BufferedReader(new FileReader(args[0])));  
	Enumeration e = clauses.elements();
	System.out.println("Start:");
	while (e.hasMoreElements()) {
	  System.out.println(((Clause) e.nextElement()).toString());
	}
	System.out.println("Finish.");
      }
      catch (IOException error) {
	System.out.println("OOoops"+ error.toString());
      }
    }
  }
}
