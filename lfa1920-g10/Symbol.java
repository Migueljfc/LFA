public abstract class Symbol
{
   protected final String name;
   protected final Type type;
   protected boolean varNameDefined;
   protected String varName;
   //protected Value value;
   //protected boolean valueDefined;

   public Symbol(String name, Type type) {
      assert name != null;
      assert type != null;

      this.name = name;
      this.type = type;
   }
   
   public String name(){
      return name;
   }

   public boolean varNameDefined(){
      varNameDefined = varName != null;
      
      return varNameDefined;
   }

   public void setVarName(String varName) {
      assert varName != null;

      this.varName = varName;
   }

   public String varName(){
      assert varNameDefined();

      return varName;
   }

   public Type type(){
      return type;
   }

  /* public void setValue(Value value) {
      assert value != null;

      this.value = value;
   }

   public void setValueDefined(){
      valueDefined = true;
   }

   public boolean valueDefined(){
      return valueDefined;
   }

   public Value value(){
      assert valueDefined();

      return value;
   }*/
}
