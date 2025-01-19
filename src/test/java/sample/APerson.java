package sample;

import io.devnindo.datatype.schema.DataBean;
import io.devnindo.datatype.schema.IgnoreField;
import io.devnindo.datatype.schema.Required;
import io.devnindo.datatype.util.SampleBean;

import java.util.List;

public class APerson implements DataBean {
    @Required
    Long id;

    @IgnoreField
    String name;

    Integer certCount;

    Gender gender;

    List<Address> addressList;

    @Required
    Integer age;

    APerson employer;

    SampleBean sampleBean;


    public Long getId() {
        return id;
    }


    public Integer getAge() {
        return age;
    }

    public APerson setAge(Integer age$) {
        age = age$;
        return this;
    }

    public Integer getCertCount() {
        return certCount;
    }

    public APerson getEmployer() {
        return employer;
    }

    public Gender getGender() {
        return gender;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public SampleBean getSampleBean() {
        return sampleBean;
    }
}
