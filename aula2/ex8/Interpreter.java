public class Interpreter extends CalculatorBaseVisitor<Integer> {

   @Override public Integer visitProgram(CalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitExpression(CalculatorParser.ExpressionContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitVariable(CalculatorParser.VariableContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitPrintFrac(CalculatorParser.PrintFracContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitReduceExpr(CalculatorParser.ReduceExprContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitAssignement(CalculatorParser.AssignementContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitPrint(CalculatorParser.PrintContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitReduce(CalculatorParser.ReduceContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitExprFrac(CalculatorParser.ExprFracContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitExprIntegerSinal(CalculatorParser.ExprIntegerSinalContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitExprRead(CalculatorParser.ExprReadContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitExprIntege(CalculatorParser.ExprIntegeContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitExprParent(CalculatorParser.ExprParentContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitExprMultDiv(CalculatorParser.ExprMultDivContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitExprID(CalculatorParser.ExprIDContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitExprElev(CalculatorParser.ExprElevContext ctx) {
      return visitChildren(ctx);
   }
}
