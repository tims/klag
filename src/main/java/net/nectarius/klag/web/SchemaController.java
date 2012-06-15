package net.nectarius.klag.web;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import net.nectarius.klag.Row;
import net.nectarius.klag.RowField;
import net.nectarius.klag.query.esper.EsperService;
import net.nectarius.klag.schema.Schema;
import net.nectarius.klag.schema.SchemaField;
import net.nectarius.klag.schema.SchemaManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@Path("/schema")
public class SchemaController {

  @Autowired
  private EsperService esperService;

  @Autowired
  private SchemaManager schemaManager;

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("{schemaId}")
  public Schema getSchema(@PathParam("schemaId") String id) throws IOException {
    Schema schema = schemaManager.getSchema(id);
    return schema;
  }
  
  @PUT
  @Produces({MediaType.APPLICATION_JSON})
  @Path("{schemaId}")
  public void putSchema(@PathParam("schemaId") String id, @QueryParam("fields") String fieldDescriptor) throws IOException {
    schemaManager.getOrCreateSchema(id, parseSchemaFields(fieldDescriptor));
  }

  @POST
  @Produces({MediaType.APPLICATION_JSON})
  @Path("{schemaId}")
  @Consumes("application/x-www-form-urlencoded")
  public void postSchema(@PathParam("schemaId") String id, MultivaluedMap<String, String> form) throws IOException {
    Schema schema = schemaManager.getSchema(id);
    Row row = getFormRow(schema, form);
    esperService.sendRow(row);
  }

  @DELETE
  @Produces({MediaType.APPLICATION_JSON})
  @Path("{schemaId}")
  public void deleteSchema(@PathParam("schemaId") String id) throws IOException {
    schemaManager.dropSchema(id);
  }
  
  protected Row getFormRow(Schema schema, MultivaluedMap<String, String> form) {
    Row row = new Row();
    row.schemaId = schema.id;
    row.fields = new RowField[form.size()];
    int i = 0;
    for (String key : form.keySet()) {
      String value = form.get(key).get(0);
      row.fields[i] = new RowField(key, value);
      i++;
    }
    return row;
  }

  public SchemaField[] parseSchemaFields(String fieldsDescriptor) {
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

  protected SchemaField parseSingleSchemaField(String fieldDescriptor) {
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
