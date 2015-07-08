/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import ASP.ASPManager;
import MVC.Modele;
import ILP.Engine.Mode;
import ILP.Engine.ModeArg;
import PrologParse.Clause;
import PrologParse.PrologParser;
import PrologParse.Term;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author Aurelien Vialon
 */
public class ILPtoASP
{    
    private Modele asp;
       
    public ILPtoASP (ASPManager a)
    {
        this.asp = a.donneModele();
    }
    
    public void givetoASP( String fn )
    {
         try 
         {
            BufferedReader rs = new BufferedReader(new FileReader(fn));
            String temp;
            
            while ( true )
            {
                temp = rs.readLine();
                 
                 
                if ( temp == null )
                    break;
                                
            }
         }
         catch(IOException e) { System.out.println("Whoops: " + e.toString()); }
    }
}