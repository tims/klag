package net.nectarius.klag.schema;

public class FieldType {
  public static enum FieldTypeEnum {
    STRING, INTEGER, LONG, FLOAT, DOUBLE, BOOLEAN
  };

  public static final FieldType STRING = new FieldType(FieldTypeEnum.STRING);
  public static final FieldType INTEGER = new FieldType(FieldTypeEnum.INTEGER);
  public static final FieldType LONG = new FieldType(FieldTypeEnum.LONG);
  public static final FieldType FLOAT = new FieldType(FieldTypeEnum.FLOAT);
  public static final FieldType DOUBLE = new FieldType(FieldTypeEnum.DOUBLE);
  public static final FieldType BOOLEAN = new FieldType(FieldTypeEnum.BOOLEAN);

  private FieldTypeEnum type;

  public FieldType() {
    this(FieldTypeEnum.STRING);
  }

  private FieldType(FieldTypeEnum type) {
    this.type = type;
  }

  public static FieldType typeOf(String typeName) {
    typeName = typeName.toLowerCase();
    if (typeName.equals("string")) {
      return FieldType.STRING;
    } else if (typeName.equals("int")) {
      return FieldType.INTEGER;
    } else if (typeName.equals("long")) {
      return FieldType.LONG;
    } else if (typeName.equals("float")) {
      return FieldType.FLOAT;
    } else if (typeName.equals("double")) {
      return FieldType.DOUBLE;
    } else if (typeName.equals("boolean")) {
      return FieldType.BOOLEAN;
    } else {
      throw new RuntimeException("invalid field type name " + typeName);
    }
  }

  public Object valueOf(String value) {
    switch (type) {
    case STRING:
      return value;
    case INTEGER:
      return Integer.valueOf(value);
    case LONG:
      return Long.valueOf(value);
    case FLOAT:
      return Float.valueOf(value);
    case DOUBLE:
      return Double.valueOf(value);
    case BOOLEAN:
      return Boolean.valueOf(value);
    default:
      return value;
    }
  }

  public String getName() {
    return type.toString().toLowerCase();
  }
}
