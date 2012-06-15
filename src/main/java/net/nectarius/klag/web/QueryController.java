package net.nectarius.klag.web;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.nectarius.klag.Data;
import net.nectarius.klag.query.Query;
import net.nectarius.klag.query.QueryManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@Path("/query")
public class QueryController {

  @Autowired
  QueryManager queryManager;

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("{expression}")
  public Data getSchema(@PathParam("expression") String expression) throws IOException {
    Query query = queryManager.getPersistentQuery(expression);
    return new Data(null, null, query.collectResults());
  }
}
