package net.nectarius.klag.schema;

import static org.junit.Assert.*;

import net.nectarius.klag.schema.SchemaField;
import net.nectarius.klag.schema.SchemaManager;

import org.junit.Test;


public class SchemaManagerTest {
  @Test
  public void testParseSingleSchemaFields() {
    SchemaField field = SchemaManager.parseSingleSchemaField("bla:int");
    assertEquals("bla", field.getName());
    assertEquals("integer", field.getType().getName());
  }

  @Test
  public void testParseSingleSchemaFieldsDefaultType() {
    SchemaField field = SchemaManager.parseSingleSchemaField("bla");
    assertEquals("bla", field.getName());
    assertEquals("string", field.getType().getName());
  }

  @Test
  public void testParseSchemaFields() {
    SchemaField[] fields = SchemaManager.parseSchemaFields("f0:int,f1:String");
    assertEquals("f0", fields[0].getName());
    assertEquals("integer", fields[0].getType().getName());
    assertEquals("f1", fields[1].getName());
    assertEquals("string", fields[1].getType().getName());
  }

  @Test
  public void testParseEmptySchemaFields() {
    SchemaField[] fields = SchemaManager.parseSchemaFields("");
    assertDefaultSchemaFields(fields);
  }

  @Test
  public void testParseNullSchemaFields() {
    SchemaField[] fields = SchemaManager.parseSchemaFields(null);
    assertDefaultSchemaFields(fields);
  }

  public void assertDefaultSchemaFields(SchemaField[] fields) {
    assertEquals(10, fields.length);
    for (int i = 0; i < 10; i++) {
      assertEquals("f" + i, fields[i].getName());
      assertEquals("string", fields[i].getType().getName());
    }
  }
}
