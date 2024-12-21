package examples;

import io.devnindo.datatype.schema.DataBean;


import java.util.List;

public class Address implements DataBean {
     String city;

     NotDataBean dataBean;
     List<String> roadList;

    public NotDataBean getDataBean() {
        return dataBean;
    }

      String getCity() {
        return city;
    }

    public List<String> getRoadList() {
        return roadList;
    }


}
