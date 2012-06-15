package net.nectarius.klag;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Data {
  public String source;
  public String path;
  public List<Row> rows;

  public Data() {

  }

  public Data(String source, String path, List<Row> rows) {
    this.source = source;
    this.path = path;
    this.rows = rows;
  }

}
