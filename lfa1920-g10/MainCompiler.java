import java.util.*;
import java.io.*;
import java.util.Iterator;
import org.stringtemplate.v4.*;
import org.antlr.v4.runtime.ParserRuleContext;

public class MainCompiler extends MainGrammarBaseVisitor<ST> {

   //private HashMap<String, HashMap<String, SymbolConv>> dimMap;

   public MainCompiler(HashMap<String, HashMap<String, SymbolConv>> dimMap) throws IOException {
      
      Units.setHashMap(dimMap);
      
   }

   @Override public ST visitProgram(MainGrammarParser.ProgramContext ctx) {
      ST res = templates.getInstanceOf("module");
      Iterator<MainGrammarParser.StatContext> list = ctx.stat().iterator();
      while(list.hasNext()){
         res.add("stat",visit(list.next()).render());
      }
      return res;
   }

   @Override public ST visitStat(MainGrammarParser.StatContext ctx) {
      ST res = templates.getInstanceOf("stats");
      if(ctx.if_clause() != null) res.add("stat", visit(ctx.if_clause()));
      else if(ctx.loop() != null) res.add("stat", visit(ctx.loop()));
      else if(ctx.instruction() != null) res.add("stat", visit(ctx.instruction()));
      return res;
   }

   @Override public ST visitBlock(MainGrammarParser.BlockContext ctx) {
      ST res = templates.getInstanceOf("block");
      Iterator<MainGrammarParser.StatContext> list = ctx.stat().iterator();
      while(list.hasNext()){
         res.add("stat",visit(list.next()).render());
      }
      return res;
   }

   @Override public ST visitIf_clause(MainGrammarParser.If_clauseContext ctx) {
      ST res = templates.getInstanceOf("stats");
      ST ifCond = templates.getInstanceOf("if_clause");
      ifCond.add("stat", visit(ctx.expression()).render());
      ifCond.add("conditional", ctx.expression().var);
      ifCond.add("statIf", visit(ctx.blockIf).render());
      res.add("stat", ifCond);
      if(ctx.blockElse != null){
         ST elseCond = templates.getInstanceOf("else_clause");
         elseCond.add("statElse", visit(ctx.blockElse).render());
         res.add("stat", elseCond);
      }
      return res;

   }

   @Override public ST visitForLoop(MainGrammarParser.ForLoopContext ctx) {
      
      ST res = templates.getInstanceOf("for");
      
      res.add("declaration", visit(ctx.declarations()).render());
      res.add("stat", visit(ctx.expression(0)).render());
      System.out.print("asd");

      String conditionalLoop = ctx.expression(0).var;

      res.add("conditional", conditionalLoop);
      res.add("inc_dec", visit(ctx.expression(1)).render());
      res.add("statFor", visit(ctx.block()).render());

      ST assign = templates.getInstanceOf("assignment");
      assign.add("stat", visit(ctx.expression(0)).render());
      assign.add("inst", conditionalLoop);
      assign.add("value", ctx.expression(0).var);

      res.add("statFor", assign.render());

      return res;
   }

   @Override public ST visitWhileLoop(MainGrammarParser.WhileLoopContext ctx) {
      ST res = templates.getInstanceOf("while");
      res.add("stat", visit(ctx.expression()).render());
      String conditionalLoop = ctx.expression().var;
      res.add("conditional", conditionalLoop);
      res.add("statWhile", visit(ctx.block()).render());
      ST assign = templates.getInstanceOf("assignment");
      assign.add("stat", visit(ctx.expression()).render());
      assign.add("inst", conditionalLoop);
      assign.add("value", ctx.expression().var);
      res.add("statWhile", assign.render());
      return res;

   }

   @Override public ST visitDoWhileLoop(MainGrammarParser.DoWhileLoopContext ctx) {
      ST res = templates.getInstanceOf("do_while");
      res.add("stat", visit(ctx.expression()).render());
      String conditionalLoop = ctx.expression().var;
      res.add("conditional", conditionalLoop);
      res.add("statDoWhile", visit(ctx.block()).render());
      ST assign = templates.getInstanceOf("assignment");
      assign.add("stat", visit(ctx.expression()).render());
      assign.add("inst", conditionalLoop);
      assign.add("value", ctx.expression().var);
      res.add("statDoWhile", assign.render());
      return res;
   }


