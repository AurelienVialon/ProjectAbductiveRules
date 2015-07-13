/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.Communications;

/**
 *
 * @author Aurélien Vialon
 */
public class Lexique 
{
    public static final String ALL = "all";
    
    public static final String MODES = "modes";
    public static final String CLAUSES = "clauses";
    public static final String TYPES = "types";
    
    public static final String MODIFY = "modify";
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String GET = "get";
    public static final String ANSWER = "answer";   
    
    public static final String INIT = "init";
    //Sentences example :
    //"modify:modes:example:new" //ILPEngine will remplace the example mode by the new one (attached of the message).
    //"add:clauses:example"
    //"get:types" //ILPEngine will return all the types in memory.
    //"init" //ILPEngine will do an initialisation.
    
    //"remove:all" //ILPEngine will empty all its memory (all modes, all clauses and all types).
    //"romove:types:all" //ILPEngine will just empty all the types in memory.
}
