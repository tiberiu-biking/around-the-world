package master.pam.crud.impl.filter;

import java.util.HashMap;
import java.util.Map;

public class FilterBuilder {

  private final Map<String, Object> map;

  public FilterBuilder() {
    map = new HashMap<String, Object>();
  }

  public FilterBuilder buildFilter(String aField, Object aFieldValue) {
    map.put(aField, aFieldValue);
    return this;
  }

  public Map<String, Object> getFilter() {
    return map;
  }

}
