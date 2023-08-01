package examples;

import io.devnindo.datatype.schema.DataBean;
import io.devnindo.datatype.schema.AField;


import java.util.List;

public class Address implements DataBean {
    public String city;

    public List<String> roadList;

    public String getCity() {
        return city;
    }

    public List<String> getRoadList() {
        return roadList;
    }
}
