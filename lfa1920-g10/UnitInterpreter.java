import java.util.*;

public class UnitInterpreter extends UnitGrammarBaseVisitor<Object> {

   public HashMap<String, HashMap<String, SymbolConv>> dimensionsMap(){
      return UnitGrammarParser.dimensionsMap;
   }

   @Override public Object visitDim(UnitGrammarParser.DimContext ctx) {
      HashMap<String, HashMap<String, SymbolConv>> res = dimensionsMap();

      String dimensionName = ctx.dimension.getText();
      String unitName = ctx.unit.getText();
      String symbolName = ctx.symbol.getText();

      SymbolConv baseUnit = new SymbolConv(symbolName, 1);

      res.get(dimensionName).put(unitName, baseUnit);

      return res;
   }

   @Override public Object visitValueConvertion(UnitGrammarParser.ValueConvertionContext ctx) {
      HashMap<String, HashMap<String, SymbolConv>> res = dimensionsMap();

      String fullName = ctx.fullName.getText();
      String destination = ctx.dest.getText();
      String source = ctx.src.getText();
      double value = Double.parseDouble(ctx.value.getText());

      SymbolConv nUnit = new SymbolConv(destination, value);

      for(HashMap<String, SymbolConv> units : UnitGrammarParser.dimensionsMap.values()){
         if(units.containsKey(fullName)){
            String dimensionName = getKey1(UnitGrammarParser.dimensionsMap, units);
            UnitGrammarParser.dimensionsMap.get(dimensionName).put(fullName, nUnit);
            return res;
         }
      }
      return res;
   }

   public <String, HashMap> String getKey1(Map<String, HashMap> map, HashMap value) {
      for(Map.Entry<String, HashMap> entry : map.entrySet()) {
         if (entry.getValue().equals(value)) {
            return entry.getKey();
         }
      }  
      return null;
   }
}
