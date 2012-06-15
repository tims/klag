package net.nectarius.klag.query;

import java.util.List;

import net.nectarius.klag.Row;
import net.nectarius.klag.query.esper.EsperService;


public class EsperQuery implements Query {
  private EsperService esper;
  private String expression;

  public EsperQuery(EsperService esper, String expression) {
    this.esper = esper;
    this.expression = expression;
  }

  public void start() {
    esper.createQuery(expression);
  }

  public void push(Row row) {
    esper.sendRow(row);
  }

  public List<Row> collectResults() {
    return esper.getResults(expression);
  }

  public void stop() {
    esper.destroyQuery(expression);
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }
}
