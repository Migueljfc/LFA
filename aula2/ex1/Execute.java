import java.util.Iterator;

public class Execute extends HelloBaseVisitor<String> {

   @Override public String visitMain(HelloParser.MainContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitTop(HelloParser.TopContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitGreetings(HelloParser.GreetingsContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitBye(HelloParser.ByeContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitName(HelloParser.NameContext ctx) {
      String s = " ";
      Iterator<TerinalNode> it = ctx.get.ID().iterator();
      while(it.hasNext()){
         s += it.next)=.getText() + " ";
      }

      return visitChildren(ctx);
   }
}
