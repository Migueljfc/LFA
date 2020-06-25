public class UnitType extends Type {

  public UnitType(){
      super("unit");
  }
  
  public boolean equals(Type other){
    if(this.name().equals(other.name()))
        return true;
    return false;
  }
}