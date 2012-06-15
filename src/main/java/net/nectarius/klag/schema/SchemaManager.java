package net.nectarius.klag.schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.nectarius.klag.query.esper.EsperService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


public class SchemaManager {

  @Autowired
  private EsperService esper;

  private Map<String, Schema> schemas = new HashMap<String, Schema>();

  public Schema getSchema(String id) {
    return schemas.get(id);
  }

  public Collection<Schema> getSchemas() {
    List<Schema> values = new ArrayList<Schema>(schemas.values());
    Collections.sort(values, new Comparator<Schema>() {
      @Override
      public int compare(Schema s1, Schema s2) {
        return s1.id.compareTo(s2.id);
      }
    });
    return values;
  }

  synchronized public Schema getOrCreateSchema(String id, SchemaField[] fields) {
    if (schemas.containsKey(id)) {
      return schemas.get(id);
    } else {
      return createSchema(id, fields);
    }
  }

  synchronized public Schema createSchema(String id, SchemaField[] fields) {
    if (schemas.containsKey(id)) {
      throw new IllegalArgumentException("Schema id already exists: " + id);
    }
    Schema schema = new Schema(id, fields);
    esper.createSchema(schema);
    schemas.put(schema.id, schema);
    return schema;
  }

  synchronized public void dropSchema(String id) {
    if (schemas.containsKey(id)) {
      schemas.remove(id);
      esper.destroySchema(id);
    }
  }

  static public SchemaField[] parseSchemaFields(String fieldsDescriptor) {
    if (StringUtils.isEmpty(fieldsDescriptor)) {
      fieldsDescriptor = "f0,f1,f2,f3,f4,f5,f6,f7,f8,f9";
    }
    String[] fieldsDescriptorParts = fieldsDescriptor.split(",");
    int numFields = fieldsDescriptorParts.length;

    SchemaField[] fields = new SchemaField[fieldsDescriptorParts.length];
    for (int i = 0; i < numFields; i++) {
      fields[i] = parseSingleSchemaField(fieldsDescriptorParts[i]);
    }
    return fields;
  }

  static protected SchemaField parseSingleSchemaField(String fieldDescriptor) {
    Pattern pattern = Pattern.compile("(\\w+):(\\w+)");
    Matcher matcher = pattern.matcher(fieldDescriptor);
    if (matcher.matches()) {
      String name = matcher.group(1);
      String type = matcher.group(2);
      return new SchemaField(name, type);
    } else {
      return new SchemaField(fieldDescriptor);
    }
  }
}
