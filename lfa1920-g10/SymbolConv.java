public class SymbolConv {
  private String unitName;
  private double value;
    
  public SymbolConv(String unitName, double value) {
    this.unitName = unitName;
    this.value = value;
  }

  // for UnitSemCheck
  public SymbolConv(String unitName){
    this.unitName = unitName;
  }

  public String unitName() {
    return unitName;
  }

  public void setName(String unitName){
    this.unitName = unitName;
  }

  public void setValue(Double value){
    this.value = value;
  }

  public double value() {
    return value;
  }

  public String toString(){
    return "Name: "+this.unitName+", Value: "+this.value;
  }
}
