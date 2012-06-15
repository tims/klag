package net.nectarius.klag.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.nectarius.klag.schema.SchemaField;
import net.nectarius.klag.schema.SchemaManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Path("/schemas")
public class SchemasController {
  @Autowired
  private SchemaManager schemaManager;

  @GET
  @Produces(MediaType.TEXT_HTML)
  public ModelAndView index() {
    ModelAndView modelView = new ModelAndView("schemasView");
    modelView.addObject("schemas", schemaManager.getSchemas());
    return modelView;
  }

  @POST
  @Produces(MediaType.TEXT_HTML)
  @Path("submit")
  @Consumes("application/x-www-form-urlencoded")
  public void submit(@FormParam("submit") String submitValue, @FormParam("id") String id, @FormParam("fields") String fieldsDescriptor) {
    SchemaField[] fields = SchemaManager.parseSchemaFields(fieldsDescriptor);
    if (submitValue.equals("create")) {
      schemaManager.createSchema(id, fields);
    }
    if (submitValue.equals("delete")) {
      schemaManager.dropSchema(id);
    }
  }
}
