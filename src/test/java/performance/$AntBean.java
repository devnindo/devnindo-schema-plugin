package performance;

import io.devnindo.datatype.json.JsonObject;
import io.devnindo.datatype.schema.BeanSchema;
import io.devnindo.datatype.schema.DataDiff;
import io.devnindo.datatype.schema.SchemaField;
import io.devnindo.datatype.util.Either;
import io.devnindo.datatype.validation.ObjViolation;
import io.devnindo.datatype.validation.Violation;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;

public class $AntBean extends BeanSchema<AntBean> {
  public static final SchemaField<AntBean, AnEnum> ANT_ENUM = enumField("ant_enum", AntBean::getAntEnum, AnEnum.class, false);

  public static final SchemaField<AntBean, Integer> ANT_INTEGER = plainField("ant_integer", AntBean::getAntInteger, Integer.class, false);

  public static final SchemaField<AntBean, Long> ANT_LONG = plainField("ant_long", AntBean::getAntLong, Long.class, false);

  public static final SchemaField<AntBean, String> ANT_STRING = plainField("ant_string", AntBean::getAntString, String.class, false);

  @Override
  public Either<Violation, AntBean> apply(JsonObject data) {
    Either<Violation, AnEnum> antEnumEither = ANT_ENUM.fromJson(data);
    Either<Violation, Integer> antIntegerEither = ANT_INTEGER.fromJson(data);
    Either<Violation, Long> antLongEither = ANT_LONG.fromJson(data);
    Either<Violation, String> antStringEither = ANT_STRING.fromJson(data);

    ObjViolation violation = newViolation(AntBean.class);
    violation.check(ANT_ENUM, antEnumEither);
    violation.check(ANT_INTEGER, antIntegerEither);
    violation.check(ANT_LONG, antLongEither);
    violation.check(ANT_STRING, antStringEither);

    if(violation.hasRequirement()) {
    	 return Either.left(violation);
    }
    AntBean bean = new AntBean();
    bean.antEnum = antEnumEither.right();
    bean.antInteger = antIntegerEither.right();
    bean.antLong = antLongEither.right();
    bean.antString = antStringEither.right();
    return Either.right(bean);
  }

  @Override
  public JsonObject apply(AntBean bean) {
    JsonObject js = new JsonObject();
    js.put(ANT_ENUM.name, ANT_ENUM.toJson(bean));
    js.put(ANT_INTEGER.name, ANT_INTEGER.toJson(bean));
    js.put(ANT_LONG.name, ANT_LONG.toJson(bean));
    js.put(ANT_STRING.name, ANT_STRING.toJson(bean));
    return js;
  }

  @Override
  public DataDiff<AntBean> diff(AntBean left, AntBean right) {
    AntBean merged = new AntBean();
    JsonObject delta = new JsonObject();

    merged.antEnum = ANT_ENUM.diff(left, right, delta::put);
    merged.antInteger = ANT_INTEGER.diff(left, right, delta::put);
    merged.antLong = ANT_LONG.diff(left, right, delta::put);
    merged.antString = ANT_STRING.diff(left, right, delta::put);

    return new DataDiff<>(delta, merged);
  }
}
