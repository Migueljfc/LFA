import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.stringtemplate.v4.*;
import java.io.*;
import java.util.*;


public class MainGrammarMain {

   private static UnitGrammarMain dimensions;
   public static void main(String[] args) throws Exception {
      if(args.length != 3){
         System.err.println("ERROR: 3 name files necessary! <OutputName> <DimensionsCONFIGFile> <ProgramFile>");
      }

      dimensions = new UnitGrammarMain(args[1]);

      // create a CharStream that reads from standard input:
      CharStream input = CharStreams.fromStream(new FileInputStream(args[2]));
      // create a lexer that feeds off of input CharStream:
      MainGrammarLexer lexer = new MainGrammarLexer(input);
      // create a buffer of tokens pulled from the lexer:
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      // create a parser that feeds off the tokens buffer:
      MainGrammarParser parser = new MainGrammarParser(tokens);
      // replace error listener:
      parser.removeErrorListeners(); // remove ConsoleErrorListener
      parser.addErrorListener(new ErrorHandlingListener());
      // begin parsing at program rule:
      ParseTree tree = parser.program();
      if(!dimensions.erreur()){
         System.out.println("DimensionsCONFIG File is correct!");
         if (parser.getNumberOfSyntaxErrors() == 0) {
            // print LISP-style tree:
            // System.out.println(tree.toStringTree(parser));
            MainSemCheck visitor0 = new MainSemCheck(dimensions.dimensionsMap()); //dimensions.dimensionsMap()
            MainCompiler visitor1 = new MainCompiler(dimensions.dimensionsMap());
            visitor0.visit(tree);
            if(!ErrorHandling.error()){
               ST code = visitor1.visit(tree);
               code.add("name",args[0]);
               PrintWriter pw = new PrintWriter(new File(args[0]+".java"));
               pw.print(code.render());
               pw.close();
            }
         }
      }
   }

   public static HashMap<String, HashMap<String, SymbolConv>> dimMap(){
      return dimensions.dimensionsMap();
   }
}