   @Override public ST visitDeclarations(MainGrammarParser.DeclarationsContext ctx) {
      ST res = templates.getInstanceOf("stats");
      ST decl;
      res.add("stat", visit(ctx.expression()));
      String id = ctx.ID().getText();
      Symbol s = MainGrammarParser.symbolTable.get(id);
      s.setVarName(newVar());
      if("unit".equals(s.type.name())) {//verificar se são tipo unidade
         ST declaration = templates.getInstanceOf("unitDecl");
         declaration.add("inst", s.varName());
         declaration.add("unit", ctx.expression().var);
         res.add("stat", declaration.render());
      }else {
         ST declaration = templates.getInstanceOf("decl");
         declaration.add("type", s.type().name());
         declaration.add("inst", s.varName());
         declaration.add("value", ctx.expression().var);
         res.add("stat", declaration.render());
      }
      return res;

   }

   @Override public ST visitAssignments(MainGrammarParser.AssignmentsContext ctx) {
      ST res = templates.getInstanceOf("assignment");
      String id = ctx.ID().getText();
      Symbol s = MainGrammarParser.symbolTable.get(id);
      res.add("stat", visit(ctx.expression()).render());
      res.add("inst", s.varName());
      res.add("value", ctx.expression().var);
      return res;
   }

   @Override public ST visitPrints(MainGrammarParser.PrintsContext ctx) {
      ST res;
		if ("unit".equals(ctx.expression().e_type.name())) {
			res = templates.getInstanceOf("stats");
			String a1 = visit(ctx.expression()).render();
         String[] b1 = a1.split(" ");
         String c1 = b1[3].substring(0, b1[3].length()-1);
         ST declaration1 = templates.getInstanceOf("unitDecl");
         declaration1.add("inst", ctx.expression().var);
         declaration1.add("unit", c1);
         res.add("stat", declaration1.render());

			ST print = templates.getInstanceOf("PrintUnit");
			print.add("unit", ctx.expression().var);
			res.add("stat", print);
		} else {
			res = templates.getInstanceOf("print");
			res.add("stat", visit(ctx.expression()));
			res.add("expr", ctx.expression().var);
		}
		return res;
   }

   @Override public ST visitIntExpr(MainGrammarParser.IntExprContext ctx) {
      ST res = templates.getInstanceOf("decl");
      ctx.var = newVar();
      res.add("type", "int");
      res.add("inst", ctx.var);
      res.add("value", ctx.INT().getText());
      return res;
   }

   @Override public ST visitAddSubExpr(MainGrammarParser.AddSubExprContext ctx) {
      ctx.var = newVar();
      ST res = templates.getInstanceOf("stats");
      if("unit".equals(ctx.e_type.name())) { //verificar se são tipo unidade
         String a1 = visit(ctx.e1).render();
         String[] b1 = a1.split(" ");
         String c1 = b1[3].substring(0, b1[3].length()-1);
         ST declaration1 = templates.getInstanceOf("unitDecl");
         declaration1.add("inst", ctx.e1.var);
         declaration1.add("unit", c1);
         res.add("stat", declaration1.render());
         
         String a2 = visit(ctx.e2).render();
         String[] b2 = a2.split(" ");
         String c2 = b2[3].substring(0, b2[3].length()-1);
         ST declaration2 = templates.getInstanceOf("unitDecl");
         declaration2.add("inst", ctx.e2.var);
         declaration2.add("unit", c2);
         res.add("stat", declaration2.render());
         
         ST operation;
         if("+".equals(ctx.op.getText())){
            operation = templates.getInstanceOf("Unitadd");
            operation.add("var", ctx.var);
            operation.add("unit1",ctx.e1.var);
            operation.add("unit2",ctx.e2.var);
            res.add("stat", operation);
            return res;
         }else{
            operation = templates.getInstanceOf("Unitsub");
            operation.add("var", ctx.var);
            operation.add("unit1",ctx.e1.var);
            operation.add("unit2",ctx.e2.var);
            res.add("stat", operation);
            return res;
         }
      }
      else{
         return binaryExpression(ctx, visit(ctx.e1).render(), visit(ctx.e2).render(), ctx.e_type.name(), ctx.var, ctx.e1.var, ctx.op.getText(), ctx.e2.var);
      }
      
   }

