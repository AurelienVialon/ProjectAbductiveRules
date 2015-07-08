/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.Engine;

import PrologParse.Clause;
import PrologParse.ClauseList;
import PrologParse.PrologParser;
import PrologParse.Term;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

/**
 *
 * @author Aurélien Vialon
 */
public class ILPMemory 
{
  protected ModeList modes;
  protected ClauseList options;
  protected ClauseList types;
  protected ClauseList clauses;
  
  protected String fileName;
  
  public ILPMemory ()
  {
    modes = new ModeList();
    types = new ClauseList();
    options = new ClauseList();
    clauses = new ClauseList();
    
                    
        this.Load("/home/aurelien/Bureau/Projet/famille.pl");
        //this.Load("/home/aurelien/Bureau/Progol/examples4.2/animals.pl");
        //this.Load("/home/aurelien/Bureau/Progol/examples4.2/chess.pl");
  }

    public String getFileName() 
    {
        return fileName;
    }
    public synchronized ModeList getModes() 
    {
        return modes;
    }

    public synchronized void setModes(ModeList modes) 
    {
        this.modes = modes;
    }

    public synchronized ClauseList getOptions() 
    {
        return options;
    }

    public synchronized void setOptions(ClauseList options) 
    {
        this.options = options;
    }

    public synchronized ClauseList getTypes() 
    {
        return types;
    }

    public synchronized void setTypes(ClauseList types)
    {
        this.types = types;
    }

    public synchronized ClauseList getClauses() 
    {
        return clauses;
    }

    public synchronized void setClauses(ClauseList clauses) 
    {
        this.clauses = clauses;
    }
     /**
   * Save the session in the given filename.
   * The file is written in the following order:
   * Options, 
   * Modes,
   * Types,
   * Other Clauses (examples and background knowledge.).
   * @param filename The file into which the session is written.
     * @return 
   */
  public final boolean Save (String filename) 
  {
    try {
      Enumeration enume;
      FileWriter ws = 
	new FileWriter(filename);
      enume = modes.elements();
      while (enume.hasMoreElements()) 
      {
	ws.write(((Mode) enume.nextElement()).toString() + "\n");
      }
      enume = types.elements();
      while (enume.hasMoreElements()) 
      {
	ws.write(((Clause) enume.nextElement()).toString() + "\n");
      }
      enume = clauses.elements();
      while (enume.hasMoreElements()) 
      {
	ws.write(((Clause) enume.nextElement()).toString() + "\n");
      }
      enume = options.elements();
      while (enume.hasMoreElements()) 
      {
	ws.write(((Clause) enume.nextElement()).toString() + "\n");
      }
      ws.close();
    }
    catch(IOException e) { System.out.println("Whoops: " + e.toString()); }

    return true;
  }

  /**
   * Load a session from the given filename.
   * The file is first scanned for QUERY type clauses - i.e.
   * modes and settings.  Then the file is scanned for types
   * and general clauses, the distinction being made on the basis
   * of the mode declarations (and other more obvious factors).
   * @param filename The file from which the session is loaded.
   */
  public final boolean Load (String filename) 
  {
    this.Init();
    ClauseList file;
    Clause c;
    Mode m;
    Term t;
    Enumeration e1,e2,e3;
    String s;
    try {
      BufferedReader rs = new BufferedReader(new FileReader(filename));
      file = new PrologParser(rs);

      e1 = file.elements();
      while (e1.hasMoreElements()) {
	c = (Clause) e1.nextElement();

	if (c.punctuation() == Clause.QUERY) {
	  e2 = c.body().elements();
	  while (e2.hasMoreElements()) {
	    t = (Term) e2.nextElement();
	    s = " :- " + t.toString() + "?";
	    if (t.symbol().equals("modeh/2") || t.symbol().equals("modeb/2")) {
	      m = new Mode(new Clause(s));
	      clauses.addDefinition(m.predicateSymbol());
	      e3 = m.arguments().elements();
	      while (e3.hasMoreElements()) {
		String tp = ((ModeArg) e3.nextElement()).type();
		if (!tp.equals("any") &&
		    !tp.equals("int") && 
		    !tp.equals("float")) {
		  types.addDefinition(tp + "/1");
		}
	      }
	      modes.addMode(m);
	    }
	    else {
	      options.addElement(new Clause(s));
	    }
	  }			       
	}
      }

      e1 = file.elements();
      while (e1.hasMoreElements()) 
      {
	c = (Clause) e1.nextElement();

	if (c.punctuation() == Clause.ASSERT) 
        {
	  if ((c.head().functionArity() == 1) && 
	      !modes.hasModeFor(c.head().symbol()) &&
	      c.body().size() ==0) {
	    types.addElement(c);
	  }
	  else 
          {
	    clauses.addElement(c);
	  }
	}
      }
      rs.close();
      this.fileName = filename;
    }
    catch(IOException e) 
    { 
        System.out.println("Error : " + e.toString()); 
    }
    return true;
  } 

   public void Init()
   {
      modes.removeAll();
      types.removeAll();
      clauses.removeAll();
      options.removeAll();
   }
   public synchronized void Reinit()
   {
       
   }
}
