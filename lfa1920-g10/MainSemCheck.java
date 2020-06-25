import java.util.*;

public class MainSemCheck extends MainGrammarBaseVisitor<Boolean> {

   private final RealType realType = new RealType();
   private final IntType intType = new IntType();
   private final UnitType unitType = new UnitType();
   private final BooleanType booleanType = new BooleanType();
   private final HashMap<String, HashMap<String, SymbolConv>> dimMap;

   public MainSemCheck(HashMap<String, HashMap<String, SymbolConv>> map){
      dimMap = map;
   }

   @Override public Boolean visitIf_clause(MainGrammarParser.If_clauseContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Boolean visitForLoop(MainGrammarParser.ForLoopContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Boolean visitWhileLoop(MainGrammarParser.WhileLoopContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Boolean visitDoWhileLoop(MainGrammarParser.DoWhileLoopContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Boolean visitDeclarations(MainGrammarParser.DeclarationsContext ctx) {
      Boolean res = visit(ctx.expression());
      String id = ctx.ID().getText();
      VariableSymbol vs = new VariableSymbol(id, ctx.expression().e_type);
     
      if(res){
         if(MainGrammarParser.symbolTable.containsKey(id)){
            ErrorHandling.printError(ctx,"ERROR: Variable \""+id+"\" already exists!");
            res = false;
         }else{
            MainGrammarParser.symbolTable.put(id, vs);
         }
      }
      return res;
   }

   @Override public Boolean visitAssignments(MainGrammarParser.AssignmentsContext ctx) {
      Boolean res = visit(ctx.expression());
      String id = ctx.ID().getText();
      if(res){
         if(!MainGrammarParser.symbolTable.containsKey(id)){
            ErrorHandling.printError(ctx,"ERROR: Variable \""+id+"\" does not exists!");
            res = false;
         }
         else{
            Symbol s = MainGrammarParser.symbolTable.get(id);
            if(!ctx.expression().e_type.conformsTo(s.type())){
               ErrorHandling.printError(ctx,"ERROR: Expression type does not conform to variable \""+id+"\" type!");
               res = false;
            }
            
         }
      }
      return res;
   }

   @Override public Boolean visitPrints(MainGrammarParser.PrintsContext ctx) {
      return visit(ctx.expression());
   }

   @Override public Boolean visitIntExpr(MainGrammarParser.IntExprContext ctx) {
      ctx.e_type = intType;
      return true;
   }

   @Override public Boolean visitAddSubExpr(MainGrammarParser.AddSubExprContext ctx) {
      Boolean res = visit(ctx.e1) && visit(ctx.e2);
      if(res){
         if(!(ctx.e1.e_type.name().equals(ctx.e2.e_type.name()))){
            ErrorHandling.printError(ctx,"ERROR: Operands of different types cannot be added/subtracted");
            return false;
         }
         ctx.e_type = fetchType(ctx.e1.e_type,ctx.e2.e_type);
      }
      return res;
   }

   @Override public Boolean visitRealExpr(MainGrammarParser.RealExprContext ctx) {
      ctx.e_type = realType;
      return true;
   }

   @Override public Boolean visitMultDivExpr(MainGrammarParser.MultDivExprContext ctx) {
      Boolean res = visit(ctx.e1) && visit(ctx.e2);
      if (res)
         ctx.e_type = ctx.e1.e_type;
      return res;

   }

   @Override public Boolean visitUnitVarExpr(MainGrammarParser.UnitVarExprContext ctx) {
      Boolean res = false;
      String unit = ctx.unit.getText();

      for(HashMap<String, SymbolConv> units : dimMap.values()){
         for(SymbolConv value : units.values()){
            if(unit.equals(value.unitName())){
               ctx.e_type = unitType;
               return true;
            }
         }
      }

      if(res != true){
         ErrorHandling.printError(ctx,"ERROR: Unit \""+unit+"\" is not configured!");
         ctx.e_type = unitType;
         return res;
      }
      
      ctx.e_type = unitType;
      return true;
   }

   @Override public Boolean visitParenExpr(MainGrammarParser.ParenExprContext ctx) {
      Boolean res = visit(ctx.e);
      if (res)
         ctx.e_type = ctx.e.e_type;
      return res;
   }

   @Override public Boolean visitIncrementsExpr(MainGrammarParser.IncrementsExprContext ctx) {
      Boolean res = true;
      String id  =  ctx.ID().getText();
      if(!MainGrammarParser.symbolTable.containsKey(id)){
         ErrorHandling.printError(ctx, "ERROR: Variable \""+id+"\" does not exists!");
         res = false;
      }
      
      return res;
   }

   @Override public Boolean visitConditionalExpr(MainGrammarParser.ConditionalExprContext ctx) {
      Boolean res = visit(ctx.e1) && visit(ctx.e2);
      if(res){
         if(!(ctx.e1.e_type.name().equals(ctx.e2.e_type.name()))){
            ErrorHandling.printError(ctx, "ERROR: Comparison types must be of the same type");
            res = false;
         }
         //else if((ctx.e1.e_type.equals(ctx.e2.e_type))
         
      }
      return res;
   }

   @Override public Boolean visitIdExpr(MainGrammarParser.IdExprContext ctx) {
      Boolean res = true;
      String id = ctx.ID().getText();
      if (!MainGrammarParser.symbolTable.containsKey(id))
      {
         ErrorHandling.printError(ctx, "ERROR: Variable \""+id+"\" does not exists!");
         res = false;
      }
      else
      {
         Symbol sym = MainGrammarParser.symbolTable.get(id);
         /*if (!sym.valueDefined())
         {
            ErrorHandling.printError(ctx, "Variable \""+id+"\" not defined!");
            res = false;
         }
         else*/
         ctx.e_type = sym.type();
         res = true;   
      }
      return res;
   }



   private Type fetchType(Type t1, Type t2){
      Type res = null;
      if (t1.isNumeric() && t2.isNumeric())
      {
         if ("real".equals(t1.name()))
            res = t1;
         else if ("real".equals(t2.name()))
            res = t2;
         else
            res = t1;
      }
      else if ("unit".equals(t1.name()) && "unit".equals(t2.name())) // ??
         res = t1;
      return res;
   }

   @Override public Boolean visitIntType(MainGrammarParser.IntTypeContext ctx) {
      ctx.t_type = intType;
      return true;
   }

   @Override public Boolean visitRealType(MainGrammarParser.RealTypeContext ctx) {
      ctx.t_type = realType;
      return true;
   }

   @Override public Boolean visitUnitType(MainGrammarParser.UnitTypeContext ctx) {
      ctx.t_type = unitType;
      return true;
   }

   @Override public Boolean visitBooleanType(MainGrammarParser.BooleanTypeContext ctx){
      ctx.t_type = booleanType;
      return true;
   }
}