   @Override public ST visitRealExpr(MainGrammarParser.RealExprContext ctx) {
      ST res = templates.getInstanceOf("decl");
      ctx.var = newVar();
      res.add("type", "real");
      res.add("inst", ctx.var);
      res.add("value", ctx.REAL().getText());
      return res;
   }

   @Override public ST visitMultDivExpr(MainGrammarParser.MultDivExprContext ctx) {
      ctx.var = newVar();
      ST res = templates.getInstanceOf("stats");
      if("unit".equals(ctx.e_type.name())) { //verificar se são tipo unidade
         if("*".equals(ctx.op.getText())){
            if("unit".equals(ctx.e1.e_type.name()) && "unit".equals(ctx.e2.e_type.name()) ) {
               String a1 = visit(ctx.e1).render();
               String[] b1 = a1.split(" ");
               String c1 = b1[3].substring(0, b1[3].length()-1);
               ST declaration1 = templates.getInstanceOf("unitDecl");
               declaration1.add("inst", ctx.e1.var);
               declaration1.add("unit", c1);
               res.add("stat", declaration1.render());
               
               String a2 = visit(ctx.e2).render();
               String[] b2 = a2.split(" ");
               String c2 = b2[3].substring(0, b2[3].length()-1);
               ST declaration2 = templates.getInstanceOf("unitDecl");
               declaration2.add("inst", ctx.e2.var);
               declaration2.add("unit", c2);
               res.add("stat", declaration2.render());
               
               ST operation;

               operation = templates.getInstanceOf("UnitMultiply");
               operation.add("var", ctx.var);
               operation.add("unit1",ctx.e1.var);
               operation.add("unit2",ctx.e2.var);
               res.add("stat", operation);
               
            }else if(!"unit".equals(ctx.e2.e_type.name()) && "unit".equals(ctx.e1.e_type.name())) {

               res.add("stat", visit(ctx.e2));
               String a1 = visit(ctx.e1).render();
               String[] b1 = a1.split(" ");
               String c1 = b1[3].substring(0, b1[3].length()-1);
               ST declaration2 = templates.getInstanceOf("unitDecl");
               declaration2.add("inst", ctx.e1.var);
               declaration2.add("unit", c1);
               res.add("stat", declaration2.render());

                  
               ST operation;

               operation = templates.getInstanceOf("UnitConstMultiply");
               operation.add("var", ctx.var);
               operation.add("unit",ctx.e1.var);
               operation.add("const",ctx.e2.var);
               res.add("stat", operation);

            } else if(!"unit".equals(ctx.e1.e_type.name()) && "unit".equals(ctx.e2.e_type.name())) {
               res.add("stat", visit(ctx.e1));
               String a2 = visit(ctx.e2).render();
               String[] b2 = a2.split(" ");
               String c2 = b2[3].substring(0, b2[3].length()-1);
               ST declaration2 = templates.getInstanceOf("unitDecl");
               declaration2.add("inst", ctx.e2.var);
               declaration2.add("unit", c2);
               res.add("stat", declaration2.render());
                  
               ST operation;
               operation = templates.getInstanceOf("UnitConstMultiply");
               operation.add("var", ctx.var);
               operation.add("unit",ctx.e2.var);
               operation.add("const",ctx.e1.var);
               res.add("stat", operation);
            }
         }else if("/".equals(ctx.op.getText())){
            if("unit".equals(ctx.e1.e_type.name()) && "unit".equals(ctx.e2.e_type.name()) ) {
               String a1 = visit(ctx.e1).render();
               String[] b1 = a1.split(" ");
               String c1 = b1[3].substring(0, b1[3].length()-1);
               ST declaration1 = templates.getInstanceOf("unitDecl");
               declaration1.add("inst", ctx.e1.var);
               declaration1.add("unit", c1);
               res.add("stat", declaration1.render());
               
               String a2 = visit(ctx.e2).render();
               String[] b2 = a2.split(" ");
               String c2 = b2[3].substring(0, b2[3].length()-1);
               ST declaration2 = templates.getInstanceOf("unitDecl");
               declaration2.add("inst", ctx.e2.var);
               declaration2.add("unit", c2);
               res.add("stat", declaration2.render());
               
               ST operation;

               operation = templates.getInstanceOf("UnitDivide");
               operation.add("var", ctx.var);
               operation.add("unit1",ctx.e1.var);
               operation.add("unit2",ctx.e2.var);
               res.add("stat", operation);
               
            }else if(!"unit".equals(ctx.e2.e_type.name()) && "unit".equals(ctx.e1.e_type.name())) {

               res.add("stat", visit(ctx.e2));
               String a1 = visit(ctx.e1).render();
               String[] b1 = a1.split(" ");
               String c1 = b1[3].substring(0, b1[3].length()-1);
               ST declaration2 = templates.getInstanceOf("unitDecl");
               declaration2.add("inst", ctx.e1.var);
               declaration2.add("unit", c1);
               res.add("stat", declaration2.render());

                  
               ST operation;

               operation = templates.getInstanceOf("UnitConstDivide");
               operation.add("var", ctx.var);
               operation.add("unit",ctx.e1.var);
               operation.add("const",ctx.e2.var);
               res.add("stat", operation);

            } 
         }
      } else {
         return binaryExpression(ctx, visit(ctx.e1).render(), visit(ctx.e2).render(), ctx.e_type.name(), ctx.var, ctx.e1.var, ctx.op.getText(), ctx.e2.var);
      }
      return res;
   }

