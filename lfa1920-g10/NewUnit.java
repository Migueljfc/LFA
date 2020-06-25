public abstract class NewUnit{
  SymbolConv result; 
  String nameVar;

  public NewUnit(String unitName, double value){
	  this.result = new SymbolConv(unitName, value);
  }
  
  public NewUnit(){}

  public String getUnitName(){
    return this.result.unitName();
  }

  public double getValue(){
    return this.result.value();
  }
}
