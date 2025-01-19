package performance;

import io.devnindo.datatype.json.JsonObject;
import io.devnindo.datatype.schema.BeanSchema;
import io.devnindo.datatype.schema.DataDiff;
import io.devnindo.datatype.schema.SchemaField;
import io.devnindo.datatype.util.Either;
import io.devnindo.datatype.validation.ObjViolation;
import io.devnindo.datatype.validation.Violation;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class $RootBean extends BeanSchema<RootBean> {
  public static final SchemaField<RootBean, Double> FLD_DOUBLE = plainField("fld_double", RootBean::getFldDouble, Double.class, false);

  public static final SchemaField<RootBean, Boolean> FLD_BOOLEAN = plainField("fld_boolean", RootBean::getFldBoolean, Boolean.class, false);

  public static final SchemaField<RootBean, String> FLD_STRING = plainField("fld_string", RootBean::getFldString, String.class, false);

  public static final SchemaField<RootBean, Long> FLD_LONG = plainField("fld_long", RootBean::getFldLong, Long.class, false);

  public static final SchemaField<RootBean, Integer> FLD_INTEGER = plainField("fld_integer", RootBean::getFldInteger, Integer.class, false);

  public static final SchemaField<RootBean, List<AntBean>> FLD_ANT_LIST = beanListField("fld_ant_list", RootBean::getFldAntList, AntBean.class, false);

  @Override
  public Either<Violation, RootBean> apply(JsonObject data) {
    Either<Violation, Double> fldDoubleEither = FLD_DOUBLE.fromJson(data);
    Either<Violation, Boolean> fldBooleanEither = FLD_BOOLEAN.fromJson(data);
    Either<Violation, String> fldStringEither = FLD_STRING.fromJson(data);
    Either<Violation, Long> fldLongEither = FLD_LONG.fromJson(data);
    Either<Violation, Integer> fldIntegerEither = FLD_INTEGER.fromJson(data);
    Either<Violation, List<AntBean>> fldAntListEither = FLD_ANT_LIST.fromJson(data);

    ObjViolation violation = newViolation(RootBean.class);
    violation.check(FLD_DOUBLE, fldDoubleEither);
    violation.check(FLD_BOOLEAN, fldBooleanEither);
    violation.check(FLD_STRING, fldStringEither);
    violation.check(FLD_LONG, fldLongEither);
    violation.check(FLD_INTEGER, fldIntegerEither);
    violation.check(FLD_ANT_LIST, fldAntListEither);

    if(violation.hasRequirement()) {
    	 return Either.left(violation);
    }
    RootBean bean = new RootBean();
    bean.fldDouble = fldDoubleEither.right();
    bean.fldBoolean = fldBooleanEither.right();
    bean.fldString = fldStringEither.right();
    bean.fldLong = fldLongEither.right();
    bean.fldInteger = fldIntegerEither.right();
    bean.fldAntList = fldAntListEither.right();
    return Either.right(bean);
  }

  @Override
  public JsonObject apply(RootBean bean) {
    JsonObject js = new JsonObject();
    js.put(FLD_DOUBLE.name, FLD_DOUBLE.toJson(bean));
    js.put(FLD_BOOLEAN.name, FLD_BOOLEAN.toJson(bean));
    js.put(FLD_STRING.name, FLD_STRING.toJson(bean));
    js.put(FLD_LONG.name, FLD_LONG.toJson(bean));
    js.put(FLD_INTEGER.name, FLD_INTEGER.toJson(bean));
    js.put(FLD_ANT_LIST.name, FLD_ANT_LIST.toJson(bean));
    return js;
  }

  @Override
  public DataDiff<RootBean> diff(RootBean left, RootBean right) {
    RootBean merged = new RootBean();
    JsonObject delta = new JsonObject();

    merged.fldDouble = FLD_DOUBLE.diff(left, right, delta::put);
    merged.fldBoolean = FLD_BOOLEAN.diff(left, right, delta::put);
    merged.fldString = FLD_STRING.diff(left, right, delta::put);
    merged.fldLong = FLD_LONG.diff(left, right, delta::put);
    merged.fldInteger = FLD_INTEGER.diff(left, right, delta::put);
    merged.fldAntList = FLD_ANT_LIST.diff(left, right, delta::put);

    return new DataDiff<>(delta, merged);
  }
}
