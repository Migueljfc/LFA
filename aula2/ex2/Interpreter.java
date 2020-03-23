import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class Interpreter extends SuffixCalculatorBaseVisitor<Double> {

   @Override public Double visitProgram(SuffixCalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Double visitStat(SuffixCalculatorParser.StatContext ctx) {
      Double res = (Double) visit(ctx.expr());
      if(res != null)
         System.out.println("Result = " + res);
      return res;
   }

   @Override public Double visitExprNumber(SuffixCalculatorParser.ExprNumberContext ctx) {
      return Double.parseDouble(ctx.Number().getText());
   }

   @Override public Double visitExprSuffix(SuffixCalculatorParser.ExprSuffixContext ctx) {
      double v1 = visit(ctx.expr(0));
      double v2 = visit(ctx.expr(1));
      double result = 0;
       switch(ctx.op.getText()){
         case "+": result = v1 + v2 ;
                   break;
         case "-": result = v1 - v2;
                   break;
         case "*": result = v1 * v2;
                   break;
         case "/": result = v1 / v2;
                   break;
       }
       return result;
   }
}
