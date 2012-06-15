package net.nectarius.klag;

import java.util.Arrays;

public class Row {
  public String schemaId;
  public RowField[] fields;

  public Row() {
  }

  public Row(String schemaId, RowField[] fields) {
    this.schemaId = schemaId;
    this.fields = fields;
  }

  @Override
  public String toString() {
    return "Row [schemaId=" + schemaId + ", fields=" + Arrays.toString(fields) + "]";
  }
}
