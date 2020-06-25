import java.util.*;

public class UnitSemCheck extends UnitGrammarBaseVisitor<Boolean> {

   private LinkedList<String> baseUnitsSymbols = new LinkedList<>();
   private Boolean erreur = false;

   public Boolean erreur(){
      return erreur;
   }

   @Override public Boolean visitDim(UnitGrammarParser.DimContext ctx) {
      Boolean res = true;

      String dimensionName = ctx.dimension.getText();
      String unitName = ctx.unit.getText();
      String symbolName = ctx.symbol.getText();
      // para o visitValueConvertion
      baseUnitsSymbols.add(symbolName);

      if(UnitGrammarParser.dimensionsMap.containsKey(dimensionName)){
         ErrorHandling.printError(ctx, "ERROR: Dimension \""+ dimensionName+ "\" already exists!");
         res = false;
         erreur = true;
      }else{
         HashMap<String, SymbolConv> unit = new HashMap<>();
         SymbolConv baseUnit = new SymbolConv(symbolName);
         unit.put(unitName, baseUnit);
         UnitGrammarParser.dimensionsMap.put(dimensionName, unit );
      }
      return res;
   }

   @Override public Boolean visitValueConvertion(UnitGrammarParser.ValueConvertionContext ctx) {
      Boolean res = true;
      
      String source = ctx.src.getText();
      String destination = ctx.dest.getText();
      String fullName = ctx.fullName.getText();

      SymbolConv nullValue = new SymbolConv(destination);

      if(!baseUnitsSymbols.contains(source)){
         ErrorHandling.printError(ctx, "ERROR: Creating unit \""+ fullName +"\" at an undefined dimension!");
         res = false;
         erreur = true;
         return res;
      }

      for(HashMap<String, SymbolConv> units : UnitGrammarParser.dimensionsMap.values()){
         String dimensionName = getKey1(UnitGrammarParser.dimensionsMap, units);
         
         if(units.containsKey(fullName)){
            ErrorHandling.printError(ctx, "ERROR: Unit \""+ fullName +"\" already exists!");
            res = false;
            erreur = true;
            return res;
         }

         for(SymbolConv value : units.values()){
            if(source.equals(value.unitName())){
               UnitGrammarParser.dimensionsMap.get(dimensionName).put(fullName, nullValue);
               res = true;
               return res;
            }
         }
      }
      
      return res;
   }
   
   private <String, HashMap> String getKey1(Map<String, HashMap> map, HashMap value) {
      for(Map.Entry<String, HashMap> entry : map.entrySet()) {
         if (entry.getValue().equals(value)) {
            return entry.getKey();
         }
      }  
      return null;
   }
}
