package net.nectarius.klag.query;

import java.util.ArrayList;
import java.util.List;

import net.nectarius.klag.Row;


public class FetchAllQuery implements Query {
  List<Row> rows;

  public void start() {
    rows = new ArrayList<Row>();
  }

  public void push(Row row) {
    this.rows.add(row);
  }

  public List<Row> collectResults() {
    return rows;
  }

  public void stop() {
    rows = new ArrayList<Row>();
  }
}
