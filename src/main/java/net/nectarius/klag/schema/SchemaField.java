package net.nectarius.klag.schema;

public class SchemaField {
  private String name;
  private FieldType type = FieldType.STRING;

  public SchemaField() {

  }

  public SchemaField(String name) {
    this.name = name;
  }

  public SchemaField(String name, FieldType type) {
    this.name = name;
    this.type = type;
  }

  public SchemaField(String name, String typeName) {
    this.name = name;
    this.type = FieldType.typeOf(typeName);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FieldType getType() {
    return type;
  }

  public void setType(FieldType type) {
    this.type = type;
  }
}
