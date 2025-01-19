package performance;

import io.devnindo.datatype.schema.DataBean;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AntBean implements DataBean
{
    Integer antInteger;
    Long antLong;

    String antString;
    AnEnum antEnum;

    public Integer getAntInteger() {
        return antInteger;
    }

    public Long getAntLong() {
        return antLong;
    }

    public String getAntString() {
        return antString;
    }

    public AnEnum getAntEnum() {
        return antEnum;
    }
}
