
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Interpreter extends StrLangBaseVisitor<String> {

   @Override public String visitProgram(StrLangParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitStat(StrLangParser.StatContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitPrint(StrLangParser.PrintContext ctx) {
      String res = visit(ctx.expr());
      if(res != null){
         System.out.println(res);
      }
      return res;
   }

   @Override public String visitDefineVar(StrLangParser.DefineVarContext ctx) {
      String res = visit(ctx.expr());
      if(res!= null){
         String id = ctx.VAR().getText();
         varMap.put(id, res);
      }
      return res;
   }
   @Override public String visitInputExpr(StrLangParser.InputExprContext ctx) {
      String prompt = visit(ctx.expr()); 
      String res = null;
      if(prompt != null){
         System.out.print(prompt);
         res = sc.nextLine();
      }
      return res;
   }
   @Override public String visitSubExpr(StrLangParser.SubExprContext ctx) { 
      String res = null;
      String e1 = visit(ctx.e1);
      if(varMap.containsKey(e1)){
         e1 = varMap.get(e1);
      }
      String e2 = visit(ctx.e2);
      String e3 = visit(ctx.e3);
      if(e1 != null && e2 != null && e3 != null){
         res = e1.replace(e2, e3);
      }
      return res; 
   }
   @Override public String visitConcExpr(StrLangParser.ConcExprContext ctx) { 
      String e1 = visit(ctx.e1);
      String e2 = visit(ctx.e2);
      String res = null;
      if(e1 != null && e2 != null){
          res = e1 + e2;
      }
      return res; 
   }

   @Override public String visitStringExpr(StrLangParser.StringExprContext ctx) {
      String res = ctx.STRING().getText();
      res = res.substring(1,res.length()-1);
      return res;
   }

   @Override public String visitVarExpr(StrLangParser.VarExprContext ctx) {
      String id = ctx.VAR().getText();
      String res = null;
      if(!varMap.containsKey(id)){
         System.out.println("Erro: não existe a variável \"" + id + "\"");
      }
      else{
         res = varMap.get(id);
      }
      return res;
   }
   private Map<String,String> varMap = new HashMap<>();
   private Scanner sc = new Scanner(System.in);
}
