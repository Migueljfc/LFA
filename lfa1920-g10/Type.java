public abstract class Type
{
   protected Type(String name) {
      assert name != null;
      this.name = name;
   }

   public String name() {
      return name;
   }

   public boolean conformsTo(Type other) {
      return name.equals(other.name());
   }

   public boolean isNumeric() {
      return false;
   }

   public boolean equals(Type other){
      return true;
   }

   protected final String name;
}

