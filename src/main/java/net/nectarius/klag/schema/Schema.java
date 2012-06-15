package net.nectarius.klag.schema;

public class Schema {
  public int maxrows = 100000;
  public String id;
  public SchemaField[] fields;

  public Schema() {

  }

  public Schema(String id, SchemaField[] fields) {
    this.id = id;
    this.fields = fields;
  }

  public int getMaxrows() {
    return maxrows;
  }

  public void setMaxrows(int maxrows) {
    this.maxrows = maxrows;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public SchemaField[] getFields() {
    return fields;
  }

  public void setFields(SchemaField[] fields) {
    this.fields = fields;
  }

}
