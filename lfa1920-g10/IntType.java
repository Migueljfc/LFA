public class IntType extends Type {
  public IntType() {
    super("int");
  }

  public boolean isNumeric() {
    return true;
  }

  @Override public boolean conformsTo(Type other) {
    return super.conformsTo(other) || other.name().equals("real");
  }
}