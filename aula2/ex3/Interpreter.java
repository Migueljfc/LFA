public class Interpreter extends CalculatorBaseVisitor<Integer> {

   @Override public Integer visitProgram(CalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitStat(CalculatorParser.StatContext ctx) {
      int res = 0;
      if(ctx.expr() != null){
         res = visit(ctx.expr());
         System.out.println("Result: " + res);
      }
      return res;
   }

   @Override public Integer visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      int op1 = visit(ctx.expr(0));
      int op2 = visit(ctx.expr(1));
      int res = 0;
      switch(ctx.op.getText()){
         case "+":
            res = op1 + op2;
         break;
         case "-":
            res= op1 - op2;
         break;
      }
      return res;
   }

   @Override public Integer visitExprParent(CalculatorParser.ExprParentContext ctx) {
      return visit(ctx.expr());
   }

   @Override public Integer visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      return Integer.parseInt(ctx.Integer().getText());
   }

   @Override public Integer visitExprMultDivMod(CalculatorParser.ExprMultDivModContext ctx) {
      int op1 = visit(ctx.expr(0));
      int op2 = visit(ctx.expr(1));
      int res = 0;
      switch(ctx.op.getText()){
         case "*":
            res= op1 * op2;
         break;
         case "/":
            res = op1 / op2;
         break;
         case "%":
            res = op1 % op2;
         break;
      }
      return res;

   }
}