   @Override public ST visitUnitVarExpr(MainGrammarParser.UnitVarExprContext ctx) {
      ST res = templates.getInstanceOf("unitCreate");
      ctx.var = newVar();
      res.add("inst", ctx.var);
      res.add("unitName", "\"" + ctx.unit.getText() +"\"");
      res.add("value", ctx.e.getText());
      return res;
   }

   @Override public ST visitParenExpr(MainGrammarParser.ParenExprContext ctx) {
      ST res = visit(ctx.expression());
      ctx.var = ctx.expression().var;
      return res;
   }

   @Override public ST visitIncrementsExpr(MainGrammarParser.IncrementsExprContext ctx) {
      ST res;
      String id = ctx.ID().getText();
      Symbol s = MainGrammarParser.symbolTable.get(id);
      if("unit".equals(s.type().name())) {
         if("++".equals(ctx.op.getText())){
				res = templates.getInstanceOf("unit_increment");
				res.add("var",s.varName());
            return res;
         }else{
            res = templates.getInstanceOf("unit_decrement");
				res.add("var",s.varName());
            return res;
         }
      } else {
         res = templates.getInstanceOf("increment_decrement");
         res.add("inst", s.varName());
         res.add("op", ctx.op.getText());
      }

      return res;

   }

   @Override public ST visitConditionalExpr(MainGrammarParser.ConditionalExprContext ctx) {
      ctx.var = newVar();
      return binaryExpression(ctx, visit(ctx.e1).render(), visit(ctx.e2).render(), "boolean", ctx.var, ctx.e1.var, ctx.op.getText(), ctx.e2.var);


   }

   @Override public ST visitIdExpr(MainGrammarParser.IdExprContext ctx) {
      ST res = templates.getInstanceOf("stats");
      ST decl = templates.getInstanceOf("decl");
      String id = ctx.ID().getText();
      Symbol s =  MainGrammarParser.symbolTable.get(id);
      
      ctx.var = newVar();
      decl.add("type", ctx.e_type.name());
      decl.add("inst", ctx.var);
      decl.add("value", s.varName());
      res.add("stat", decl.render());
      return res;
   }

   @Override public ST visitBooleanExpr(MainGrammarParser.BooleanExprContext ctx) {
      ST res = templates.getInstanceOf("decl");
      ctx.var = newVar();
      res.add("type", "boolean");
      res.add("var", ctx.var);
      ST lb = templates.getInstanceOf("literalBoolean");
      lb.add("value", ctx.BOOLEAN().getText());
      res.add("value", lb.render());
      return res;
   }



   protected ST binaryExpression(ParserRuleContext ctx, String e1Stats, String e2Stats, String type, String var, String e1Var, String op, String e2Var) {
      ST res = templates.getInstanceOf("stats");

		res.add("stat", e1Stats);
		res.add("stat", e2Stats);

		ST aux = templates.getInstanceOf("binaryExpression");

		aux.add("type", type);
		aux.add("var", var);
		aux.add("e1", e1Var);
		aux.add("op", op);
		aux.add("e2", e2Var);

		res.add("stat", aux.render());

		return res;
   }

   private String newVar(){
      numVars++;
      return "v"+numVars;
   }

   protected int numVars = 0;
   protected STGroup templates = new STGroupFile("java.stg");
}


