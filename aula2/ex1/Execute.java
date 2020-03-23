import java.util.Iterator;
import org.antlr.v4.runtime.tree.TerminalNode;

public class Execute extends HelloBaseVisitor<String> {

   @Override public String visitMain(HelloParser.MainContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitTop(HelloParser.TopContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitGreetings(HelloParser.GreetingsContext ctx) {
      System.out.println("Ola " + visit(ctx.name()) );
      return null;
   }

   @Override public String visitBye(HelloParser.ByeContext ctx) {
      System.out.println("Adeus " + visit(ctx.name()) );
      return null;
   }

   @Override public String visitName(HelloParser.NameContext ctx) {
      String s = " ";
      Iterator<TerminalNode> it = ctx.ID().iterator();
      while(it.hasNext()){
         s += it.next().getText() + " ";
      }

      return s.trim();
   }
}
