package net.nectarius.klag.query;

import java.util.HashMap;
import java.util.Map;

import net.nectarius.klag.query.esper.EsperService;
import net.nectarius.klag.schema.Schema;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


public class QueryManager {
  @Autowired
  private EsperService esper;
  private Map<String, Query> queries = new HashMap<String, Query>();

  public Query getTransientQuery(String expression) {
    return newQuery(expression);
  }

  synchronized public Query getPersistentQuery(String expression) {
    if (queries.containsKey(expression)) {
      return queries.get(expression);
    } else {
      Query query = newQuery(expression);
      query.start();
      queries.put(expression, query);
      return query;
    }
  }

  synchronized public void removeQuery(Schema schema) {
    if (queries.containsKey(schema.id)) {
      Query query = queries.remove(schema.id);
      query.stop();
    }
  }

  private Query newQuery(String expression) {
    if (StringUtils.isEmpty(expression)) {
      return new FetchAllQuery();
    } else {
      return new EsperQuery(esper, expression);
    }
  }
}
