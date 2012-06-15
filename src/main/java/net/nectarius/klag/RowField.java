package net.nectarius.klag;

import net.nectarius.klag.schema.FieldType;

public class RowField {
  public String name;
  public String value;
  public FieldType type = FieldType.STRING;

  public RowField() {
  }

  public RowField(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public RowField(String name, String value, FieldType type) {
    this.name = name;
    this.value = value;
    this.type = type;
  }

  public Object getValueObject() {
    return type.valueOf(value);
  }

  @Override
  public String toString() {
    return name + "=" + value;
  }
}
