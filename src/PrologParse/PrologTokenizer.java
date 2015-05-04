package PrologParse;

import java.io.FileReader;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;
import java.util.Enumeration;

/**
 * A Class that represents prolog code as a sequence of tokens.
 *
 * @author Rupert Parson
 * @version 1.1
 * @see PrologParse.Token
 */
public class PrologTokenizer {
  BufferedReader in;
  private final static int START = 0;
  private final static int DLMFN = 1;
  private final static int FNSYM = 2;
  private final static int VAR   = 3;
  private final static int IF    = 4;
  private final static int NUM   = 5;
  private final static int COMP  = 6;
  private boolean finished = false;

  /**
   * Construct from a input buffer of prolog text.
   * @param in A BufferedReader of some prolog.
   */
  public PrologTokenizer(BufferedReader in) {
    this.in = in;
  }

  /**
   * Construct from a String of prolog text.
   * @param s A String of some prolog.
   */
  public PrologTokenizer(String s) {
    this.in = new BufferedReader(new StringReader(s));
  }

  /**
   * @return the next token in the prolog.
   */
  public final Token nextToken() throws IOException {
    String text = "";
    int state = START;
      
    int c = in.read();

    while (c!=-1) {
      switch (state) {
      case START:
	switch ((char) c) {
	case '\0':
	  finished = true;
	  return new Token(text, Token.EOF);
	case ' ': case '\t': case '\n': case '\r':
	  break;
	case '%':
	  while ((char) c != '\n' && (char) c != -1) { c = in.read(); }
	  break;
	case '(':
	  return new Token("(", Token.OPEN);
	case ')':
	  return new Token(")", Token.CLOSE);
	case '[':
	  return new Token("[", Token.OPENSQ);
	case ']':
	  return new Token("]", Token.CLOSESQ);
	case '|':
	  return new Token("|", Token.VBAR);
	case ',':
	  return new Token(",", Token.COMMA);
	case '.':
	  return new Token(".", Token.FSTOP);
	case '?':
	  return new Token("?", Token.QMARK);
	case '!':
	  return new Token("!", Token.PLING);
	case '-': 
	  return new Token("-", Token.MINUS);
	case '+':
	  return new Token("+", Token.PLUS);
	case '*':
	  return new Token("*", Token.TIMES);
	case '#':
	  return new Token("#", Token.HASH);
	case '<': case '>': case '=': case '\\': case '@':
	  state = COMP;
	  text = text + (char) c;
	  break;
	case ':':
	  c = in.read();
	  if ((char) c=='-') { return new Token(":-", Token.IF); }
	  in.reset();
	case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
	case 'G': case 'H': case 'I': case 'J': case 'K': case 'L': 
	case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R': 
	case 'S': case 'T': case 'U': case 'V': case 'W': case 'X': 
	case 'Y': case 'Z': case '_':
	  state = VAR;
	  text = text + (char) c;
	  break;
	case '1': case '2': case '3': case '4': case '5': case '6': 
	case '7': case '8': case '9': case '0':
	  state = NUM;
	  text = text + (char) c;
	  break;
        case '\'':
	  state = DLMFN;
	  text = "'";
	  break;
	default:
	  state = FNSYM;
      	  text = text + (char) c;
	}
	break;
      case COMP:
	while((char) c == '<' || (char) c == '>' || 
	      (char) c == '=' || (char) c == '.') {
	  text = text + (char) c;
	  in.mark(0);
	  c = in.read();
	}
	in.reset();
	return new Token(text,Token.OP);
      case NUM:
	while (Character.isDigit((char) c) || (char) c == '.') {
	  text = text + (char) c;
	  in.mark(0);
	  c = in.read();
	}
	in.reset();
	return new Token(text,Token.NUM);
      case VAR:
	while (Character.isLetterOrDigit((char) c) || (char) c == '_') {
	  text = text + (char) c;
	  in.mark(0);
	  c = in.read();
	}
	in.reset();
	return new Token(text,Token.VAR);
      case DLMFN:
	while ((char) c != '\'') {
	  text = text + (char) c;
	  c = in.read();
	}
	text = text + '\'';
	return new Token(text,Token.FNSYM);
      case FNSYM:
        while (Character.isLetterOrDigit((char) c) || (char) c == '_'
               || (char) c == '/') {
	  text = text + (char) c;
	  in.mark(0);
	  c = in.read();
	}
	in.reset();
	return new Token(text,Token.FNSYM);
      }
      in.mark(0);
      c =  in.read();
    }
    return new Token(text,Token.EOF);
  }	    

  public static void main(String[] args) {
    PrologTokenizer pt;
    Token t;
    if (args.length ==1) {
      try {
	pt = new PrologTokenizer(new BufferedReader(new FileReader(args[0])));
	t = pt.nextToken();
	
	System.out.println("Start:");
	while (t.type() != Token.EOF) {
	  System.out.println(t.toString());
	  t = pt.nextToken();
	}
	System.out.println("Finish.");
      }
      catch (IOException e) {System.out.println("OOoops"+ e.toString());};
    }
  }
}
