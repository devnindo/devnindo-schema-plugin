package sample;

import io.devnindo.datatype.schema.DataBean;
import io.devnindo.datatype.schema.IgnoreField;


import java.util.List;

public class Address implements DataBean {
     String city;

     @IgnoreField
     NotDataBean dataBean;
     List<String> roadList;

    public NotDataBean getDataBean() {
        return dataBean;
    }

    public  String getCity() {
        return city;
    }

    public List<String> getRoadList() {
        return roadList;
    }


}
