package sample;

import io.devnindo.datatype.json.JsonObject;
import io.devnindo.datatype.schema.BeanSchema;
import io.devnindo.datatype.schema.DataDiff;
import io.devnindo.datatype.schema.SchemaField;
import io.devnindo.datatype.util.Either;
import io.devnindo.datatype.util.SampleBean;
import io.devnindo.datatype.validation.ObjViolation;
import io.devnindo.datatype.validation.Violation;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.util.List;

public class $APerson extends BeanSchema<APerson> {
  public static final SchemaField<APerson, Integer> CERT_COUNT = plainField("cert_count", APerson::getCertCount, Integer.class, false);

  public static final SchemaField<APerson, Gender> GENDER = enumField("gender", APerson::getGender, Gender.class, false);

  public static final SchemaField<APerson, List<Address>> ADDRESS_LIST = beanListField("address_list", APerson::getAddressList, Address.class, false);

  public static final SchemaField<APerson, APerson> EMPLOYER = beanField("employer", APerson::getEmployer, APerson.class, false);

  public static final SchemaField<APerson, SampleBean> SAMPLE_BEAN = beanField("sample_bean", APerson::getSampleBean, SampleBean.class, false);

  public static final SchemaField<APerson, Long> ID = plainField("id", APerson::getId, Long.class, true);

  public static final SchemaField<APerson, Integer> AGE = plainField("age", APerson::getAge, Integer.class, true);

  @Override
  public Either<Violation, APerson> apply(JsonObject data) {
    Either<Violation, Integer> certCountEither = CERT_COUNT.fromJson(data);
    Either<Violation, Gender> genderEither = GENDER.fromJson(data);
    Either<Violation, List<Address>> addressListEither = ADDRESS_LIST.fromJson(data);
    Either<Violation, APerson> employerEither = EMPLOYER.fromJson(data);
    Either<Violation, SampleBean> sampleBeanEither = SAMPLE_BEAN.fromJson(data);
    Either<Violation, Long> idEither = ID.fromJson(data);
    Either<Violation, Integer> ageEither = AGE.fromJson(data);

    ObjViolation violation = newViolation(APerson.class);
    violation.check(CERT_COUNT, certCountEither);
    violation.check(GENDER, genderEither);
    violation.check(ADDRESS_LIST, addressListEither);
    violation.check(EMPLOYER, employerEither);
    violation.check(SAMPLE_BEAN, sampleBeanEither);
    violation.check(ID, idEither);
    violation.check(AGE, ageEither);

    if(violation.hasRequirement()) {
    	 return Either.left(violation);
    }
    APerson bean = new APerson();
    bean.certCount = certCountEither.right();
    bean.gender = genderEither.right();
    bean.addressList = addressListEither.right();
    bean.employer = employerEither.right();
    bean.sampleBean = sampleBeanEither.right();
    bean.id = idEither.right();
    bean.setAge(ageEither.right());
    return Either.right(bean);
  }

  @Override
  public JsonObject apply(APerson bean) {
    JsonObject js = new JsonObject();
    js.put(CERT_COUNT.name, CERT_COUNT.toJson(bean));
    js.put(GENDER.name, GENDER.toJson(bean));
    js.put(ADDRESS_LIST.name, ADDRESS_LIST.toJson(bean));
    js.put(EMPLOYER.name, EMPLOYER.toJson(bean));
    js.put(SAMPLE_BEAN.name, SAMPLE_BEAN.toJson(bean));
    js.put(ID.name, ID.toJson(bean));
    js.put(AGE.name, AGE.toJson(bean));
    return js;
  }

  @Override
  public DataDiff<APerson> diff(APerson left, APerson right) {
    APerson merged = new APerson();
    JsonObject delta = new JsonObject();

    merged.certCount = CERT_COUNT.diff(left, right, delta::put);
    merged.gender = GENDER.diff(left, right, delta::put);
    merged.addressList = ADDRESS_LIST.diff(left, right, delta::put);
    merged.employer = EMPLOYER.diff(left, right, delta::put);
    merged.sampleBean = SAMPLE_BEAN.diff(left, right, delta::put);
    merged.id = ID.diff(left, right, delta::put);
    merged.age = AGE.diff(left, right, delta::put);

    return new DataDiff<>(delta, merged);
  }
}
