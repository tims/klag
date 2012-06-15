package net.nectarius.klag.query;

import java.util.List;

import net.nectarius.klag.Row;


public interface Query {

  public void start();

  public void push(Row row);

  public List<Row> collectResults();

  public void stop();
}