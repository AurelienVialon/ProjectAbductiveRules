/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ASP.Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Aurélien Vialon
 */
public class ASPEngine 
{
    private Process p;
    private String cheminASP;
    
     private int run()
    {
        Runtime now = Runtime.getRuntime();
         
        try 
        {
          String line;

          p = now.exec(this.cheminASP + " tmppgli");

          InputStream s = p.getInputStream();
          BufferedReader in
            = new BufferedReader(new InputStreamReader(s));
          BufferedReader sauve
            = new BufferedReader(new InputStreamReader(s));

          //this.pm.ParseResults(in, output);
          //this.pm.UpdateResults();

          return 1;
        }
        catch (IOException e) 
        {
          System.out.println("Oooups: " + e);
          return 1;
        }
    }
}
