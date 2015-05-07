package ProgolInterface;

import PrologParse.*;

/**
 * A class to represent an argument in a mode declaration.
 * i.e. the type paired with an input/output operator.
 * @author Rupert Parson
 * @version 2.0
 * @see Mode
 */
public class ModeArg {
  /**
   * The input/output constants
   */
  static int INPUT=0, OUTPUT=1, CONSTANT=2;
  private static String[] ioString = {"+", "-", "#"};
  private String type;
  private int ioDirection;

  /**
   * Constructs a ModeArg with the given type and I/O.
   * @param type The type of the argument (as a string).
   * @param dir The I/O direction of the argument.
   */
  public ModeArg(String type, int dir) {
    this.type = type;
    this.ioDirection = dir;
  }

  /**
   * Constructs a ModeArg from the given term.
   * @param term A Term representing the argument.
   */
  public ModeArg(Term term) {
    if (term.name().equals("+")) { this.ioDirection = INPUT; }
    else if (term.name().equals("-")) { this.ioDirection = OUTPUT; }
    else if (term.name().equals("#")) { this.ioDirection = CONSTANT; }
    this.type = ((Term) term.subterms().firstElement()).name();
  }

  /**
   * @return the type of the Mode argument.
   */
  public final String type() {
    return type;
  }

  /**
   * @return A string representation of the Mode argument.
   */
  public final String toString() {
    return (ioString[ioDirection] + type);
  }
}
