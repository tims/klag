package net.nectarius.klag.query.esper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.nectarius.klag.Row;
import net.nectarius.klag.RowField;
import net.nectarius.klag.schema.Schema;
import net.nectarius.klag.schema.SchemaField;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;

public class EsperService {
  private static final Logger log = LoggerFactory.getLogger(EsperService.class);
  private EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
  private Map<String, EPStatement> queryStatements = new HashMap<String, EPStatement>();
  private Map<String, EPStatement> schemaStatements = new HashMap<String, EPStatement>();

  public void createQuery(String expression) throws EPException {
    if (queryStatements.containsKey(expression)) {
      throw new IllegalArgumentException("Query with expression=" + expression + " already exists");
    }
    EPStatement statement = createStatement(expression);
    queryStatements.put(expression, statement);
  }

  public void destroyQuery(String expression) {
    if (queryStatements.containsKey(expression)) {
      EPStatement statement = queryStatements.remove(expression);
      statement.destroy();
    }
  }

  public void createSchema(Schema schema) {
    if (schemaStatements.containsKey(schema.id)) {
      throw new IllegalArgumentException("Schema with id=" + schema.id + " already exists");
    }
    List<String> fields = new ArrayList<String>();
    for (SchemaField field : schema.fields) {
      fields.add(field.getName() + " " + field.getType().getName());
    }
    String fieldDescription = StringUtils.join(fields, ", ");
    String expression = "CREATE SCHEMA " + schema.id + " AS (" + fieldDescription + ")";
    EPStatement statement = createStatement(expression);
    schemaStatements.put(schema.id, statement);
  }

  private EPStatement createStatement(String expression) {
    EPStatement statement = epService.getEPAdministrator().createEPL(expression);
    return statement;
  }

  public void destroySchema(String schemaId) {
    if (schemaStatements.containsKey(schemaId)) {
      EPStatement statement = schemaStatements.remove(schemaId);
      statement.destroy();
    }
  }

  public void sendRow(Row row) {
    EPRuntime runtime = epService.getEPRuntime();
    Map<String, Object> propertyMap = getPropertyMapFromRowFields(row.fields);
    log.info(row.schemaId);
    runtime.sendEvent(propertyMap, row.schemaId);
  }

  public Map<String, Object> getPropertyMapFromRowFields(RowField[] fields) {
    Map<String, Object> propertyMap = new HashMap<String, Object>();
    for (RowField field : fields) {
      propertyMap.put(field.name, field.getValueObject());
    }
    return propertyMap;
  }

  public List<Row> getResults(String expression) {
    if (!queryStatements.containsKey(expression)) {
      throw new IllegalArgumentException("Statement with expression=" + expression + " does not exists");
    }
    EPStatement statement = queryStatements.get(expression);
    SafeIterator<EventBean> safeIter = statement.safeIterator();
    List<Row> rows = new ArrayList<Row>();
    try {
      while (safeIter.hasNext()) {
        EventBean event = safeIter.next();
        Row row = getRowFromEvent(event);
        rows.add(row);
      }
    } finally {
      safeIter.close();
    }
    return rows;
  }

  public Row getRowFromEvent(EventBean event) {
    String[] fieldNames = event.getEventType().getPropertyNames();
    RowField[] fields = new RowField[fieldNames.length];

    for (int i = 0; i < fieldNames.length; i++) {
      String name = fieldNames[i];
      RowField field = new RowField(name, String.valueOf(event.get(name)), null);
      fields[i] = field;
    }
    Row row = new Row();
    row.fields = fields;
    row.schemaId = event.getEventType().getName();
    return row;
  }
}
