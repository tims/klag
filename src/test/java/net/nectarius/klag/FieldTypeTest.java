package net.nectarius.klag;

import static org.junit.Assert.*;

import net.nectarius.klag.schema.FieldType;

import org.junit.Test;


public class FieldTypeTest {
  @Test
  public void testString() {
    FieldType type = FieldType.typeOf("String");
    assertEquals("string", type.getName());
  }
}
