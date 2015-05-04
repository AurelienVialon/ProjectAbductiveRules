package PrologParse;

/**
 * A class that represents tokens produced by the PrologTokenizer.
 *
 * @author Rupert Parson
 * @version 1.1
 * @see PrologParse.PrologTokenizer
 */
public class Token {
  private String text;
  private int type;
  
  public static final int DEFAULT = 0;
  public static final int FNSYM   = 1;
  public static final int VAR     = 2;
  public static final int COMMA   = 3;
  public static final int OPEN    = 4;
  public static final int CLOSE   = 5;
  public static final int FSTOP   = 6;
  public static final int IF      = 7;
  public static final int EOF     = 8;
  public static final int NUM     = 9;
  public static final int OP      = 10;
  public static final int QMARK   = 11;
  public static final int PLING   = 12;
  public static final int LIST    = 13;
  public static final int PLUS    = 14;
  public static final int MINUS   = 15;
  public static final int TIMES   = 16;
  public static final int HASH    = 17;
  public static final int OPENSQ  = 18;
  public static final int CLOSESQ = 19;
  public static final int VBAR    = 20;

  static final String tokentype[] = {"DEFAULT","FNSYM","VAR","COMMA","OPEN",
				     "CLOSE","FSTOP","IF","EOF","NUM","OP",
				     "QMARK","PLING","LIST","PLUS","MINUS",
				     "TIMES","HASH","OPENSQ","CLOSESQ",
				     "VBAR"};
  
  /**
   * Constructs a Token of the specified type from the specified text.
   * @param text The text from which the token is constructed.
   * @param type The type of this Token.
   */
  public Token(String text, int type) {
    this.text = text;
    this.type = type;
  }

  /**
   * @return the type of this Token.
   */
  public final int type() { return type; }

  /**
   * @return the text of this Token.
   */
  public final String text() { return text; }

  /**
   * @return a string representation of this token.
   */
  public final String toString() {
    return text + ":" + tokentype[type];
  }
}
