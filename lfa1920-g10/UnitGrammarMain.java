import java.util.*;
import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class UnitGrammarMain {

   private static HashMap<String, HashMap<String, SymbolConv>> dimensionsMap = new HashMap<>();
   private static Boolean erreur = false;
   public UnitGrammarMain(String ficheiro) throws Exception {
      // create a CharStream that reads from standard input:
      CharStream input = CharStreams.fromStream(new FileInputStream(ficheiro));
      // create a lexer that feeds off of input CharStream:
      UnitGrammarLexer lexer = new UnitGrammarLexer(input);
      // create a buffer of tokens pulled from the lexer:
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      // create a parser that feeds off the tokens buffer:
      UnitGrammarParser parser = new UnitGrammarParser(tokens);
      // replace error listener:
      parser.removeErrorListeners(); // remove ConsoleErrorListener
      parser.addErrorListener(new ErrorHandlingListener());
      // begin parsing at program rule:
      ParseTree tree = parser.program();
      if (parser.getNumberOfSyntaxErrors() == 0) {
         // print LISP-style tree:
         // System.out.println(tree.toStringTree(parser));
         UnitSemCheck visitor0 = new UnitSemCheck();
         UnitInterpreter visitor1 = new UnitInterpreter();
         visitor0.visit(tree);
         erreur = visitor0.erreur();
         if(!erreur){
            visitor1.visit(tree);
            dimensionsMap = visitor1.dimensionsMap();
         }
      }
   }

   public static HashMap<String, HashMap<String, SymbolConv>> dimensionsMap(){
      return dimensionsMap;
   }

   public Boolean erreur(){
      return erreur;
   }
}