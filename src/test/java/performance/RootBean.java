package performance;

import io.devnindo.datatype.schema.DataBean;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RootBean implements DataBean
{
    Integer fldInteger;
    Long fldLong;
    Boolean fldBoolean;
    Double fldDouble;
    String fldString;

    List<AntBean> fldAntList;

    public Integer getFldInteger() {
        return fldInteger;
    }

    public Long getFldLong() {
        return fldLong;
    }

    public Boolean getFldBoolean() {
        return fldBoolean;
    }

    public Double getFldDouble() {
        return fldDouble;
    }

    public String getFldString() {
        return fldString;
    }

    public List<AntBean> getFldAntList() {
        return fldAntList;
    }
}
