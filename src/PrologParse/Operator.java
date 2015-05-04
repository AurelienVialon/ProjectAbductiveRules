package PrologParse;

class Operator {
  private static final int XFX = 0;
  private static final int XFY = 1;
  private static final int YFX = 2;
  private static final int FX  = 3;
  private static final int FY  = 4;
  private static final int XF  = 5;
  private static final int YF  = 6;
  private static final int ERR = 7;

  private String symbol;
  private int type;
  private int precedence;

  public Operator(int precedence, String type_string, String symbol) {
    
    this.precedence = precedence;
    this.symbol = symbol;
    
    if      (type_string.equals("xfx")) { this.type = XFX; }
    else if (type_string.equals("xfy")) { this.type = XFY; }
    else if (type_string.equals("yfx")) { this.type = YFX; }
    else if (type_string.equals("fx"))  { this.type = FX;  }
    else if (type_string.equals("fy"))  { this.type = FY;  }
    else if (type_string.equals("xf"))  { this.type = XF;  }
    else if (type_string.equals("yf"))  { this.type = YF;  }
    else { this.type = ERR; }

  }
}
